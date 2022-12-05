package human.resource.mgmt.api;

import human.resource.mgmt.query.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetScheduleQueryController {

    private final QueryGateway queryGateway;

    public GetScheduleQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/calendars")
    public CompletableFuture findAll() {
        return queryGateway
            .query(
                new GetScheduleQuery(),
                ResponseTypes.multipleInstancesOf(CalendarReadModel.class)
            )
            .thenApply(resources -> {
                CollectionModel<CalendarReadModel> model = CollectionModel.of(
                    resources
                );

                return new ResponseEntity<>(model, HttpStatus.OK);
            });
    }

    @GetMapping("/calendars/{id}")
    public CompletableFuture findById(@PathVariable("id") String id) {
        GetScheduleSingleQuery query = new GetScheduleSingleQuery();
        query.setUserId(id);

        return queryGateway
            .query(
                query,
                ResponseTypes.optionalInstanceOf(CalendarReadModel.class)
            )
            .thenApply(resource -> {
                if (!resource.isPresent()) {
                    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                }

                EntityModel<CalendarReadModel> model = EntityModel.of(
                    resource.get()
                );
                model.add(
                    Link
                        .of("/calendars/" + resource.get().getUserId())
                        .withSelfRel()
                );

                return new ResponseEntity<>(model, HttpStatus.OK);
            })
            .exceptionally(ex -> {
                throw new RuntimeException(ex);
            });
    }
}
