package um.haberes.core.kotlin.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table
data class LegajoCategoriaImputacion(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var legajoCategoriaImputacionId: Long? = null,
    var legajoId: Long? = null,
    var anho: Int = 0,
    var mes: Int = 0,
    var dependenciaId: Int? = null,
    var facultadId: Int? = null,
    var geograficaId: Int? = null,
    var categoriaId: Int? = null,
    var cuentaSueldos: BigDecimal? = null,
    var basico: BigDecimal? = BigDecimal.ZERO,
    var antiguedad: BigDecimal = BigDecimal.ZERO,
    var cuentaAportes: BigDecimal? = null,

    @OneToOne
    @JoinColumn(name = "legajoId", insertable = false, updatable = false)
    var persona: Persona? = null,

    @OneToOne
    @JoinColumn(name = "dependenciaId", insertable = false, updatable = false)
    var dependencia: Dependencia? = null,

    @OneToOne
    @JoinColumn(name = "facultadId", insertable = false, updatable = false)
    var facultad: Facultad? = null,

    @OneToOne
    @JoinColumn(name = "geograficaId", insertable = false, updatable = false)
    var geografica: Geografica? = null,

    @OneToOne
    @JoinColumn(name = "categoriaId", insertable = false, updatable = false)
    var categoria: Categoria? = null

) : Auditable()
