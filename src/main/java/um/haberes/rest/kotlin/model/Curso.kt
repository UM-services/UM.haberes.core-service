package um.haberes.rest.kotlin.model

import jakarta.persistence.*
import um.haberes.rest.model.Auditable
import um.haberes.rest.model.Facultad
import um.haberes.rest.model.Geografica
import um.haberes.rest.model.Nivel

@Entity
@Table
data class Curso(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var cursoId: Long? = null,

    var nombre: String = "",
    var facultadId: Int? = null,
    var geograficaId: Int? = null,
    var anual: Byte = 0,
    var semestre1: Byte = 0,
    var semestre2: Byte = 0,
    var nivelId: Int? = null,
    var adicionalCargaHoraria: Byte = 0,

    @OneToOne
    @JoinColumn(name = "facultadId", insertable = false, updatable = false)
    var facultad: Facultad? = null,

    @OneToOne
    @JoinColumn(name = "geograficaId", insertable = false, updatable = false)
    var geografica: Geografica? = null,

    @OneToOne
    @JoinColumn(name = "nivelId", insertable = false, updatable = false)
    var nivel: Nivel? = null

) : Auditable()
