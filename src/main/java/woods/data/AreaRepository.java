package woods.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import woods.datamodel.Area;
import woods.datamodel.Wood;

import java.util.List;
@Repository
public interface AreaRepository extends CrudRepository<Area, Long> {

}
