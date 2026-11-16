package um.haberes.core.hexagonal.liquidaciones.reglas;

/** Resultado neutral de una regla de elegibilidad, con mensaje listo para mostrar. */
public record Inelegibilidad(MotivoInelegibilidad motivo, String mensaje) {

    public static Inelegibilidad de(MotivoInelegibilidad motivo, String mensaje) {
        return new Inelegibilidad(motivo, mensaje);
    }
}
