package um.haberes.core.kotlin.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table
data class LegajoCargoClaseImputacion(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var legajoCargoClaseImputacionId: Long? = null,

    var legajoId: Long? = null,
    var anho: Int = 0,
    var mes: Int = 0,
    var dependenciaId: Int? = null,
    var facultadId: Int? = null,
    var geograficaId: Int? = null,
    var cargoClaseId: Long? = null,
    var cuentaSueldos: BigDecimal? = null,
    var basico: BigDecimal = BigDecimal.ZERO,
    var antiguedad: BigDecimal = BigDecimal.ZERO,
    var cuentaAportes: BigDecimal? = null

) : Auditable()
