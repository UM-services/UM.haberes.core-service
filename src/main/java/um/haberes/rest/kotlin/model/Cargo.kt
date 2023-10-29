package um.haberes.rest.kotlin.model

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.OffsetDateTime

@Entity
@Table
data class Cargo(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var cargoId: Long? = null,

    var legajoId: Long? = null,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    var fechaAlta: OffsetDateTime? = null,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    var fechaBaja: OffsetDateTime? = null,

    var dependenciaId: Int? = null,
    var categoriaId: Int? = null,
    var jornada: Int = 0,
    var presentismo: Int = 0,
    var horasJornada: BigDecimal = BigDecimal.ZERO,

    @OneToOne
    @JoinColumn(name = "legajoId", insertable = false, updatable = false)
    var persona: Persona? = null,

    @OneToOne
    @JoinColumn(name = "dependenciaId", insertable = false, updatable = false)
    var dependencia: Dependencia? = null,

    @OneToOne
    @JoinColumn(name = "categoriaId", insertable = false, updatable = false)
    var categoria: Categoria? = null

) : Auditable()
