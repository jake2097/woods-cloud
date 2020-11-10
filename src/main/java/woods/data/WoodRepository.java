package woods.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import woods.datamodel.Wood;
@Repository
public interface WoodRepository extends CrudRepository<Wood, Long> {
}
