package um.haberes.core.util.transfer;

/**
 * Resultado de la transferencia de novedades del mes anterior.
 *
 * @param nuevas      filas creadas en el período destino
 * @param actualizadas filas que existían en el destino con datos distintos y se actualizaron
 * @param sinCambios  filas que ya estaban transferidas tal cual (no se escribieron)
 * @param total       filas procesadas del período de origen ({@code nuevas + actualizadas + sinCambios})
 */
public record NovedadesTransferencia(int nuevas, int actualizadas, int sinCambios, int total) {
}
