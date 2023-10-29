package um.haberes.rest.kotlin.model

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import java.math.BigDecimal
import java.text.MessageFormat
import java.time.OffsetDateTime

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["documento"])])
data class Persona(

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
    var estado: Int = 0,
    var liquida: String = "",
    var estadoAfip: Int = 0,
    var dependenciaId: Int? = null,
    var salida: String? = null,
    var obraSocial: Long? = null,
    var actividadAfip: Int? = null,
    var localidadAfip: Int? = null,
    var situacionAfip: Int = 0,
    var modeloContratacionAfip: Int? = null,
    var directivoEtec: Byte = 0,

    @OneToOne(optional = true)
    @JoinColumn(name = "dependenciaId", insertable = false, updatable = false)
    var dependencia: Dependencia? = null,

    @OneToOne(optional = true)
    @JoinColumn(name = "situacionAfip", insertable = false, updatable = false)
    var afipSituacion: AfipSituacion? = null,

) : Auditable() {
    fun getApellidoNombre(): String {
        return MessageFormat.format("{0}, {1}", this.apellido, this.nombre)
    }

}
