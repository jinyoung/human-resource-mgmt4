package human.resource.mgmt.query;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Calendar_table")
@Data
//<<< EDA / Read Model

public class CalendarReadModel {

    @Id
    private String userId;

    @ElementCollection
    private List<Event> events;
}
//>>> EDA / Read Model
