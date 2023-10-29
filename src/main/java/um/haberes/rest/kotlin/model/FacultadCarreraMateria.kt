package um.haberes.rest.kotlin.model

import jakarta.persistence.*

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["facultadId", "planId", "carreraId", "materiaId"])])
data class FacultadCarreraMateria(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var facultadCarreraMateriaId: Long? = null,

    var facultadId: Int? = null,
    var planId: Int? = null,
    var carreraId: Int? = null,
    var materiaId: String? = null,

    @OneToOne
    @JoinColumns(
        JoinColumn(
            name = "facultadId",
            referencedColumnName = "facultadId",
            insertable = false,
            updatable = false
        ),
        JoinColumn(name = "planId", referencedColumnName = "planId", insertable = false, updatable = false),
        JoinColumn(name = "carreraId", referencedColumnName = "carreraId", insertable = false, updatable = false)
    )
    var facultadCarrera: FacultadCarrera? = null,

    @OneToOne
    @JoinColumns(
        JoinColumn(
            name = "facultadId",
            referencedColumnName = "facultadId",
            insertable = false,
            updatable = false
        ),
        JoinColumn(name = "planId", referencedColumnName = "planId", insertable = false, updatable = false),
        JoinColumn(name = "materiaId", referencedColumnName = "materiaId", insertable = false, updatable = false)
    )
    var facultadMateria: FacultadMateria? = null

) : Auditable()
