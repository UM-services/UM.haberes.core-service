package um.haberes.rest.kotlin.model.view

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import org.hibernate.annotations.Immutable
import java.math.BigDecimal

@Entity
@Table(name = "vw_imputado_sueldo",
    uniqueConstraints = [UniqueConstraint(columnNames = ["anho", "mes", "cuentaSueldos"])])
@Immutable
data class ImputadoSueldo(

    @Id
    var uniqueId: String? = null,

    var anho: Int? = null,
    var mes: Int? = null,
    var cuentaSueldos: BigDecimal? = null,
    var totalImputado: BigDecimal? = null

)
