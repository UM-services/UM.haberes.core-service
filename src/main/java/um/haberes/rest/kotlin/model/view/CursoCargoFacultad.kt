package um.haberes.rest.kotlin.model.view

import jakarta.persistence.*
import org.hibernate.annotations.Immutable
import um.haberes.rest.kotlin.model.*
import java.math.BigDecimal

@Entity
@Table(name = "vw_curso_cargo_facultad")
@Immutable
data class CursoCargoFacultad(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var cursoCargoId: Long? = null,

    var cursoId: Long? = null,
    var anho: Int? = null,
    var mes: Int? = null,
    var cargoTipoId: Int? = null,
    var legajoId: Long? = null,
    var horasSemanales: BigDecimal? = null,
    var horasTotales: BigDecimal? = null,
    var designacionTipoId: Int? = null,
    var categoriaId: Int? = null,
    var desarraigo: Byte? = null,
    var cursoCargoNovedadId: Long? = null,
    var facultadId: Int? = null,
    var geograficaId: Int? = null,
    var anual: Byte? = null,
    var semestre1: Byte? = null,
    var semestre2: Byte? = null,

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

)
