package um.haberes.core.hexagonal.seguridad.infrastructure.security;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Exige que el usuario tenga alguna de las claves indicadas (OR) para ejecutar el
 * endpoint. Si no se declara, el permiso se resuelve con {@link RutasPermisos}.
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequierePermiso {

    String[] value();
}
