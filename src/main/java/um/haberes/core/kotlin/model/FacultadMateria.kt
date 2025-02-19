package um.haberes.core.kotlin.model

import jakarta.persistence.*

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["facultadId", "planId", "materiaId"])])
data class FacultadMateria(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var facultadMateriaId: Long? = null,

    var facultadId: Int? = null,
    var planId: Int? = null,
    var materiaId: String? = null,
    var nombre: String = ""

) : Auditable()
