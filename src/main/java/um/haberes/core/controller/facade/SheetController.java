/**
 *
 */
package um.haberes.core.controller.facade;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import um.haberes.core.service.facade.SheetService;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/api/haberes/core/sheet")
public class SheetController {

    private final SheetService service;

    public SheetController(SheetService service) {
        this.service = service;
    }

    @GetMapping("/generatecargos/{anho}/{mes}")
    public ResponseEntity<Resource> generatecargos(@PathVariable Integer anho, @PathVariable Integer mes)
            throws IOException {
        String filename = service.generateCargos(anho, mes);
        File file = new File(filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cargos.xlsx");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity.ok().headers(headers).contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
    }

    @GetMapping("/generateitems/{anho}/{mes}")
    public ResponseEntity<Resource> generateitems(@PathVariable Integer anho, @PathVariable Integer mes)
            throws IOException {
        String filename = service.generateItems(anho, mes);
        File file = new File(filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=items.xlsx");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity.ok().headers(headers).contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
    }

    @GetMapping("/generatecodigosnodoc/{anho}/{mes}")
    public ResponseEntity<Resource> generatecodigosnodoc(@PathVariable Integer anho, @PathVariable Integer mes)
            throws IOException {
        String filename = service.generateCodigosNoDoc(anho, mes);
        File file = new File(filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=codigos.xlsx");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity.ok().headers(headers).contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
    }

    @GetMapping("/generatecategoriasnodoc/{anho}/{mes}")
    public ResponseEntity<Resource> generatecategoriasnodoc(@PathVariable Integer anho, @PathVariable Integer mes)
            throws IOException {
        String filename = service.generateCategoriasNoDoc(anho, mes);
        return this.handleCategoriaFile(filename);
    }

    @GetMapping("/comparativocodigo/{codigoId}/{anho}/{mes}")
    public ResponseEntity<Resource> comparativocodigo(@PathVariable Integer codigoId, @PathVariable Integer anho,
                                                      @PathVariable Integer mes) throws IOException {
        String filename = service.comparativoCodigo(codigoId, anho, mes);
        File file = new File(filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=comparativo.xlsx");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity.ok().headers(headers).contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
    }

    @GetMapping("/simulasac/{anho}/{semestre}")
    public ResponseEntity<Resource> simulasac(@PathVariable Integer anho, @PathVariable Integer semestre)
            throws IOException {
        String filename = service.simulasac(anho, semestre);
        File file = new File(filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=simulasac.xlsx");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity.ok().headers(headers).contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
    }

    @GetMapping("/generasac/{anho}/{semestre}/{adelanto}")
    public ResponseEntity<Void> generasac(@PathVariable Integer anho, @PathVariable Integer semestre,
                                          @PathVariable Byte adelanto) {
        service.generasac(anho, semestre, adelanto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/generatelegajocursocantidad/{anho}/{mes}")
    public ResponseEntity<Resource> generateLegajoCursoCantidad(@PathVariable Integer anho, @PathVariable Integer mes)
            throws IOException {
        String filename = service.generateLegajoCursoCantidad(anho, mes);
        File file = new File(filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=docentes.xlsx");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity.ok().headers(headers).contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
    }

    @GetMapping("/generateLiquidables")
    public ResponseEntity<Resource> generateLiquidables() throws IOException {
        String filename = service.generateLiquidables();
        File file = new File(filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=liquidables.xlsx");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity.ok().headers(headers).contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
    }

    @GetMapping("/generateCategorias")
    public ResponseEntity<Resource> generateCategorias() throws IOException {
        String filename = service.generateCategorias();
        return this.handleCategoriaFile(filename);
    }


    @GetMapping("/generateBasicos/{anho}/{mes}")
    public ResponseEntity<Resource> generateBasicos(@PathVariable Integer anho, @PathVariable Integer mes)
            throws IOException {
        String filename = service.generateBasicos(anho, mes);
        File file = new File(filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=basicos.xlsx");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity.ok().headers(headers).contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
    }

    @GetMapping("/generatePersonales")
    public ResponseEntity<Resource> generatePersonales() throws IOException {
        String filename = service.generatePersonales();
        File file = new File(filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=personales.xlsx");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity.ok().headers(headers).contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
    }

    @GetMapping("/comparaConsecutivo/{anho}/{mes}")
    public ResponseEntity<Resource> comparaConsecutivos(@PathVariable Integer anho, @PathVariable Integer mes)
            throws IOException {
        String filename = service.comparaConsecutivos(anho, mes);
        File file = new File(filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=consecutivos.xlsx");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity.ok().headers(headers).contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
    }

    @GetMapping("/comparaImputacionSueldos/{anhoDesde}/{mesDesde}/{anhoHasta}/{mesHasta}")
    public ResponseEntity<Resource> comparaImputacionSueldos(@PathVariable Integer anhoDesde, @PathVariable Integer mesDesde, @PathVariable Integer anhoHasta, @PathVariable Integer mesHasta) throws FileNotFoundException {
        String filename = service.comparaImputaciones(anhoDesde, mesDesde, anhoHasta, mesHasta);
        File file = new File(filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=imputaciones.xlsx");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity.ok().headers(headers).contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
    }

    @GetMapping("/cruceImputaciones/{anho}/{mes}")
    public ResponseEntity<Resource> cruceImputaciones(@PathVariable Integer anho, @PathVariable Integer mes) throws FileNotFoundException {
        String filename = service.cruceImputaciones(anho, mes);
        File file = new File(filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cruce.xlsx");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity.ok().headers(headers).contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
    }

    private ResponseEntity<Resource> handleCategoriaFile(String filename) throws FileNotFoundException {
        File file = new File(filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=categorias.xlsx");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        return ResponseEntity.ok().headers(headers).contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
    }

}
