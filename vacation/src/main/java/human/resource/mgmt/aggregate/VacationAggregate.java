package human.resource.mgmt.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import static org.axonframework.modelling.command.AggregateLifecycle.*;
import org.axonframework.spring.stereotype.Aggregate;

import org.springframework.beans.BeanUtils;
import java.util.List;

import lombok.Data;
import lombok.ToString;

import java.util.Date; 



import human.resource.mgmt.command.*;
import human.resource.mgmt.event.*;

@Aggregate
@Data
@ToString
public class VacationAggregate {

    @AggregateIdentifier
    private String id;
    private Date startDate;
    private Date endDate;
    private String reason;
    private String userId;
    private Integer days;
    private String status;

    public VacationAggregate(){}

    @CommandHandler
    public VacationAggregate(RegisterVacationCommand command){

        //pre condition.
        assert(command.getUserId()!=null);
        assert(command.getDays()!=null); 
//            throw new IllegalArgumentException("Invalid vacation request.");

        VacationRegisteredEvent event = new VacationRegisteredEvent();
        BeanUtils.copyProperties(command, event);     

        //Please uncomment here and implement the createUUID method.
        event.setId(createUUID());
        
        apply(event);

    }

    private String createUUID() {
        return java.util.UUID.randomUUID().toString();
    }

    @CommandHandler
    public void handle(CancelCommand command){

        VacationCancelledEvent event = new VacationCancelledEvent();
        //TODO
        BeanUtils.copyProperties(this, event);     


        apply(event);

    }

    @CommandHandler
    public void handle(ApproveCommand command){
        if("USED".equals(getStatus())){ //참조는 의미가 있음.
            throw new IllegalStateException("Already used vacation!");
        }

        if(command.getApprove()){
            VacationApprovedEvent event = new VacationApprovedEvent();
            BeanUtils.copyProperties(this, event);     
    
            //setStatus("APPROVED"); // 휘발 - 무의미
            apply(event);
    
        }else{
            VacationRejectedEvent event = new VacationRejectedEvent();
            BeanUtils.copyProperties(this, event);     
    
            apply(event);
    
        }
    }

    @CommandHandler
    public void handle(ConfirmUsedCommand command){

        VacationUsedEvent event = new VacationUsedEvent();
        BeanUtils.copyProperties(this, event);     


        apply(event);

    }








    @EventSourcingHandler
    public void on(VacationRegisteredEvent event) {
        BeanUtils.copyProperties(event, this); //POST
        
    }


    @EventSourcingHandler
    public void on(VacationCancelledEvent event) {
        //BeanUtils.copyProperties(event, this);
        setStatus("CANCELLED");
        
    }


    @EventSourcingHandler
    public void on(VacationApprovedEvent event) {
       // BeanUtils.copyProperties(event, this);
        setStatus("APPROVED");
    }


    @EventSourcingHandler
    public void on(VacationRejectedEvent event) {
       // BeanUtils.copyProperties(event, this);
       setStatus("REJECTED");

    }


    @EventSourcingHandler
    public void on(VacationUsedEvent event) {
        //BeanUtils.copyProperties(event, this);
        setStatus("USED");

    }


}

