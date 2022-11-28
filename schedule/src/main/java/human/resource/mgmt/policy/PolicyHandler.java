package human.resource.mgmt.policy;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.eventhandling.DisallowReplay;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.axonframework.commandhandling.gateway.CommandGateway;

import human.resource.mgmt.command.*;
import human.resource.mgmt.event.*;
import human.resource.mgmt.aggregate.*;


@Service
@ProcessingGroup("schedule")
public class PolicyHandler{

    @Autowired
    CommandGateway commandGateway;

    @EventHandler
    //@DisallowReplay
    public void wheneverVacationRegistered_Add(VacationRegisteredEvent vacationRegistered){
        System.out.println(vacationRegistered.toString());

        AddCommand command = new AddCommand();
        commandGateway.send(command);
    }
    @EventHandler
    //@DisallowReplay
    public void wheneverVacationCancelled_Cancel(VacationCancelledEvent vacationCancelled){
        System.out.println(vacationCancelled.toString());

        CancelCommand command = new CancelCommand();
        commandGateway.send(command);
    }
    @EventHandler
    //@DisallowReplay
    public void wheneverVacationRejected_Cancel(VacationRejectedEvent vacationRejected){
        System.out.println(vacationRejected.toString());

        CancelCommand command = new CancelCommand();
        commandGateway.send(command);
    }
    @EventHandler
    //@DisallowReplay
    public void wheneverEmployeeJoined_Register(EmployeeJoinedEvent employeeJoined){
        System.out.println(employeeJoined.toString());

        RegisterCommand command = new RegisterCommand();
        commandGateway.send(command);
    }

}
