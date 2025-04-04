package um.haberes.core.kotlin.model

import jakarta.persistence.*

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
