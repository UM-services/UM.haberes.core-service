package um.haberes.core.kotlin.model

import jakarta.persistence.*

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["facultadId", "geograficaId", "desde"])])
data class InasistenciaDescuento(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var inasistenciaDescuentoId: Long? = null,

    var facultadId: Int? = null,
    var geograficaId: Int? = null,
    var desde: Int = 0,
    var hasta: Int = 0,
    var porcentaje: Int = 0

) : Auditable()
