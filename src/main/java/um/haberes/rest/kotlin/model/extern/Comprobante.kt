package um.haberes.rest.kotlin.model.extern

import jakarta.persistence.Column

data class Comprobante(

    var comprobanteId: Int? = null,
    var descripcion: String = "",
    var tipoTransaccionId: Int? = null,
    var ordenPago: Byte = 0,
    var aplicaPendiente: Byte = 0,
    var cuentaCorriente: Byte = 0,
    var debita: Byte = 0,
    var diasVigencia: Long = 0,
    var facturacionElectronica: Byte = 0,
    var comprobanteAfipId: Int? = null,
    var puntoVenta: Int? = null,
    var letraComprobante: String? = null

)
