# Endpoints nuevos en haberes-core — Bono Individual (frmImprimirInd.frm)

**Proyecto:** Migración gradual VB6 (`prjBonos.vbp`) → `haberes-frontend/apps/liquidacion`
**Pantalla destino:** `/consultas/bono-individual`
**Fecha de análisis:** 2026-09-17
**Fuentes VB6 de referencia:** `frmImprimirInd.frm`, `clsCtlPrint.cls`, `clsCtlLiquidacion.cls`, `clsMOD/REPBonoImpresion.cls`, `modIP.bas`

---

## 1. Objetivo

El front Angular **no debe contener lógica de negocio**. En el diseño actual del
monorepo, las pantallas migradas (ej. `feature-cargos`) resuelven la impresión del
bono componiendo varios GET/PUT sobre `/api/haberes/core/*` y llamando directo al
report service (`/api/haberes/report/bono/generatePdf|sendBono`). Eso arrastraría
al browser responsabilidades que en VB6 estaban en clases de servicio, y datos que
el browser no puede conocer de forma confiable (IP real del puesto, auditoría).

Este documento define los endpoints que `haberes-core` debe exponer para que el
front quede reducido a: **buscar persona → validar integridad → preparar → pedir
PDF → (opcional) enviar por e-mail**.

---

## 2. Responsabilidades VB6 a trasladar al core (trazabilidad)

| # | Responsabilidad | Código VB6 | Problema si queda en el front |
|---|---|---|---|
| R1 | Verificación de integridad previa a imprimir (Liquidación, Item, Actividad, LegajoControl y "Legajo SIN Dependencia") | `frmImprimirInd.frm:272-327` (`cmdIntegridad_Click`), `frmImprimirInd.frm:251-254` (`cmdImprimir_Click`) | 4+ requests encadenados, reglas de negocio y mensajes de error duplicados en TypeScript |
| R2 | `beforePrintBono`: recalcular flags `docente/otras/clases` de `actividad` antes de emitir el bono | `clsCtlLiquidacion.cls:16-49` | El front tendría que leer `cargoliquidacion` + `cargoclasedetalle`, juzgar categorías (`docente=1 && categoriaBasico!=0`, `noDocente=1 && categoriaBasico!=0`) y hacer `PUT /actividad` — lógica de dominio pura en el cliente |
| R3 | Auditoría de impresión en `bono_impresion` (legajoId, año, mes, legajoIdSolicitud, fecha, ipAddress) | `clsCtlPrint.cls:41` pasa `usuarioApp.legajoId` + `modIP.getIPAddress` al report service; tabla auditada por `clsREPBonoImpresion.cls` | El browser no puede conocer su IP real de red (NAT/proxy) ni debe poder elegir el "solicitante" ni la IP: registro auditable falsificable |
| R4 | Resolución de dependencias de datos para el formulario (persona por documento/legajo/nombre, contacto/mail institucional) | `frmImprimirInd.frm:179-193, 398-461` | **Resuelto**: ya existen endpoints en core (ver §3) |
| R5 | Envío por e-mail del bono (`sendBono` + guardado del contacto) | `frmImprimirInd.frm:212-243`, `clsCtlPrint.cls:269-285` | Igual que R3: el parámetro `{solicitante}/{ip}` no debe armarlo el front |

---

## 3. Lo que YA existe en haberes-core (no construir, reutilizar)

Verificado en el código actual:

| Uso | Endpoint | Fuente |
|---|---|---|
| Búsqueda de legajo por texto (autocomplete) | `POST /api/haberes/core/persona/search` (`List<String>`) | `PersonaController.java:81` |
| Persona por legajo | `GET /api/haberes/core/persona/{legajoId}` | `PersonaController.java:86` |
| Persona por documento | `GET /api/haberes/core/persona/documento/{documento}` | `PersonaController.java:95` |
| Mail institucional (leer) | `GET /api/haberes/core/contacto/{legajoId}` | `ContactoController.java` |
| Mail institucional (guardar) | `PUT /api/haberes/core/contacto/{legajoId}` | `ContactoController.java` |
| Liquidación por período-legajo | `GET /api/haberes/core/liquidacion/unique/{legajoId}/{anho}/{mes}` | `LiquidacionController.java` |
| Actividad por período-legajo | `GET /api/haberes/core/actividad/unique/{legajoId}/{anho}/{mes}` | `ActividadController.java:40` |
| LegajoControl por período-legajo | `GET /api/haberes/core/legajocontrol/unique/{legajoId}/{anho}/{mes}` | `LegajoControlController.java` |
| Items del legajo en período | `GET /api/haberes/core/item/periodolegajo/{anho}/{mes}/{legajoId}/{limit}` | `ItemController.java:55` |
| Registro simple de auditoría | `POST /api/haberes/core/bonoimpresion/` (sin IP ni fecha derivadas del request: `add()` solo fija `fecha`) | `BonoImpresionController.java:31`, `BonoImpresionService.java` |

Estos endpoints sirven tal cual a la pantalla. **Ninguno de los de arriba debe
componerse en el front para implementar R1/R2/R3**: para eso van los nuevos.

---

## 4. Endpoints nuevos propuestos

Nuevo facade: `um.haberes.core.controller.facade.BonoIndividualController`
(base recommended: `/api/haberes/core/bono`, coherente con los facades existentes
`MailingController`, `LiquidacionEtecController`, etc.).

Nuevo service: `um.haberes.core.service.bono.BonoIndividualService` (lógica de R1/R2/R3).
Nuevo DTO de request/response en `um.haberes.core.model.dto`.

### E1 — Verificación de integridad

```
GET /api/haberes/core/bono/{legajoId}/{anho}/{mes}/integridad
```

Reemplaza `cmdIntegridad_Click` (R1) **y** el guard de dependencia de
`cmdImprimir_Click`. Un solo round-trip.

Reglas (mismas que VB6):

| Check | Criterio VB6 | Criterio core |
|---|---|---|
| DEPENDENCIA | `legajo.dependencia Is Nothing` | `PersonaEntity` sin dependencia asociada |
| LIQUIDACION | `liquidacionRep.findByUnique(...)` vacío | `findByLegajoIdAndAnhoAndMes(...)` vacío |
| ITEM | `itemRep.collectionByPeriodo(anho, mes, legajoId, 1).Count = 0` | `existsByLegajoIdAndAnhoAndMes(...)` false (usar `exists…`, no traer listas) |
| ACTIVIDAD | `actividadRep.findByUnique(...)` vacío | `findByLegajoIdAndAnhoAndMes(...)` vacío |
| LEGAJO_CONTROL | `legajoControlRep.findByUnique(...)` vacío | derivado equivalente en `JpaLegajoControlRepository` |

Respuesta 200 (el éxito HTTP no depende del resultado):

```json
{
  "legajoId": 12345,
  "anho": 2026,
  "mes": 8,
  "ok": false,
  "faltantes": ["ITEM", "LEGAJO_CONTROL"]
}
```

- `faltantes` admite: `DEPENDENCIA | LIQUIDACION | ITEM | ACTIVIDAD | LEGAJO_CONTROL`.
- El front solo mapea códigos a mensajes ("ERROR: Falta ITEM", etc.).

### E2 — Preparar impresión (`beforePrintBono`)

```
POST /api/haberes/core/bono/{legajoId}/{anho}/{mes}/prepare
```

Reemplaza R2 (`clsCtlLiquidacion.beforePrintBono`). Transacción única:

1. Si no existe `Liquidacion` → `400` con detalle (VB6 salía en silencio; en web
   conviene error explícito).
2. Recalcular sobre `Actividad` del período (crear si no existe, con `findByUnique`):
   - `docente = 1` si algún `CargoLiquidacionEntity` tiene `categoria.docente == 1 && categoriaBasico != 0`
     (fuente: `CargoLiquidacionEntity.categoria` / `.categoriaBasico`, ya mapeados).
   - `otras = 1` si alguno tiene `categoria.noDocente == 1 && categoriaBasico != 0`.
   - `clases = 1` si `CargoClaseDetalle` por legajo/período es no vacío
     (`JpaCargoClaseDetalleRepository`: derivado `existsByLegajoIdAndAnhoAndMes` — verificar nombre de campos en la entidad).
   - Antes de asignar, los tres flags se resetean a `0` (comportamiento VB6).
3. Persistir `Actividad` (save). Si el save falla → `500` (VB6 tenía `rollback` vacío; acá usar `@Transactional` y excepción de dominio).

Respuesta 200: `ActividadEntity` actualizado.

### E3 — Registrar auditoría (con IP y fecha server-side)

```
POST /api/haberes/core/bono/{legajoId}/{anho}/{mes}/auditoria
```

Reemplaza R3. El body es **vacío o mínimo** (ver abajo); el servidor deriva:

- `bonoImpresion.legajoId` ← path
- `anho`, `mes` ← path
- `fecha` ← `Tool.hourAbsoluteArgentina()` (ya lo hace `BonoImpresionService.add`)
- `ipAddress` ← nueva utilidad `ClientIpResolver` (ver §5): `X-Forwarded-For` →
  `X-Real-IP` → `getRemoteAddr()` (nginx/gateway es origen único, ver AGENTS.md de haberes-frontend)
- `legajoIdSolicitud` ← identidad del usuario autenticado (ver §5, decisión D2)

Body opcional (si D2 se resuelve en "el front envía el solicitante"):

```json
{ "legajoIdSolicitud": 999 }
```

Respuesta 200: `BonoImpresionEntity` persistido.

Comportamiento "one-shot" sugerido para el front: el botón **Imprimir** llama
`prepare` + `auditoria` y luego descarga el PDF; o bien se expone:

### E4 (opcional, recomendada) — Compuesto de un solo paso

```
POST /api/haberes/core/bono/{legajoId}/{anho}/{mes}/print-prepare
```

Ejecuta E1 (rechaza con `400` + `faltantes` si `ok=false`), E2 y E3 en una sola
transacción/round-trip. El front queda en dos llamadas:

```
POST /bono/{...}/print-prepare      → valida + recalcula + audita (IP server-side)
GET  /api/haberes/report/bono/generatePdf/{legajoId}/{anho}/{mes} → blob PDF → abrir
```

### E5 — Preparar envío por e-mail

```
POST /api/haberes/core/bono/{legajoId}/{anho}/{mes}/send-prepare
Content-Type: application/json
{ "mailInstitucional": "agente@um.edu.ar" }
```

Reemplaza R5. Haz:

1. Validar formato de mail (equivalente `modValidate.validateMail`) → `400` si inválido.
2. Upsert `ContactoEntity` (`findByLegajoId` + update/add) con el mail.
3. Registrar auditoría de envío (misma mecánica que E3).
4. Dejar listo el estado para el envío real.

Luego el front llama `GET /api/haberes/report/bono/sendBono/...` (decisión D1 sobre
parámetros) y muestra el texto de respuesta, o — **preferido si se puede tocar el
report service** — el propio core dispara el mailing reusando `MailingController`
(`facade/MailingController.java:36`, `MailInfo`), dejando el `sendBono` de report
solo para compatibilidad VB6. Esto elimina el último "orchestration" del front.

### E6 (opcional) — Historial de impresiones

```
GET /api/haberes/core/bono/{legajoId}/{anho}/{mes}/auditoria
```

Devuelve `List<BonoImpresionEntity>` del período (derivado
`findAllByLegajoIdAndAnhoAndMesOrderByFechaDesc` en `JpaBonoImpresionRepository`,
hoy vacía). Útil para "¿se imprimió ya?" antes de emitir. No tiene equivalente
directo en el formulario VB6; incluir solo si la pantalla lo muestra.

---

## 5. Dependencias transversales (trabajo previo)

1. **`ClientIpResolver` (nuevo)**: no hay ningún uso de `HttpServletRequest` /
   `X-Forwarded-For` / `getRemoteAddr()` en el core actual (verificado). Crear en
   `um.haberes.core.util`, leer primero `X-Forwarded-For` (primer valor), luego
   `X-Real-IP`, luego `getRemoteAddr()`. El deploy es single-origin con nginx que
   proxydea `/api/` → se debe garantizar que nginx/gateway propague `X-Forwarded-For`.
2. **Decisión D1 — compatibilidad VB6 con el report service**: mientras `Bonos.exe`
   siga en uso, `generatePdf/{legajoId}/{anho}/{mes}/{solicitante}/{ip}` y
   `sendBono/{...}` deben seguir respondiendo con 5 segmentos. El front nuevo puede
   llamar las variantes sin solicitante/ip si el report service acepta 3 segmentos;
   si no, llamar con 5 segmentos poniendo el `solicitante` de sesión e IP vacía o
   `0.0.0.0` **solo** como placeholder de compatibilidad, sabiendo que la auditoría
   real la escribió core (E3/E4). **Coordinar: evitar doble registro en
   `bono_impresion`** (si report service inserta además de core). Alternativa limpia:
   report service deja de insertar y pasa a llamar core para auditar.
3. **Decisión D2 — quién es `legajoIdSolicitud`**: el core hoy no lee identidad
   alguna del request (no hay patrón `SecurityContextHolder`/header de usuario;
   verificado). Opciones, en orden de preferencia:
   - El gateway propaga el legajo del usuario autenticado en un header interno
     (ej. `X-Legajo-Solicitante`) y el core lo consume (robusto, no falsificable
     desde el browser). Requiere cambio menor en gateway.
   - El front envía `legajoIdSolicitud` en el body de E3/E5 tomado de su sesión
     (aceptable en red interna, falsificable; peor para auditoría).
4. **Regla de proyecto obligatoria**: sin `@Query` en repositorios. Para los
   chequeos de E1/E2 usar derived query methods (`findByLegajoIdAndAnhoAndMes`,
   `existsByLegajoIdAndAnhoAndMes`, `findAllByLegajoIdAndAnhoAndMesOrderByFechaDesc`).
   Verificar los nombres de columnas de `Item`, `CargoClaseDetalle` y
   `LegajoControl` antes de fijar las firmas.

---

## 6. Contrato final del front (objetivo)

Con E1/E4/E5 en pie, la pantalla `/consultas/bono-individual` queda así:

```
Persona search / documento / legajo  →  GET/PUT /contacto/{legajoId}
Verif. Integridad  →  GET  /bono/{legajoId}/{anho}/{mes}/integridad
Imprimir           →  POST /bono/{legajoId}/{anho}/{mes}/print-prepare
                      GET  /api/haberes/report/bono/generatePdf/{legajoId}/{anho}/{mes}  (blob)
Enviar             →  POST /bono/{legajoId}/{anho}/{mes}/send-prepare
                      (+ /sendBono/{...} o mailing interno del core, según D1)
```

Sin lógica de negocio, sin composición de reglas, sin parámetros de auditoría
armados por el lado cliente.

---

## 7. Checklist de implementación en core

- [ ] `BonoIndividualController` (facade) + `BonoIndividualService` + DTOs (`IntegridadBonoResponse`, `AuditoriaBonoRequest`)
- [ ] Derived methods necesarios en `JpaLiquidacionRepository`, `JpaItemRepository`, `JpaActividadRepository`, `JpaLegajoControlRepository`, `JpaCargoClaseDetalleRepository`, `JpaBonoImpresionRepository` (sin `@Query`)
- [ ] `ClientIpResolver` en `util` + propagación `X-Forwarded-For` confirmada en gateway/nginx
- [ ] Validador de mail (reutilizar si ya existe alguno en core; si no, `jakarta.validation` `@Pattern` en el DTO de E5)
- [ ] Tests: service (`@SpringBootTest` o unitario con mocks de repos) + controller (`@WebMvcTest`) cubriendo: legajo sin dependencia, faltantes de E1, recálculo de flags de E2 (docente/otras/clases), auditoría con IP derivada
- [ ] Coordinar con el equipo del report service las decisiones D1/D2 (doble escritura `bono_impresion`, segmentación de URL, mailing desde core)
- [ ] Documentar los endpoints en `docs/` si el repo mantiene un índice de API
