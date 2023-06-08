package um.haberes.rest.kotlin.model.extern

import java.math.BigDecimal

data class ContratadoPersona(

    var contratadoId: Long? = null,
    var personaId: BigDecimal? = null,
    var documentoId: Int? = null,
    var apellido: String? = null,
    var nombre: String? = null

)
