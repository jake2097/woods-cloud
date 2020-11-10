package woods.datamodel;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;


@Data
@Entity
public class Worker {

    @Id
    private long id;
    private String surname;

    @Override
    public String toString() {
        return "Прізвище: " + getSurname();
    }
}
