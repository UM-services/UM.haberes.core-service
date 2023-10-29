package um.haberes.rest.kotlin.model

import jakarta.persistence.*

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["facultadId", "planId", "carreraId"])])
data class FacultadCarrera(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var facultadCarreraId: Long? = null,

    var facultadId: Int? = null,
    var planId: Int? = null,
    var carreraId: Int? = null,
    var nombre: String = ""

) : Auditable()
