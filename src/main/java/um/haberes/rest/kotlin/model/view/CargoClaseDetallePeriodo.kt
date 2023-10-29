package um.haberes.rest.kotlin.model.view

import jakarta.persistence.*
import org.hibernate.annotations.Immutable
import um.haberes.rest.kotlin.model.Auditable
import java.math.BigDecimal

@Entity
@Table(name = "vw_cargo_clase_detalle_periodo")
@Immutable
data class CargoClaseDetallePeriodo(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var cargoClaseDetalleId: Long? = null,

    var legajoId: Long? = null,
    var anho: Int? = null,
    var mes: Int? = null,
    var cargoClaseId: Long? = null,
    var dependenciaId: Int? = null,
    var facultadId: Int? = null,
    var geograficaId: Int? = null,
    var horas: Int? = null,
    var valorHora: BigDecimal? = null,
    var cargoClasePeriodoId: Long? = null,
    var liquidado: Byte? = null,
    var periodo: Long? = null

) : Auditable()
