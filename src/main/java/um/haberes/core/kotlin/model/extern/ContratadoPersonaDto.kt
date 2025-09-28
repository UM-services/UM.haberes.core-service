package um.haberes.core.kotlin.model.extern

import java.math.BigDecimal

data class ContratadoPersonaDto(

    var uniqueId: String? = null,
    var personaId: BigDecimal? = null,
    var documentoId: Int? = null,
    var apellido: String? = null,
    var nombre: String? = null,
    var cuit: String? = null

)
