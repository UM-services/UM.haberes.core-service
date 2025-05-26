package um.haberes.core.kotlin.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table
data class Categoria(

    @Id
    var categoriaId: Int? = null,

    var nombre: String = "",
    var basico: BigDecimal = BigDecimal.ZERO,
    var docente: Byte = 0,
    var noDocente: Byte = 0,
    var liquidaPorHora: Byte = 0,
    var estadoDocente: BigDecimal = BigDecimal.ZERO

) : Auditable()
