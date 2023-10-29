package um.haberes.rest.kotlin.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table
data class NovedadUpload(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var novedadUploadId: Long? = null,

    var legajoId: Long? = null,
    var anho: Int = 0,
    var mes: Int = 0,
    var codigoId: Int? = null,
    var dependenciaId: Int? = null,
    var importe: BigDecimal = BigDecimal.ZERO,
    var value: String = "",
    var pendiente: Byte = 0,

    @OneToOne(optional = true)
    @JoinColumn(name = "legajoId", insertable = false, updatable = false)
    var persona: Persona? = null,

    @OneToOne(optional = true)
    @JoinColumn(name = "codigoId", insertable = false, updatable = false)
    var codigo: Codigo? = null,

    @OneToOne(optional = true)
    @JoinColumn(name = "dependenciaId", insertable = false, updatable = false)
    var dependencia: Dependencia? = null

) : Auditable()
