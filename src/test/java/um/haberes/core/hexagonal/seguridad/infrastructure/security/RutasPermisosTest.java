package um.haberes.core.hexagonal.seguridad.infrastructure.security;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RutasPermisosTest {

    private final RutasPermisos rutas = new RutasPermisos();

    @Test
    void resuelveLasRutasMasEspecificasPrimero() {
        assertThat(rutas.requeridas("GET", "/api/haberes/core/permisoEfectivo/usuario/725"))
                .contains(List.of());
        assertThat(rutas.requeridas("GET", "/api/haberes/core/permiso/"))
                .contains(List.of("seguridad.catalogo.administrar"));
        assertThat(rutas.requeridas("GET", "/api/haberes/core/rolPermiso/rol/1"))
                .contains(List.of("seguridad.roles.administrar"));
        assertThat(rutas.requeridas("GET", "/api/haberes/core/rol/"))
                .contains(List.of("seguridad.roles.administrar", "seguridad.permisos.administrar"));
    }

    @Test
    void lasLecturasDeCatalogosUsanComun() {
        assertThat(rutas.requeridas("GET", "/api/haberes/core/facultad/3"))
                .contains(List.of("comun.catalogos.consultar"));
        assertThat(rutas.requeridas("GET", "/api/haberes/core/categoria/"))
                .contains(List.of("comun.catalogos.consultar"));
        assertThat(rutas.requeridas("GET", "/api/haberes/core/persona/725"))
                .contains(List.of("comun.personas.consultar"));
        assertThat(rutas.requeridas("POST", "/api/haberes/core/persona/search"))
                .contains(List.of("comun.personas.consultar"));
        assertThat(rutas.requeridas("GET", "/api/haberes/core/cargotipo/1"))
                .contains(List.of("comun.catalogos.consultar"));
        assertThat(rutas.requeridas("POST", "/api/haberes/core/cargotipo/"))
                .contains(List.of("designaciones.general.gestionar", "liquidacion.asig_cargos.gestionar"));
        assertThat(rutas.requeridas("GET", "/api/haberes/core/personaFacultad/1"))
                .contains(List.of("comun.personas.consultar"));
    }

    @Test
    void lasEscriturasDeCatalogosPidenElPermisoDelModulo() {
        assertThat(rutas.requeridas("POST", "/api/haberes/core/categoria/"))
                .contains(List.of("administrador.categorias.editar"));
        assertThat(rutas.requeridas("PUT", "/api/haberes/core/usuario/setpassword"))
                .contains(List.of("administrador.usuarios.administrar"));
    }

    @Test
    void cargoliquidacionAceptaNovedadesSoloEnLectura() {
        List<String> lectura = rutas.requeridas("GET", "/api/haberes/core/cargoliquidacion/legajo/1/2026/1")
                .orElseThrow();
        assertThat(lectura).contains(
                "liquidacion.general.procesar",
                "novedades.cargos.ver",
                "novedades.designaciones.ver");

        List<String> escritura = rutas.requeridas("POST", "/api/haberes/core/cargoliquidacion/generacion/etec/periodo/2026/1")
                .orElseThrow();
        assertThat(escritura)
                .contains("liquidacion.general.procesar")
                .doesNotContain("novedades.cargos.ver", "novedades.designaciones.ver");
    }

    @Test
    void lasRutasSinReglaQuedanSinPermiso() {
        assertThat(rutas.requeridas("GET", "/api/haberes/core/desconocido")).isEmpty();
    }
}
