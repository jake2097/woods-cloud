package woods.datamodel;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class WorkerStatistics {
    private String surname;
    private double volume;
    private double salary;
    private double rate;
}
