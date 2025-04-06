package um.haberes.core.kotlin.model.view

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Immutable
import java.math.BigDecimal

@Entity
@Table(name = "vw_docente_designacion")
@Immutable
data class DocenteDesignacion(

    @Id

    var legajoId: Long? = null,
    var anho: Int? = null,
    var mes: Int? = null,
    var facultadId: Int? = null,
    var geograficaId: Int? = null,
    var espacio: String? = null,
    var horasSemanales: BigDecimal? = null,
    var cargo: String? = null,
    var designacion: String? = null,
    var horasDesignacion: BigDecimal? = null,
    var anual: Byte? = null,
    var semestre1: Byte? = null,
    var semestre2: Byte? = null,

)