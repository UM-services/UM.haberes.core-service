package um.haberes.core.kotlin.model

import jakarta.persistence.*
import um.haberes.core.hexagonal.facultad.infrastructure.persistence.entity.FacultadEntity
import um.haberes.core.hexagonal.geografica.infrastructure.persistence.entity.GeograficaEntity

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
    var facultad: FacultadEntity? = null,

    @OneToOne(optional = false)
    @JoinColumn(name = "geograficaId", insertable = false, updatable = false)
    var geografica: GeograficaEntity? = null

) : Auditable() {

    fun getSedeKey(): String {
        return this.facultadId.toString() + "." + this.geograficaId
    }

}
