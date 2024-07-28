package um.haberes.core.kotlin.model.view

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import org.hibernate.annotations.Immutable
import um.haberes.core.kotlin.model.Auditable
import um.haberes.core.kotlin.model.Categoria
import um.haberes.core.kotlin.model.Dependencia
import um.haberes.core.kotlin.model.Persona
import java.math.BigDecimal
import java.time.OffsetDateTime

@Entity
@Table(name = "vw_cargo_liquidacion_periodo")
@Immutable
data class CargoLiquidacionPeriodo(

    @Id
    var cargoLiquidacionId: Long? = null,

    var legajoId: Long? = null,
    var anho: Int? = null,
    var mes: Int? = null,
    var dependenciaId: Int? = null,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    var fechaDesde: OffsetDateTime? = null,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    var fechaHasta: OffsetDateTime? = null,

    var categoriaId: Int? = null,
    var categoriaNombre: String? = null,
    var categoriaBasico: BigDecimal? = null,
    var jornada: Int = 0,
    var presentismo: Int = 0,
    var asignacionEspecialPermanente: BigDecimal = BigDecimal.ZERO,
    var situacion: String? = null,
    var periodo: Long? = null,

    @OneToOne(optional = true)
    @JoinColumn(name = "legajoId", insertable = false, updatable = false)
    var persona: Persona? = null,

    @OneToOne(optional = true)
    @JoinColumn(name = "dependenciaId", insertable = false, updatable = false)
    var dependencia: Dependencia? = null,

    @OneToOne(optional = true)
    @JoinColumn(name = "categoriaId", insertable = false, updatable = false)
    var categoria: Categoria? = null

) : Auditable()
