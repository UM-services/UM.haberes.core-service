package um.haberes.core.kotlin.model.dto

import um.haberes.core.kotlin.model.Contacto
import um.haberes.core.kotlin.model.LegajoControl

data class FormularioImprimir(

    var legajoControls: List<LegajoControl?>? = null,
    var contactos: List<Contacto>? = null

)
