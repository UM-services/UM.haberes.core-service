package um.haberes.core.kotlin.model

import jakarta.persistence.*

@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["legajoId", "anho", "mes", "facultadId", "geograficaId"])])
data class LegajoInasistencia(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var legajoInasistenciaId: Long? = null,

    var legajoId: Long? = null,
    var anho: Int = 0,
    var mes: Int = 0,
    var facultadId: Int? = null,
    var geograficaId: Int? = null,
    var cantidadInasistencias: Int = 0

) : Auditable()
