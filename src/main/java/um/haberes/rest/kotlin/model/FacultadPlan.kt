package um.haberes.rest.kotlin.model

import jakarta.persistence.*
import um.haberes.rest.model.Auditable

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["facultadId", "planId"])])
data class FacultadPlan(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var facultadPlanId: Long? = null,

    var facultadId: Int? = null,
    var planId: Int? = null,
    var nombre: String = "",

    @OneToOne
    @JoinColumn(name = "facultadId", insertable = false, updatable = false)
    var facultad: Facultad? = null

) : Auditable()
