package um.haberes.core.hexagonal.personas.persona.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Cambios parciales sobre una persona: cada campo null significa "no modificar".
 * Se usa desde el endpoint PATCH para no reemplazar la fila completa como hace
 * el PUT existente.
 *
 * En los campos numéricos que la base admite nulos (obra social, AFIP, etc.) un
 * cero se interpreta como "borrar el valor" (queda null), igual que hacía la
 * aplicación anterior. Los campos primitivos se asignan tal cual.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonaPatch {

    private BigDecimal documento;
    private String apellido;
    private String nombre;
    private OffsetDateTime nacimiento;
    private OffsetDateTime altaDocente;
    private Integer ajusteDocente;
    private OffsetDateTime altaAdministrativa;
    private Integer ajusteAdministrativo;
    private String estadoCivil;
    private Integer situacionId;
    private Byte reemplazoDesarraigo;
    private Byte mitadDesarraigo;
    private String cuil;
    private Integer posgrado;
    private Integer estado;
    private String liquida;
    private Integer estadoAfip;
    private Integer dependenciaId;
    private String salida;
    private Long obraSocial;
    private Integer actividadAfip;
    private Integer localidadAfip;
    private Integer situacionAfip;
    private Integer modeloContratacionAfip;
    private Byte directivoEtec;

    public void applyTo(Persona persona) {
        if (documento != null) persona.setDocumento(documento);
        if (apellido != null) persona.setApellido(apellido);
        if (nombre != null) persona.setNombre(nombre);
        if (nacimiento != null) persona.setNacimiento(nacimiento);
        if (altaDocente != null) persona.setAltaDocente(altaDocente);
        if (ajusteDocente != null) persona.setAjusteDocente(ajusteDocente);
        if (altaAdministrativa != null) persona.setAltaAdministrativa(altaAdministrativa);
        if (ajusteAdministrativo != null) persona.setAjusteAdministrativo(ajusteAdministrativo);
        if (estadoCivil != null) persona.setEstadoCivil(estadoCivil);
        if (situacionId != null) persona.setSituacionId(situacionId == 0 ? null : situacionId);
        if (reemplazoDesarraigo != null) persona.setReemplazoDesarraigo(reemplazoDesarraigo);
        if (mitadDesarraigo != null) persona.setMitadDesarraigo(mitadDesarraigo);
        if (cuil != null) persona.setCuil(cuil);
        if (posgrado != null) persona.setPosgrado(posgrado);
        if (estado != null) persona.setEstado(estado);
        if (liquida != null) persona.setLiquida(liquida);
        if (estadoAfip != null) persona.setEstadoAfip(estadoAfip);
        if (dependenciaId != null) persona.setDependenciaId(dependenciaId == 0 ? null : dependenciaId);
        if (salida != null) persona.setSalida(salida);
        if (obraSocial != null) persona.setObraSocial(obraSocial == 0L ? null : obraSocial);
        if (actividadAfip != null) persona.setActividadAfip(actividadAfip == 0 ? null : actividadAfip);
        if (localidadAfip != null) persona.setLocalidadAfip(localidadAfip == 0 ? null : localidadAfip);
        if (situacionAfip != null) persona.setSituacionAfip(situacionAfip);
        if (modeloContratacionAfip != null) {
            persona.setModeloContratacionAfip(modeloContratacionAfip == 0 ? null : modeloContratacionAfip);
        }
        if (directivoEtec != null) persona.setDirectivoEtec(directivoEtec);
    }
}
