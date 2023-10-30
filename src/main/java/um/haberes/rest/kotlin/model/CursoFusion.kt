package um.haberes.rest.kotlin.model

import jakarta.persistence.*

@Entity
@Table
data class CursoFusion(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var cursoFusionId: Long? = null,

    var legajoId: Long? = null,
    var anho: Int = 0,
    var mes: Int = 0,
    var facultadId: Int? = null,
    var geograficaId: Int? = null,
    var cargoTipoId: Int? = null,
    var designacionTipoId: Int? = null,
    var anual: Byte = 0,
    var categoriaId: Int? = null,

    @OneToOne(optional = true)
    @JoinColumn(name = "legajoId", insertable = false, updatable = false)
    var persona: Persona? = null,

    @OneToOne(optional = true)
    @JoinColumn(name = "facultadId", insertable = false, updatable = false)
    var facultad: Facultad? = null,

    @OneToOne(optional = true)
    @JoinColumn(name = "geograficaId", insertable = false, updatable = false)
    var geografica: Geografica? = null,

    @OneToOne(optional = true)
    @JoinColumn(name = "cargoTipoId", insertable = false, updatable = false)
    var cargoTipo: CargoTipo? = null,

    @OneToOne(optional = true)
    @JoinColumn(name = "designacionTipoId", insertable = false, updatable = false)
    var designacionTipo: CargoTipo? = null,

    @OneToOne(optional = true)
    @JoinColumn(name = "categoriaId", insertable = false, updatable = false)
    var categoria: Categoria? = null

) : Auditable()
