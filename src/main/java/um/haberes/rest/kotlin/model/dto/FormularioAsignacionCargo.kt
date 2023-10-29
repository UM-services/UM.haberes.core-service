package um.haberes.rest.kotlin.model.dto

import um.haberes.rest.kotlin.model.Categoria
import um.haberes.rest.kotlin.model.Dependencia
import um.haberes.rest.kotlin.model.Facultad

data class FormularioAsignacionCargo(

    var categorias: List<Categoria?>? = null,
    var categoriasAsignables: List<Categoria>? = null,
    var dependencias: List<Dependencia>? = null,
    var facultades: List<Facultad>? = null

)
