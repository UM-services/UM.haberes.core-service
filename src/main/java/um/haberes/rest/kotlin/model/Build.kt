package um.haberes.rest.kotlin.model

import jakarta.persistence.*

@Entity
@Table
data class Build(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var build: Long? = null

) : Auditable()
