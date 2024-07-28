package um.haberes.core.kotlin.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table
data class ConversionLog(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var version: Long? = null,
    var anho: Int = 0,
    val mes: Int = 0

) : Auditable()
