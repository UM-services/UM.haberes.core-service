package um.haberes.rest.kotlin.model

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
@Table
data class Usuario(

    @Id
    var legajoId: Long? = null,
    var password: String = "",

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    var lastLog: OffsetDateTime? = null,

    var usuarioId: Long? = null

) : Auditable()
