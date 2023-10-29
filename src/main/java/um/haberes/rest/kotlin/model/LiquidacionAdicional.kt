package um.haberes.rest.kotlin.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table
data class LiquidacionAdicional(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var liquidacionAdicionalId: Long? = null,
    var legajoId: Long? = null,
    var anho: Int? = null,
    var mes: Int? = null,
    var dependenciaId: Int? = null,
    var adicional: BigDecimal = BigDecimal.ZERO,

    @OneToOne
    @JoinColumn(name = "legajoId", insertable = false, updatable = false)
    var persona: Persona? = null,

    @OneToOne
    @JoinColumn(name = "dependenciaId", insertable = false, updatable = false)
    var dependencia: Dependencia? = null

) : Auditable()
