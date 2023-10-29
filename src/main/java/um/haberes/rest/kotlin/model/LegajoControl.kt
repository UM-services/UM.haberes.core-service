package um.haberes.rest.kotlin.model

import jakarta.persistence.*

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["legajoId", "anho", "mes"])])
data class LegajoControl(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var legajoControlId: Long? = null,

    var legajoId: Long? = null,
    var anho: Int = 0,
    var mes: Int = 0,
    var liquidado: Byte = 0,
    var fusionado: Byte = 0,
    var bonoEnviado: Byte = 0,

    @OneToOne(optional = true)
    @JoinColumn(name = "legajoId", insertable = false, updatable = false)
    var persona: Persona? = null

) : Auditable()
