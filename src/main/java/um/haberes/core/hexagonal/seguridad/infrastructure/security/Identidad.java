package um.haberes.core.hexagonal.seguridad.infrastructure.security;

/**
 * Identidad resuelta de una request. `legajoId` es el usuario autenticado;
 * `servicio` es el nombre del servicio interno cuando la llamada es
 * service-to-service (en ese caso `legajoId` es null).
 */
public record Identidad(Long legajoId, String servicio) {

    public static final String ATRIBUTO = "haberes.identidad";

    public static Identidad usuario(Long legajoId) {
        return new Identidad(legajoId, null);
    }

    public static Identidad servicio(String nombre) {
        return new Identidad(null, nombre);
    }

    public boolean esServicio() {
        return servicio != null;
    }
}
