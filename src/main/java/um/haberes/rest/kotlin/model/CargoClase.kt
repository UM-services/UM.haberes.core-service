package um.haberes.rest.kotlin.model

import jakarta.persistence.*

@Entity
@Table
data class CargoClase(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var cargoClaseId: Long? = null,

    var nombre: String = "",
    var claseId: Int? = null,

    @OneToOne
    @JoinColumn(name = "claseId", insertable = false, updatable = false)
    var clase: Clase? = null

) : Auditable()
