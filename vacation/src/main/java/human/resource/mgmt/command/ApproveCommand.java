package human.resource.mgmt.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import java.util.Date; 



import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class ApproveCommand {


    @TargetAggregateIdentifier
    private String id;

        private Boolean approve;

}
