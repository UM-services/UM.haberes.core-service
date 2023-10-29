package um.haberes.rest.kotlin.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table
data class Facultad(

    @Id
    var facultadId: Int? = null,

    var nombre: String = "",
    var reducido: String = "",
    var server: String = "",
    var backendServer: String = "",
    var backendPort: Int = 0,
    var dbName: String = "",
    var dsn: String = ""

) : Auditable()
