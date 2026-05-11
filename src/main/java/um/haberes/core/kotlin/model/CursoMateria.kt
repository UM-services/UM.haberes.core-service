package um.haberes.core.kotlin.model

import jakarta.persistence.*
import um.haberes.core.hexagonal.facultad.infrastructure.persistence.entity.FacultadEntity
import um.haberes.core.hexagonal.geografica.infrastructure.persistence.entity.GeograficaEntity
import java.math.BigDecimal

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["cursoId", "facultadId", "geograficaId", "planId", "carreraId", "materiaId"])])
data class CursoMateria(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var cursoMateriaId: Long? = null,

    var cursoId: Long? = null,
    var facultadId: Int? = null,
    var geograficaId: Int? = null,
    var planId: Int? = null,
    var carreraId: Int? = null,
    var materiaId: String? = null,
    var proporcion: BigDecimal = BigDecimal.ZERO,

    @OneToOne
    @JoinColumn(name = "cursoId", insertable = false, updatable = false)
    var curso: Curso? = null,

    @OneToOne
    @JoinColumn(name = "facultadId", insertable = false, updatable = false)
    var facultad: FacultadEntity? = null,

    @OneToOne
    @JoinColumn(name = "geograficaId", insertable = false, updatable = false)
    var geografica: GeograficaEntity? = null

) : Auditable()
