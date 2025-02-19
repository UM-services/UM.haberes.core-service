package um.haberes.core.kotlin.model

import jakarta.persistence.*

@Entity
@Table
data class AdicionalCursoTabla(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var adicionalCursoTablaId: Long? = null,

    var periodoDesde: Long = 0L,
    var periodoHasta: Long = 0L,
    var facultadId: Int? = null,
    var geograficaId: Int? = null,

    @OneToOne
    @JoinColumn(name = "facultadId", insertable = false, updatable = false)
    var facultad: Facultad? = null,

    @OneToOne
    @JoinColumn(name = "geograficaId", insertable = false, updatable = false)
    var geografica: Geografica? = null,

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "adicionalCursoTablaId", insertable = false, updatable = false)
    var adicionalCursoRangos: List<AdicionalCursoRango>? = null

) : Auditable()
