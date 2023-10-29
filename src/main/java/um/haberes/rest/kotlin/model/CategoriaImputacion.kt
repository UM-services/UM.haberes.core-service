package um.haberes.rest.kotlin.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["dependenciaId", "facultadId", "geograficaId", "categoriaId"])])
data class CategoriaImputacion(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var categoriaImputacionId: Long? = null,
    var dependenciaId: Int? = null,
    var facultadId: Int? = null,
    var geograficaId: Int? = null,
    var categoriaId: Int? = null,
    var cuentaSueldos: BigDecimal? = null,
    var cuentaAportes: BigDecimal? = null

) : Auditable() {

    fun key(): String {
        return "$dependenciaId.$facultadId.$geograficaId.$categoriaId"
    }

}
