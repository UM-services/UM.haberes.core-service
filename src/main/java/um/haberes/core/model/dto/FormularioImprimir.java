package um.haberes.core.model.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import um.haberes.core.model.ContactoEntity;
import um.haberes.core.model.LegajoControlEntity;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FormularioImprimir {

    private List<LegajoControlEntity> legajoControls = null;

    private List<ContactoEntity> contactos = null;
}
