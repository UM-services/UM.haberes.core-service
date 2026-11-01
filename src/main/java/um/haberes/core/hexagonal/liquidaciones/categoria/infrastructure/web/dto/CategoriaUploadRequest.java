package um.haberes.core.hexagonal.liquidaciones.categoria.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;
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
public class CategoriaUploadRequest {

    @NotBlank
    private String filename;

    @NotBlank
    private String base64;
}
