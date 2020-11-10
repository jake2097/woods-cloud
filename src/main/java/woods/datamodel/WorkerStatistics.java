package woods.datamodel;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


import javax.persistence.*;


@Data
@RequiredArgsConstructor
@Entity
public class WorkerStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(targetEntity = Worker.class)
    private Worker worker;
    private double volume;
    private double salary;
    private double rate;

    @Override
    public String toString() {
        return "\n" + worker +
                " \nОб'єм: " + volume +
                " \nЗарплата: " + salary + "\n";
    }
}
