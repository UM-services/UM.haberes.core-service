package um.haberes.rest.repository.view;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import um.haberes.rest.kotlin.model.view.AfipConceptoSueldoSearch;

@Repository
public interface IAfipConceptoSueldoSearchRepository extends JpaRepository<AfipConceptoSueldoSearch, String>, IAfipConceptoSueldoSearchRepositoryCustom {
}
