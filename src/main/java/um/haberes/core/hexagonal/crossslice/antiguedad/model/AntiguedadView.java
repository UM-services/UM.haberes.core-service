package um.haberes.core.hexagonal.crossslice.antiguedad.model;

import java.math.BigDecimal;

/**
 * Vista de antigüedad de un legajo en un período.
 *
 * <p>{@code mesesDocentes}/{@code mesesAdministrativos} pueden ser {@code null} cuando todavía no
 * existe el registro del período; los índices pueden venir vacíos.
 */
public record AntiguedadView(Integer mesesDocentes, Integer mesesAdministrativos,
                             BigDecimal indiceDocente, BigDecimal indiceAdministrativo) {
}
