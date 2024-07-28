package um.haberes.core.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import um.haberes.core.kotlin.model.extern.EjercicioDto;

import java.time.OffsetDateTime;
import java.util.List;

@FeignClient(name = "tesoreria-core-service/ejercicio")
public interface EjercicioClient {

    @GetMapping("/")
    List<EjercicioDto> findAll();

    @GetMapping("/{ejercicioId}")
    EjercicioDto findByEjercicioId(@PathVariable Integer ejercicioId);

    @GetMapping("/last")
    EjercicioDto findLast();

    @GetMapping("/fecha/{fecha}")
    EjercicioDto findByFecha(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime fecha);

    @PostMapping("/")
    EjercicioDto add(@RequestBody EjercicioDto ejercicio);

    @PutMapping("/{ejercicioId}")
    EjercicioDto update(@RequestBody EjercicioDto ejercicio, @PathVariable Integer ejercicioId);

    @DeleteMapping("/{ejercicioId}")
    Void delete(@PathVariable Integer ejercicioId);

}
