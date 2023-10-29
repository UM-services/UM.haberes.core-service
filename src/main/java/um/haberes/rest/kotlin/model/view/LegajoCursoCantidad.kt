package um.haberes.rest.kotlin.model.view

import jakarta.persistence.*
import org.hibernate.annotations.Immutable

@Entity
@Table(name = "vw_legajo_curso_cantidad")
@Immutable
data class LegajoCursoCantidad(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var uniqueId: String? = null,

    var anho: Int? = null,
    var mes: Int? = null,
    var legajoId: Long? = null,
    var facultadId: Int? = null,
    var geograficaId: Int? = null,
    var anuales: Int? = null,
    var semestre1: Int? = null,
    var semestre2: Int? = null

)
