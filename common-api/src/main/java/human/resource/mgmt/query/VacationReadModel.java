package human.resource.mgmt.query;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Vacation_table")
@Data
public class VacationReadModel {

    @Id
    private String id;

    private Date startDate;

    private Date endDate;

    private String reason;

    private String userId;

    private Integer days;

    private String status;
}
