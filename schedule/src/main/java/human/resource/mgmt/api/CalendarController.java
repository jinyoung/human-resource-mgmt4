package human.resource.mgmt.api;

import human.resource.mgmt.aggregate.*;
import human.resource.mgmt.command.*;
import human.resource.mgmt.query.GetScheduleQuery;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalendarController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public CalendarController(
        CommandGateway commandGateway,
        QueryGateway queryGateway
    ) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @RequestMapping(
        value = "/calendars/{id}/add",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public CompletableFuture addCalendar(
        @PathVariable("id") String id,
        @RequestBody AddCalendarCommand addCalendarCommand
    ) throws Exception {
        System.out.println("##### /calendar/addCalendar  called #####");
        addCalendarCommand.setUserId(id);
        // send command
        return commandGateway.send(addCalendarCommand);
    }

    @RequestMapping(
        value = "/calendars/{id}/cancel",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public CompletableFuture cancelCalendar(
        @PathVariable("id") String id,
        @RequestBody CancelCalendarCommand cancelCalendarCommand
    ) throws Exception {
        System.out.println("##### /calendar/cancelCalendar  called #####");
        cancelCalendarCommand.setUserId(id);
        // send command
        return commandGateway.send(cancelCalendarCommand);
    }

    @RequestMapping(value = "/calendars", method = RequestMethod.POST)
    public CompletableFuture registerCalendar(
        @RequestBody RegisterCalendarCommand registerCalendarCommand
    ) throws Exception {
        System.out.println("##### /calendar/registerCalendar  called #####");

        // send command
        return commandGateway
            .send(registerCalendarCommand)
            .thenApply(id -> {
                CalendarAggregate resource = new CalendarAggregate();
                BeanUtils.copyProperties(registerCalendarCommand, resource);

                resource.setUserId((String) id);

                EntityModel<CalendarAggregate> model = EntityModel.of(resource);
                model.add(
                    Link.of("/calendars/" + resource.getUserId()).withSelfRel()
                );

                return new ResponseEntity<>(model, HttpStatus.OK);
            });
    }

    @Autowired
    EventStore eventStore;

    @GetMapping(value = "/calendars/{id}/events")
    public ResponseEntity getEvents(@PathVariable("id") String id) {
        ArrayList resources = new ArrayList<CalendarAggregate>();
        eventStore.readEvents(id).asStream().forEach(resources::add);

        CollectionModel<CalendarAggregate> model = CollectionModel.of(
            resources
        );

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    @GetMapping(value="/calendars/search/findByDateRange")
    public ResponseEntity findByDateRange(GetScheduleQuery query) {
        
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
