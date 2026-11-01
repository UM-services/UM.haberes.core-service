package um.haberes.core.model.extern;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CuentaDto {

    private BigDecimal numeroCuenta = null;

    private String nombre = "";

    private Byte integradora = 0;

    private int grado = 0;

    private BigDecimal grado1 = null;

    private BigDecimal grado2 = null;

    private BigDecimal grado3 = null;

    private BigDecimal grado4 = null;

    private Integer geograficaId = null;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXX", timezone = "UTC")
    private OffsetDateTime fechaBloqueo = null;

    private Byte visible = 0;

    private Long cuentaContableId = null;
}
