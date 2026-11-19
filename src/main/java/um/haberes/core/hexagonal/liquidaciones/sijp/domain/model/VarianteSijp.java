package um.haberes.core.hexagonal.liquidaciones.sijp.domain.model;

import java.util.Locale;

public enum VarianteSijp {

    NORMAL,
    ETEC;

    public static VarianteSijp from(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("La variante SIJP es obligatoria (normal o etec)");
        }
        try {
            return valueOf(value.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Variante SIJP inválida: " + value + " (use normal o etec)");
        }
    }

}
