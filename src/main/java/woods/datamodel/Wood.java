package woods.datamodel;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;


@Data
@RequiredArgsConstructor
@Entity
public class Wood {
    @Id
    private long id;
    private WoodTypes type;
    private double volume;

    public String toString(){
        return type.name() + ": " + volume;
    }
}
