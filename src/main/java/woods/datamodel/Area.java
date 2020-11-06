package woods.datamodel;

import lombok.Data;

import java.util.List;

@Data
public class Area {

    private Months month;
    private String areaName;
    private double square;
    private WoodTypes[] woods;
    private double woodsSum;
    private double PMM1;  // fuel in Zagotovka
    private double PMM2; // fuel in Treliovka
    private List<WorkerStatistics> list;

}
