package um.haberes.core.kotlin.model

import jakarta.persistence.*

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["legajoId", "anho", "mes"])])
data class Antiguedad(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var antiguedadId: Long? = null,

    var legajoId: Long? = null,
    var anho: Int = 0,
    var mes: Int = 0,
    var mesesDocentes: Int = 0,
    var mesesAdministrativos: Int = 0,

    @OneToOne(optional = true)
    @JoinColumn(name = "legajoId", insertable = false, updatable = false)
    var persona: Persona? = null

) : Auditable()
