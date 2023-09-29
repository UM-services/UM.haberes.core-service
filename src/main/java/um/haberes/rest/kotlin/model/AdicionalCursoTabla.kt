package um.haberes.rest.kotlin.model

import jakarta.persistence.*
import um.haberes.rest.model.Auditable
import um.haberes.rest.model.Facultad

@Entity
@Table
data class AdicionalCursoTabla(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var adicionalCursoTablaId: Long? = null,

    var periodoDesde: Long = 0L,
    var periodoHasta: Long = 0L,
    var facultadId: Int? = null,

    @OneToOne
    @JoinColumn(name = "facultadId", insertable = false, updatable = false)
    var facultad: Facultad? = null,

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "adicionalCursoTablaId", insertable = false, updatable = false)
    var adicionalCursoRangos: List<AdicionalCursoRango>? = null

) : Auditable()
