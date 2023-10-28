package um.haberes.rest.kotlin.model

import jakarta.persistence.*
import um.haberes.rest.model.Auditable
import java.math.BigDecimal

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["cursoId", "anho", "mes", "cargoTipoId", "legajoId"])])
data class CursoCargoNovedad(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var cursoCargoNovedadId: Long? = null,

    var cursoId: Long? = null,
    var anho: Int = 0,
    var mes: Int = 0,
    var cargoTipoId: Int? = null,
    var legajoId: Long? = null,
    var horasSemanales: BigDecimal = BigDecimal.ZERO,
    var horasTotales: BigDecimal = BigDecimal.ZERO,
    var desarraigo: Byte = 0,
    var alta: Byte = 0,
    var baja: Byte = 0,
    var cambio: Byte = 0,
    var solicitud: String? = null,
    var autorizado: Byte = 0,
    var rechazado: Byte = 0,
    var respuesta: String? = null,
    var transferido: Byte = 0,

    @OneToOne
    @JoinColumn(name = "cursoId", insertable = false, updatable = false)
    var curso: Curso? = null,

    @OneToOne
    @JoinColumn(name = "cargoTipoId", insertable = false, updatable = false)
    var cargoTipo: CargoTipo? = null,

    @OneToOne
    @JoinColumn(name = "legajoId", insertable = false, updatable = false)
    var persona: Persona? = null

) : Auditable()
