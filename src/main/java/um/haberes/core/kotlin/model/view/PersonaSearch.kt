package um.haberes.core.kotlin.model.view

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import org.hibernate.annotations.Immutable
import um.haberes.core.kotlin.model.Auditable
import um.haberes.core.kotlin.model.Dependencia
import java.math.BigDecimal
import java.time.OffsetDateTime

@Entity
@Table(name = "vw_persona_search")
@Immutable
data class PersonaSearch(

    @Id
    var legajoId: Long? = null,
    var documento: BigDecimal = BigDecimal.ZERO,
    var apellido: String = "",
    var nombre: String = "",

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    var nacimiento: OffsetDateTime? = null,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    var altaDocente: OffsetDateTime? = null,

    var ajusteDocente: Int = 0,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    var altaAdministrativa: OffsetDateTime? = null,

    var ajusteAdministrativo: Int = 0,

    var estadoCivil: String = "",
    var situacionId: Int? = null,
    var reemplazoDesarraigo: Byte = 0,
    var mitadDesarraigo: Byte = 0,
    var cuil: String = "",
    var posgrado: Int = 0,
    var estado: Int = 1,
    var liquida: String = "S",
    var estadoAfip: Int = 1,
    var dependenciaId: Int? = null,
    var salida: String? = null,
    var obraSocial: Long? = null,
    var actividadAfip: Int? = null,
    var localidadAfip: Int? = null,
    var situacionAfip: Int? = null,
    var modeloContratacionAfip: Int? = null,
    var search: String? = null,

    @OneToOne
    @JoinColumn(name = "dependenciaId", insertable = false, updatable = false)
    var dependencia: Dependencia? = null

) : Auditable()
