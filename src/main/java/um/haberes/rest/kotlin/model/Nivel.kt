package um.haberes.rest.kotlin.model

import jakarta.persistence.*

@Entity
@Table
data class Nivel(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var nivelId: Int? = null,

    val nombre: String = ""

) : Auditable()
