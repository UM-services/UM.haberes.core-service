package um.haberes.rest.kotlin.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["legajoId", "anho", "mes", "codigoId", "dependenciaId"])])
data class Novedad(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var novedadId: Long? = null,

    var legajoId: Long? = null,
    var anho: Int = 0,
    var mes: Int = 0,
    var codigoId: Int? = null,
    var dependenciaId: Int? = null,
    var importe: BigDecimal = BigDecimal.ZERO,
    var value: String = "",
    var observaciones: String? = null,
    var importado: Byte = 0,
    var novedadUploadId: Long? = null,

    @OneToOne
    @JoinColumn(name = "legajoId", insertable = false, updatable = false)
    var persona: Persona? = null,

    @OneToOne
    @JoinColumn(name = "codigoId", insertable = false, updatable = false)
    var codigo: Codigo? = null,

    @OneToOne
    @JoinColumn(name = "dependenciaId", insertable = false, updatable = false)
    var dependencia: Dependencia? = null

) : Auditable()
