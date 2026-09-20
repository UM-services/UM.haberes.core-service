package um.haberes.core.repository.view;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.core.model.view.AfipConceptoSueldoSearch;

@Repository
public interface JpaAfipConceptoSueldoSearchRepository extends JpaRepository<AfipConceptoSueldoSearch, String>, JpaAfipConceptoSueldoSearchRepositoryCustom {
}
