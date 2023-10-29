package um.haberes.rest.kotlin.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table
data class CursoDesarraigo(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var cursoDesarraigoId: Long? = null,

    var legajoId: Long? = null,
    var anho: Int = 0,
    var mes: Int? = null,
    var cursoId: Long? = null,
    var geograficaId: Int? = null,
    var importe: BigDecimal = BigDecimal.ZERO,
    var version: Int? = null,

    @OneToOne
    @JoinColumn(name = "cursoId", insertable = false, updatable = false)
    var curso: Curso? = null,

    @OneToOne
    @JoinColumn(name = "legajoId", insertable = false, updatable = false)
    var persona: Persona? = null,

    @OneToOne
    @JoinColumn(name = "geograficaId", insertable = false, updatable = false)
    var geografica: Geografica? = null

) : Auditable()
