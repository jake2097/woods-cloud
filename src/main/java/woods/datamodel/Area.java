package woods.datamodel;



import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Data
@RequiredArgsConstructor
@Entity
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Months month;
    private String areaName;
    private double square;

    @ManyToMany(targetEntity = Wood.class)
    private List<Wood> woods;

    private double woodsSum;
    private double PMM1;  // fuel in Zagotovka
    private double PMM2; // fuel in Treliovka

    @ManyToMany(targetEntity = WorkerStatistics.class)
    private List<WorkerStatistics> list;

    @Override
    public String toString() {
        String str;
        str = "Місяць = " + month + "\nКвартал: " + areaName + "\nПлоща = " + square;



        str += "\n" + "Всього: " + woodsSum + "\nПММ(заготовка): " + PMM1 + "\nПММ(трельовка): " + PMM2;

        for (WorkerStatistics obj : list) {
            str += obj.toString();
        }

        return str;
    }
}
