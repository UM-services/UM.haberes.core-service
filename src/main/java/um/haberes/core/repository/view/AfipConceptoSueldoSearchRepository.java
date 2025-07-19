package um.haberes.core.repository.view;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.kotlin.model.view.AfipConceptoSueldoSearch;

@Repository
public interface AfipConceptoSueldoSearchRepository extends JpaRepository<AfipConceptoSueldoSearch, String>, AfipConceptoSueldoSearchRepositoryCustom {
}
