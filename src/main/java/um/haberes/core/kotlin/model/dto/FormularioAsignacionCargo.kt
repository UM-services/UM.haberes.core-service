package um.haberes.core.kotlin.model.dto

import um.haberes.core.kotlin.model.Categoria
import um.haberes.core.kotlin.model.Dependencia
import um.haberes.core.kotlin.model.Facultad

data class FormularioAsignacionCargo(

    var categorias: List<Categoria?>? = null,
    var categoriasAsignables: List<Categoria>? = null,
    var dependencias: List<Dependencia>? = null,
    var facultades: List<Facultad>? = null

)
