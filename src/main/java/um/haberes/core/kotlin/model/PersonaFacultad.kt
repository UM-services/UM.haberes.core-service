package um.haberes.core.kotlin.model

import jakarta.persistence.*
import um.haberes.core.hexagonal.facultad.infrastructure.persistence.entity.FacultadEntity

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["legajoId", "facultadId"])])
data class PersonaFacultad(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var personaFacultadId: Long? = null,

    var legajoId: Long? = null,
    var facultadId: Int? = null,

    @OneToOne
    @JoinColumn(name = "legajoId", insertable = false, updatable = false)
    var persona: Persona? = null,

    @OneToOne
    @JoinColumn(name = "facultadId", insertable = false, updatable = false)
    var facultad: FacultadEntity? = null

) : Auditable()
