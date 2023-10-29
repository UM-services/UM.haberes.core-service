package um.haberes.rest.kotlin.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["categoriaId", "anho", "mes"])])
data class CategoriaPeriodo(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var categoriaPeriodoId: Long? = null,

    var categoriaId: Int? = null,
    var anho: Int = 0,
    var mes: Int = 0,
    var nombre: String = "",
    var basico: BigDecimal = BigDecimal.ZERO,
    var docente: Byte = 0,
    var noDocente: Byte = 0,
    var liquidaPorHora: Byte = 0

) : Auditable()
