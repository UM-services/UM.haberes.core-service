package um.haberes.rest.kotlin.model

import jakarta.persistence.*
import um.haberes.rest.model.Auditable
import um.haberes.rest.model.Facultad
import um.haberes.rest.model.Geografica

@Entity
@Table
data class Dependencia(

    @Id
    var dependenciaId: Int? = null,
    var nombre: String = "",
    var acronimo: String = "",
    var facultadId: Int? = null,
    var geograficaId: Int? = null,
    @OneToOne(optional = false)
    @JoinColumn(name = "facultadId", insertable = false, updatable = false)
    var facultad: Facultad? = null,
    @OneToOne(optional = false)
    @JoinColumn(name = "geograficaId", insertable = false, updatable = false)
    var geografica: Geografica? = null

) : Auditable() {

    fun getSedeKey(): String {
        return this.facultadId.toString() + "." + this.geograficaId
    }

}
