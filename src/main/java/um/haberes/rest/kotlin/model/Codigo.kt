package um.haberes.rest.kotlin.model

import jakarta.persistence.*
import um.haberes.rest.model.Auditable

@Entity
@Table
data class Codigo(

    @Id
    var codigoId: Int? = null,
    var nombre: String = "",
    var docente: Byte = 0,
    var noDocente: Byte = 0,
    var transferible: Byte = 0,
    var incluidoEtec: Byte = 0,
    var afipConceptoSueldoIdPrimerSemestre: Long? = null,
    var afipConceptoSueldoIdSegundoSemestre: Long? = null,

    @OneToOne
    @JoinColumn(name = "afipConceptoSueldoIdPrimerSemestre", insertable = false, updatable = false)
    var afipConceptoSueldoPrimerSemestre: AfipConceptoSueldo? = null,

    @OneToOne
    @JoinColumn(name = "afipConceptoSueldoIdSegundoSemestre", insertable = false, updatable = false)
    var afipConceptoSueldoSegundoSemestre: AfipConceptoSueldo? = null

) : Auditable()
