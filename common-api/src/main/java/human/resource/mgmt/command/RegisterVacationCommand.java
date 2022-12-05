package human.resource.mgmt.command;

import lombok.Data;
import lombok.ToString;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@ToString
@Data
public class RegisterVacationCommand {

    private Long id; // Please comment here if you want user to enter the id directly
}
