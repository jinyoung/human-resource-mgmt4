package human.resource.mgmt.event;




import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class VacationDaysUsedEvent {

    private String userId;
    private Integer dayCount;
    private String reason;

}
