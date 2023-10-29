package um.haberes.rest.kotlin.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table
data class LegajoCodigoImputacion(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var legajoCodigoImputacionId: Long? = null,

    var legajoId: Long? = null,
    var anho: Int = 0,
    var mes: Int = 0,
    var dependenciaId: Int? = null,
    var facultadId: Int? = null,
    var geograficaId: Int? = null,
    var codigoId: Int? = null,
    var cuentaSueldos: BigDecimal? = null,
    var importe: BigDecimal = BigDecimal.ZERO,
    var cuentaAportes: BigDecimal? = null

) : Auditable()
