package human.resource.mgmt.command;

import lombok.Data;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@ToString
@Data
public class RegisterCommand {

    private String userId; // Please comment here if you want user to enter the id directly
}
