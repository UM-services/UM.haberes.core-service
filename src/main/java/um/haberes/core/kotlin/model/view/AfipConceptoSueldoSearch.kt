package um.haberes.core.kotlin.model.view

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Immutable

@Entity
@Table(name = "vw_afip_concepto_sueldo_search")
@Immutable
data class AfipConceptoSueldoSearch(

    @Id
    var afipConceptoSueldoId: Long? = null,
    var descripcion: String = "",
    var asignado: Byte = 0,
    var search: String = ""

)
