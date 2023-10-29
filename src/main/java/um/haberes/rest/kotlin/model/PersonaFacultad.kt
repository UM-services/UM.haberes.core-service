package um.haberes.rest.kotlin.model

import jakarta.persistence.*

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
    var facultad: Facultad? = null

) : Auditable()
