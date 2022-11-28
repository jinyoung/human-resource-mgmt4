package human.resource.mgmt.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;



import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class RegisterUserCommand {


        @TargetAggregateIdentifier //TODO: Please uncomment here if you want user to enter the id directly
        private String userId;  
        private Integer dayCount;

}
