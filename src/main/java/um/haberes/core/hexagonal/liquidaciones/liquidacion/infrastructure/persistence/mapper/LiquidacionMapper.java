package um.haberes.core.hexagonal.liquidaciones.liquidacion.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.infrastructure.persistence.entity.LiquidacionEntity;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;
import um.haberes.core.hexagonal.personas.persona.infrastructure.persistence.entity.PersonaEntity;

@Component
public class LiquidacionMapper {

    public LiquidacionEntity toEntity(Liquidacion domain) {
        if (domain == null) {
            return null;
        }
        LiquidacionEntity entity = new LiquidacionEntity();
        entity.setLiquidacionId(domain.getLiquidacionId());
        entity.setLegajoId(domain.getLegajoId());
        if (domain.getAnho() != null) {
            entity.setAnho(domain.getAnho());
        }
        if (domain.getMes() != null) {
            entity.setMes(domain.getMes());
        }
        entity.setFechaLiquidacion(domain.getFechaLiquidacion());
        entity.setFechaAcreditacion(domain.getFechaAcreditacion());
        entity.setDependenciaId(domain.getDependenciaId());
        entity.setSalida(domain.getSalida());
        if (domain.getTotalRemunerativo() != null) {
            entity.setTotalRemunerativo(domain.getTotalRemunerativo());
        }
        if (domain.getTotalNoRemunerativo() != null) {
            entity.setTotalNoRemunerativo(domain.getTotalNoRemunerativo());
        }
        if (domain.getTotalDeduccion() != null) {
            entity.setTotalDeduccion(domain.getTotalDeduccion());
        }
        if (domain.getTotalNeto() != null) {
            entity.setTotalNeto(domain.getTotalNeto());
        }
        if (domain.getBloqueado() != null) {
            entity.setBloqueado(domain.getBloqueado());
        }
        if (domain.getEstado() != null) {
            entity.setEstado(domain.getEstado());
        }
        if (domain.getLiquida() != null) {
            entity.setLiquida(domain.getLiquida());
        }
        return entity;
    }

    public Liquidacion toDomain(LiquidacionEntity entity) {
        if (entity == null) {
            return null;
        }
        Liquidacion.LiquidacionBuilder builder = Liquidacion.builder()
                .liquidacionId(entity.getLiquidacionId())
                .legajoId(entity.getLegajoId())
                .anho(entity.getAnho())
                .mes(entity.getMes())
                .fechaLiquidacion(entity.getFechaLiquidacion())
                .fechaAcreditacion(entity.getFechaAcreditacion())
                .dependenciaId(entity.getDependenciaId())
                .salida(entity.getSalida())
                .estado(entity.getEstado());
        if (entity.getTotalRemunerativo() != null) {
            builder.totalRemunerativo(entity.getTotalRemunerativo());
        }
        if (entity.getTotalNoRemunerativo() != null) {
            builder.totalNoRemunerativo(entity.getTotalNoRemunerativo());
        }
        if (entity.getTotalDeduccion() != null) {
            builder.totalDeduccion(entity.getTotalDeduccion());
        }
        if (entity.getTotalNeto() != null) {
            builder.totalNeto(entity.getTotalNeto());
        }
        if (entity.getBloqueado() != null) {
            builder.bloqueado(entity.getBloqueado());
        }
        if (entity.getLiquida() != null) {
            builder.liquida(entity.getLiquida());
        }
        if (entity.getPersona() != null) {
            builder.persona(toDomainPersona(entity.getPersona()));
        }
        return builder.build();
    }

    private Persona toDomainPersona(PersonaEntity persona) {
        if (persona == null) {
            return null;
        }
        Persona.PersonaBuilder builder = Persona.builder()
                .legajoId(persona.getLegajoId())
                .nacimiento(persona.getNacimiento())
                .altaDocente(persona.getAltaDocente())
                .altaAdministrativa(persona.getAltaAdministrativa())
                .ajusteDocente(persona.getAjusteDocente())
                .ajusteAdministrativo(persona.getAjusteAdministrativo())
                .situacionId(persona.getSituacionId())
                .posgrado(persona.getPosgrado())
                .estado(persona.getEstado())
                .estadoAfip(persona.getEstadoAfip())
                .dependenciaId(persona.getDependenciaId())
                .salida(persona.getSalida())
                .obraSocial(persona.getObraSocial())
                .actividadAfip(persona.getActividadAfip())
                .localidadAfip(persona.getLocalidadAfip())
                .situacionAfip(persona.getSituacionAfip())
                .modeloContratacionAfip(persona.getModeloContratacionAfip());
        if (persona.getDocumento() != null) {
            builder.documento(persona.getDocumento());
        }
        if (persona.getApellido() != null) {
            builder.apellido(persona.getApellido());
        }
        if (persona.getNombre() != null) {
            builder.nombre(persona.getNombre());
        }
        if (persona.getEstadoCivil() != null) {
            builder.estadoCivil(persona.getEstadoCivil());
        }
        if (persona.getReemplazoDesarraigo() != null) {
            builder.reemplazoDesarraigo(persona.getReemplazoDesarraigo());
        }
        if (persona.getMitadDesarraigo() != null) {
            builder.mitadDesarraigo(persona.getMitadDesarraigo());
        }
        if (persona.getCuil() != null) {
            builder.cuil(persona.getCuil());
        }
        if (persona.getLiquida() != null) {
            builder.liquida(persona.getLiquida());
        }
        if (persona.getDirectivoEtec() != null) {
            builder.directivoEtec(persona.getDirectivoEtec());
        }
        return builder.build();
    }
}
