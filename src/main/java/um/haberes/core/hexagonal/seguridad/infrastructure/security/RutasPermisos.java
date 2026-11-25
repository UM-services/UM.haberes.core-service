package um.haberes.core.hexagonal.seguridad.infrastructure.security;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * Mapa central ruta → permisos. Evita anotar 100+ controladores existentes y deja
 * el enforcement completo en un solo lugar, fácil de auditar.
 *
 * <p>Una regla puede declarar varias claves: el usuario pasa si tiene
 * <b>cualquiera</b> de ellas (OR). Las rutas más específicas se evalúan primero.
 */
@Component
public class RutasPermisos {

    public record Regla(String metodo, String prefijo, List<String> claves) {
    }

    private static final List<Regla> REGLAS = ordenar(List.of(
            // Lecturas compartidas que deben ganarle a una regla de escritura
            // declarada antes en la lista (mismo prefijo).
            get("/api/haberes/core/cargotipo", List.of("comun.catalogos.consultar")),
            get("/api/haberes/core/designaciontipo", List.of("comun.catalogos.consultar")),
            get("/api/haberes/core/personaFacultad", List.of("comun.personas.consultar")),

            // --- Seguridad (las rutas más específicas primero) ---
            any("/api/haberes/core/auth", List.of()),
            any("/api/haberes/core/permisoEfectivo", List.of()),
            any("/api/haberes/core/permiso", List.of("seguridad.catalogo.administrar")),
            any("/api/haberes/core/rolPermiso", List.of("seguridad.roles.administrar")),
            any("/api/haberes/core/rol", List.of("seguridad.roles.administrar", "seguridad.permisos.administrar")),
            any("/api/haberes/core/usuarioRol", List.of("seguridad.permisos.administrar")),
            any("/api/haberes/core/usuarioPermiso", List.of("seguridad.permisos.administrar")),

            // --- Novedades / designaciones (comparten catálogos) ---
            any("/api/haberes/core/anotador", List.of("novedades.anotador.gestionar", "designaciones.anotador.gestionar")),
            any("/api/haberes/core/cursoCargo", List.of("novedades.asig_cursos.gestionar", "designaciones.asig_cursos.gestionar")),
            any("/api/haberes/core/cursofusion", List.of("designaciones.fusionar_todos.procesar", "designaciones.general.gestionar")),
            any("/api/haberes/core/cursodesarraigo", List.of("novedades.asig_cursos.gestionar", "designaciones.asig_cursos.gestionar")),
            any("/api/haberes/core/curso", List.of("novedades.asig_cursos.gestionar", "designaciones.asig_cursos.gestionar")),
            any("/api/haberes/core/cargotipo", List.of("designaciones.general.gestionar", "liquidacion.asig_cargos.gestionar")),
            any("/api/haberes/core/designaciontipo", List.of("designaciones.general.gestionar")),
            any("/api/haberes/core/docenteDesignacion", List.of("novedades.designaciones.ver", "consultas.designacion_docentes.ver")),

            // --- Liquidación ---
            get("/api/haberes/core/cargoliquidacion", List.of(
                    "liquidacion.asig_cargos.gestionar", "liquidacion.individual.procesar",
                    "liquidacion.general.procesar", "liquidacion.etec.asignaciones.gestionar",
                    "novedades.cargos.ver", "novedades.designaciones.ver",
                    "novedades.asig_cursos.gestionar", "novedades.anotador.gestionar",
                    "consultas.*")),
            any("/api/haberes/core/cargoliquidacion", List.of(
                    "liquidacion.asig_cargos.gestionar", "liquidacion.individual.procesar",
                    "liquidacion.general.procesar", "liquidacion.etec.asignaciones.gestionar")),
            any("/api/haberes/core/liquidacion-individual", List.of("liquidacion.individual.procesar")),
            any("/api/haberes/core/liquidacion-general", List.of("liquidacion.general.procesar")),
            any("/api/haberes/core/liquidacion-etec", List.of("liquidacion.etec.asignaciones.gestionar")),
            any("/api/haberes/core/novedadupload", List.of("liquidacion.novedades.gestionar")),
            any("/api/haberes/core/novedadfile", List.of("liquidacion.novedades.gestionar")),
            any("/api/haberes/core/novedadacumulado", List.of("liquidacion.novedades.gestionar", "consultas.totales_generales.ver")),
            any("/api/haberes/core/novedadExiste", List.of("liquidacion.novedades.gestionar", "consultas.totales_generales.ver")),
            any("/api/haberes/core/novedad", List.of(
                    "liquidacion.novedades.gestionar", "novedades.anotador.gestionar",
                    "novedades.designaciones.ver")),
            any("/api/haberes/core/sijp", List.of("liquidacion.sijp.generar")),
            any("/api/haberes/core/item", List.of("liquidacion.individual.procesar", "liquidacion.general.procesar")),
            any("/api/haberes/core/letra", List.of("liquidacion.individual.procesar", "liquidacion.general.procesar")),
            any("/api/haberes/core/liquidacion", List.of(
                    "liquidacion.individual.procesar", "liquidacion.general.procesar",
                    "consultas.totales_generales.ver")),
            get("/api/haberes/core/cargo", List.of(
                    "liquidacion.asig_cargos.gestionar", "designaciones.general.gestionar",
                    "novedades.cargos.ver", "novedades.asig_cursos.gestionar",
                    "novedades.designaciones.ver")),
            any("/api/haberes/core/cargo", List.of(
                    "liquidacion.asig_cargos.gestionar", "designaciones.general.gestionar")),

            // --- Etec ---
            any("/api/haberes/core/etec", List.of("liquidacion.etec.asignaciones.gestionar")),

            // --- Herramientas de designaciones/cargos (también las usa Novedades) ---
            get("/api/haberes/core/designaciontool", List.of(
                    "designaciones.general.gestionar", "designaciones.asig_cursos.gestionar",
                    "novedades.designaciones.ver", "novedades.asig_cursos.gestionar",
                    "consultas.*")),
            any("/api/haberes/core/designaciontool", List.of(
                    "designaciones.general.gestionar", "designaciones.asig_cursos.gestionar")),
            get("/api/haberes/core/cargoclasetool", List.of(
                    "designaciones.cargos_clase.gestionar", "novedades.designaciones.ver")),
            any("/api/haberes/core/cargoclasetool", List.of("designaciones.cargos_clase.gestionar")),
            any("/api/haberes/core/cursocargonovedad", List.of(
                    "novedades.asig_cursos.gestionar", "designaciones.asig_cursos.gestionar",
                    "liquidacion.novedades.gestionar")),
            any("/api/haberes/core/cursoCargoContratado", List.of(
                    "designaciones.cursos.ver", "consultas.cursos_contratado.ver",
                    "liquidacion.general.procesar")),

            // --- Consultas / reportes ---
            any("/api/haberes/core/bono", List.of("consultas.bono_individual.ver", "consultas.bonos_dependencia.ver")),
            any("/api/haberes/core/total", List.of("consultas.totales_generales.ver", "consultas.totales_codigo.ver")),
            any("/api/haberes/core/novedadacumulado", List.of("consultas.totales_generales.ver")),
            any("/api/haberes/core/legajocursoCantidad", List.of("consultas.*")),
            any("/api/haberes/core/asignadocategoria", List.of("consultas.*")),
            any("/api/haberes/core/asignadoclase", List.of("consultas.*")),
            any("/api/haberes/core/imputado", List.of("consultas.*", "contabilidad.*")),
            any("/api/haberes/core/cargoLiquidacion", List.of("consultas.*", "liquidacion.*")),
            any("/api/haberes/core/libroSueldo", List.of("consultas.libro_sueldos.ver", "liquidacion.general.procesar")),

            // --- Contabilidad ---
            any("/api/haberes/core/legajocontabilidad", List.of("contabilidad.*")),
            any("/api/haberes/core/legajocargoclaseimputacion", List.of("contabilidad.*")),
            any("/api/haberes/core/legajocategoriaimputacion", List.of("contabilidad.*")),
            any("/api/haberes/core/legajocodigoimputacion", List.of("contabilidad.*")),
            any("/api/haberes/core/cargoclaseimputacion", List.of("contabilidad.*")),
            any("/api/haberes/core/categoriaimputacion", List.of("contabilidad.*")),
            any("/api/haberes/core/codigoimputacion", List.of("contabilidad.*")),
            any("/api/haberes/core/contable", List.of("contabilidad.*")),

            // --- Movimientos y bancos ---
            any("/api/haberes/core/acreditacion", List.of("movimientos.*")),
            any("/api/haberes/core/acreditacionpago", List.of("movimientos.*")),
            any("/api/haberes/core/legajobanco/preparar", List.of("movimientos.*")),
            any("/api/haberes/core/excluido", List.of("movimientos.excluidos.gestionar")),

            // --- Personas ---
            post("/api/haberes/core/persona/search", List.of("comun.personas.consultar")),
            get("/api/haberes/core/persona", List.of("comun.personas.consultar")),
            any("/api/haberes/core/persona", List.of("administrador.personas.editar", "novedades.asig_cursos.gestionar")),
            any("/api/haberes/core/personaFacultad", List.of(
                    "administrador.personas.editar", "designaciones.general.gestionar",
                    "novedades.asig_cursos.gestionar")),
            any("/api/haberes/core/contacto", List.of("administrador.personas.editar", "liquidacion.novedades.gestionar")),
            get("/api/haberes/core/legajobanco", List.of(
                    "administrador.personas.editar", "movimientos.*", "novedades.cargos.ver")),
            any("/api/haberes/core/legajobanco", List.of(
                    "administrador.personas.editar", "movimientos.*")),

            // --- Lecturas de catálogos de referencia (cualquier usuario autenticado) ---
            get("/api/haberes/core/categoria", List.of("comun.catalogos.consultar")),
            get("/api/haberes/core/codigo", List.of("comun.catalogos.consultar")),
            get("/api/haberes/core/codigogrupo", List.of("comun.catalogos.consultar")),
            get("/api/haberes/core/clase", List.of("comun.catalogos.consultar")),
            get("/api/haberes/core/cargoclase", List.of("comun.catalogos.consultar")),
            get("/api/haberes/core/cargoclasedetalle", List.of("comun.catalogos.consultar")),
            get("/api/haberes/core/cargoclaseperiodo", List.of("comun.catalogos.consultar")),
            get("/api/haberes/core/geografica", List.of("comun.catalogos.consultar")),
            get("/api/haberes/core/facultad", List.of("comun.catalogos.consultar")),
            get("/api/haberes/core/dependencia", List.of("comun.catalogos.consultar")),
            get("/api/haberes/core/nivel", List.of("comun.catalogos.consultar")),
            get("/api/haberes/core/situacion", List.of("comun.catalogos.consultar")),
            get("/api/haberes/core/actividad", List.of("comun.catalogos.consultar")),
            get("/api/haberes/core/antiguedad", List.of("comun.catalogos.consultar")),
            get("/api/haberes/core/afipConceptoSueldo", List.of("comun.catalogos.consultar")),
            get("/api/haberes/core/afipSituacion", List.of("comun.catalogos.consultar")),
            get("/api/haberes/core/modoLiquidacion", List.of("comun.catalogos.consultar")),
            get("/api/haberes/core/seguridadSocial", List.of("comun.catalogos.consultar")),
            get("/api/haberes/core/lectivo", List.of("comun.catalogos.consultar")),
            get("/api/haberes/core/cargotipo", List.of("comun.catalogos.consultar")),
            get("/api/haberes/core/designaciontipo", List.of("comun.catalogos.consultar")),
            get("/api/haberes/core/adicionalCurso", List.of("comun.catalogos.consultar")),
            get("/api/haberes/core/control", List.of("comun.catalogos.consultar")),
            get("/api/haberes/core/legajocontrol", List.of("comun.catalogos.consultar")),
            get("/api/haberes/core/personaFacultad", List.of("comun.personas.consultar")),

            // --- Administrador: catálogos (escritura) ---
            any("/api/haberes/core/adicionalCurso", List.of("administrador.adicionales.editar")),
            any("/api/haberes/core/legajocontrol", List.of("administrador.limite_novedades.editar")),
            any("/api/haberes/core/control", List.of("administrador.control_periodo.editar", "liquidacion.general.procesar")),
            any("/api/haberes/core/geografica", List.of("administrador.sedes.editar", "administrador.personas.editar")),
            any("/api/haberes/core/cargoclase", List.of("administrador.cargos_clase.editar", "designaciones.cargos_clase.gestionar")),
            any("/api/haberes/core/clase", List.of("administrador.clases.editar")),
            any("/api/haberes/core/codigo", List.of("administrador.codigos.editar")),
            any("/api/haberes/core/categoria", List.of("administrador.categorias.editar")),
            any("/api/haberes/core/facultad", List.of("administrador.personas.editar", "administrador.sedes.editar")),
            any("/api/haberes/core/dependencia", List.of("administrador.personas.editar", "novedades.asig_cursos.gestionar")),

            any("/api/haberes/core/codigogrupo", List.of("administrador.codigos.editar")),
            any("/api/haberes/core/cargoclasedetalle", List.of(
                    "administrador.cargos_clase.editar", "designaciones.cargos_clase.gestionar")),
            any("/api/haberes/core/cargoclaseperiodo", List.of(
                    "administrador.cargos_clase.editar", "designaciones.cargos_clase.gestionar")),
            any("/api/haberes/core/nivel", List.of("administrador.categorias.editar")),
            any("/api/haberes/core/situacion", List.of("administrador.categorias.editar")),
            any("/api/haberes/core/actividad", List.of("administrador.categorias.editar")),
            any("/api/haberes/core/antiguedad", List.of("administrador.categorias.editar")),
            any("/api/haberes/core/afipConceptoSueldo", List.of("administrador.codigos.editar")),
            any("/api/haberes/core/afipSituacion", List.of("administrador.codigos.editar")),
            any("/api/haberes/core/modoLiquidacion", List.of("administrador.categorias.editar")),
            any("/api/haberes/core/seguridadSocial", List.of("administrador.categorias.editar")),
            any("/api/haberes/core/lectivo", List.of("administrador.categorias.editar")),
            get("/api/haberes/core/ejercicio", List.of("comun.catalogos.consultar", "contabilidad.*")),
            any("/api/haberes/core/ejercicio", List.of("contabilidad.*", "administrador.categorias.editar")),
            get("/api/haberes/core/cuenta", List.of("comun.catalogos.consultar", "contabilidad.*")),
            any("/api/haberes/core/cuenta", List.of("contabilidad.*", "administrador.categorias.editar")),
            get("/api/haberes/core/contratado", List.of(
                    "comun.catalogos.consultar", "liquidacion.*", "consultas.*")),
            any("/api/haberes/core/contratado", List.of("liquidacion.*", "consultas.*")),
            any("/api/haberes/core/contratadoPersona", List.of("liquidacion.*", "consultas.*")),
            any("/api/haberes/core/totalitem", List.of(
                    "consultas.totales_generales.ver", "consultas.totales_codigo.ver")),
            any("/api/haberes/core/totalmensual", List.of(
                    "consultas.totales_generales.ver", "consultas.totales_codigo.ver")),
            any("/api/haberes/core/totalsalida", List.of(
                    "consultas.totales_generales.ver", "consultas.totales_codigo.ver")),
            any("/api/haberes/core/totalnovedad", List.of(
                    "liquidacion.novedades.gestionar", "consultas.totales_generales.ver")),
            any("/api/haberes/core/imputadoaporte", List.of("consultas.*", "contabilidad.*")),
            any("/api/haberes/core/imputadosueldo", List.of("consultas.*", "contabilidad.*")),
            any("/api/haberes/core/imputadototal", List.of("consultas.*", "contabilidad.*")),
            any("/api/haberes/core/cargoLiquidacionDocente", List.of("consultas.*", "liquidacion.*")),
            any("/api/haberes/core/cargoLiquidacionNoDocente", List.of("consultas.*", "liquidacion.*")),
            any("/api/haberes/core/formularioAsignacionCargo", List.of("liquidacion.*", "consultas.*")),
            any("/api/haberes/core/formularioImprimir", List.of("liquidacion.*", "consultas.*")),
            any("/api/haberes/core/makeLiquidacionGeneral", List.of("liquidacion.general.procesar")),

            // --- Catálogos compartidos (lectura abierta a usuarios autenticados) ---
            any("/api/haberes/core/nivel", List.of("comun.catalogos.consultar")),
            any("/api/haberes/core/situacion", List.of("comun.catalogos.consultar")),
            any("/api/haberes/core/actividad", List.of("comun.catalogos.consultar")),
            any("/api/haberes/core/antiguedad", List.of("comun.catalogos.consultar")),
            any("/api/haberes/core/afip", List.of("comun.catalogos.consultar")),
            any("/api/haberes/core/modoLiquidacion", List.of("comun.catalogos.consultar")),
            any("/api/haberes/core/seguridadSocial", List.of("comun.catalogos.consultar")),
            any("/api/haberes/core/lectivo", List.of("comun.catalogos.consultar")),

            // --- Herramientas y procesos ---
            any("/api/haberes/core/mailing", List.of("liquidacion.general.procesar", "consultas.*")),
            any("/api/haberes/core/makeLiquidacion", List.of("liquidacion.general.procesar", "liquidacion.individual.procesar")),
            any("/api/haberes/core/ordenPago", List.of("liquidacion.general.procesar", "contabilidad.*")),
            any("/api/haberes/core/sheet", List.of("liquidacion.general.procesar", "consultas.*")),
            any("/api/haberes/core/tool", List.of("liquidacion.general.procesar", "designaciones.general.gestionar")),
            any("/api/haberes/core/formulario", List.of("liquidacion.general.procesar", "consultas.*")),
            any("/api/haberes/core/build", List.of("administrador.categorias.editar")),
            any("/api/haberes/core/conversionlog", List.of("administrador.categorias.editar")),

            // --- Usuarios: lo administrativo; el resto lo resuelve el ownership ---
            any("/api/haberes/core/usuario/setpassword", List.of("administrador.usuarios.administrar")),
            any("/api/haberes/core/usuario/isuservalid", List.of()),
            any("/api/haberes/core/usuario/lastlog", List.of()),
            any("/api/haberes/core/usuario/cambiarclave", List.of()),
            any("/api/haberes/core/usuario", List.of("administrador.usuarios.administrar"))
    ));

    public Optional<List<String>> requeridas(String metodo, String path) {
        String m = metodo == null ? "" : metodo.toUpperCase(Locale.ROOT);
        for (Regla regla : REGLAS) {
            if (regla.metodo() != null && !regla.metodo().equals(m)) {
                continue;
            }
            if (path.equals(regla.prefijo()) || path.startsWith(regla.prefijo() + "/")
                    || path.startsWith(regla.prefijo() + "?")
                    || (regla.prefijo().endsWith("/") && path.startsWith(regla.prefijo()))) {
                return Optional.of(regla.claves());
            }
        }
        return Optional.empty();
    }

    private static List<Regla> ordenar(List<Regla> reglas) {
        List<Regla> copia = new ArrayList<>(reglas);
        copia.sort(Comparator.comparingInt((Regla regla) -> regla.prefijo().length()).reversed());
        return List.copyOf(copia);
    }

    private static Regla any(String prefijo, List<String> claves) {
        return new Regla(null, prefijo, claves);
    }

    private static Regla get(String prefijo, List<String> claves) {
        return new Regla("GET", prefijo, claves);
    }

    private static Regla post(String prefijo, List<String> claves) {
        return new Regla("POST", prefijo, claves);
    }
}
