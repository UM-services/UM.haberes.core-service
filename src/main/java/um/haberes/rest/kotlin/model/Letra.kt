package um.haberes.rest.kotlin.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["legajoId", "anho", "mes"])])
data class Letra(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var letraId: Long? = null,

    var legajoId: Long? = null,
    var anho: Int = 0,
    var mes: Int = 0,
    var neto: BigDecimal = BigDecimal.ZERO,
    var cadena: String = ""

) : Auditable()
