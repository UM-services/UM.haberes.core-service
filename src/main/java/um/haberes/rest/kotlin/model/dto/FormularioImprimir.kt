package um.haberes.rest.kotlin.model.dto

import um.haberes.rest.kotlin.model.Contacto
import um.haberes.rest.kotlin.model.LegajoControl

data class FormularioImprimir(

    var legajoControls: List<LegajoControl?>? = null,
    var contactos: List<Contacto>? = null

)
