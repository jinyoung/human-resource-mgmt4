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
@ProcessingGroup("vacation")  //consumer group name
public class PolicyHandler{

    @Autowired
    CommandGateway commandGateway;

    @EventHandler
    public void wheneverVacationRegistered_Use(VacationRegisteredEvent vacationRegistered){
        System.out.println(vacationRegistered.toString());

        UseCommand command = new UseCommand();
        command.setUserId(vacationRegistered.getUserId());
        command.setDayCount(vacationRegistered.getDays());
        commandGateway.send(command);
    }

    @EventHandler
    public void wheneverVacationCancelled_Add(VacationCancelledEvent vacationCancelled){
        System.out.println(vacationCancelled.toString());

        AddCommand command = new AddCommand();
        command.setUserId(vacationCancelled.getUserId());
        command.setDayCount(vacationCancelled.getDays());
        commandGateway.send(command);
    }

    @EventHandler
    public void wheneverVacationRejected_Add(VacationRejectedEvent vacationRejected){
        System.out.println(vacationRejected.toString());

        AddCommand command = new AddCommand();
        command.setUserId(vacationRejected.getUserId());
        command.setDayCount(vacationRejected.getDays());
        commandGateway.send(command);
    }

    @EventHandler
    //@DisallowReplay
    public void wheneverEmployeeJoined_RegisterUser(EmployeeJoinedEvent employeeJoined){
        System.out.println(employeeJoined.toString());

        RegisterUserCommand command = new RegisterUserCommand();
        command.setUserId(employeeJoined.getUserId());
        command.setDayCount(10);
        commandGateway.send(command);
    }

}
