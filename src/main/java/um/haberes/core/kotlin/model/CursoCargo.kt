package um.haberes.core.kotlin.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["cursoId", "anho", "mes", "cargoTipoId", "legajoId"])])
data class CursoCargo(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var cursoCargoId: Long? = null,

    var cursoId: Long? = null,
    var anho: Int = 0,
    var mes: Int = 0,
    var cargoTipoId: Int? = null,
    var legajoId: Long? = null,
    var horasSemanales: BigDecimal = BigDecimal.ZERO,
    var horasTotales: BigDecimal = BigDecimal.ZERO,
    var designacionTipoId: Int? = null,
    var categoriaId: Int? = null,
    var desarraigo: Byte = 0,
    var cursoCargoNovedadId: Long? = null,

    @OneToOne
    @JoinColumn(name = "cursoId", insertable = false, updatable = false)
    var curso: Curso? = null,

    @OneToOne
    @JoinColumn(name = "cargoTipoId", insertable = false, updatable = false)
    var cargoTipo: CargoTipo? = null,

    @OneToOne
    @JoinColumn(name = "legajoId", insertable = false, updatable = false)
    var persona: Persona? = null,

    @OneToOne
    @JoinColumn(name = "designacionTipoId", insertable = false, updatable = false)
    var designacionTipo: DesignacionTipo? = null,

    @OneToOne
    @JoinColumn(name = "categoriaId", insertable = false, updatable = false)
    var categoria: Categoria? = null

) : Auditable()
