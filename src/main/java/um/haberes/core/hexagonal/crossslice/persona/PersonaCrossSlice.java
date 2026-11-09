package um.haberes.core.hexagonal.crossslice.persona;

import java.util.Optional;

import um.haberes.core.hexagonal.crossslice.persona.model.PersonaView;

/** Contrato de cross-slice para leer y corregir datos acotados de una persona. */
public interface PersonaCrossSlice {

    Optional<PersonaView> porLegajo(Long legajoId);

    /**
     * Marca la persona como no liquidable ({@code liquida = "N"}).
     *
     * <p>Es la corrección que aplica la regla de elegibilidad cuando la persona no está en
     * estado liquidable y la liquidación anterior sí lo estaba.
     */
    void marcarNoLiquida(Long legajoId);
}
