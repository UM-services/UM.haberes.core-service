package ar.edu.um.haberes.rest.kotlin.model

import ar.edu.um.haberes.rest.model.Auditable
import jakarta.persistence.*

@Entity
@Table
data class Build(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var build: Long? = null

) : Auditable()
