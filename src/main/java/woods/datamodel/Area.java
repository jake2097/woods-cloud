package woods.datamodel;

import lombok.Data;

import java.util.Arrays;
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

    @Override
    public String toString() {
        return "Area{" +
                "month=" + month +
                ", areaName='" + areaName + '\'' +
                ", square=" + square +
                ", woods=" + Arrays.toString(woods) +
                ", woodsSum=" + woodsSum +
                ", PMM1=" + PMM1 +
                ", PMM2=" + PMM2 +
                ", list=" + list +
                '}';
    }
}
