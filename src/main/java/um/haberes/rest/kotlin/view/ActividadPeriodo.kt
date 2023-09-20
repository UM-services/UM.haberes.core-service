package um.haberes.rest.kotlin.view

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Immutable
import um.haberes.rest.model.Auditable

@Entity
@Table(name = "vw_actividad_periodo")
@Immutable
data class ActividadPeriodo(

    @Id
    var actividadId: Long? = null,
    var legajoId: Long? = null,
    var anho: Int? = null,
    var mes: Int? = null,
    var docente: Byte? = null,
    var otras: Byte? = null,
    var clases: Byte? = null,
    var dependenciaId: Int? = null,
    var periodo: Long? = null

) : Auditable()
