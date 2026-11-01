package um.haberes.core.hexagonal.liquidaciones.letra.infrastructure.web.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LetraResponse {

    private Long letraId;

    private Long legajoId;

    private Integer anho;

    private Integer mes;

    private BigDecimal neto;

    private String cadena;
}
