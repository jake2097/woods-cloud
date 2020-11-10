package woods.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import woods.datamodel.Worker;
@Repository
public interface WorkerRepository extends CrudRepository<Worker, Long> {
}
