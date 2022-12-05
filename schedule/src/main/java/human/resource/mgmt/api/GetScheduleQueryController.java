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

    @GetMapping("/getSchedules")
    public CompletableFuture findAll() {
        return queryGateway
            .query(
                new GetScheduleQuery(),
                ResponseTypes.multipleInstancesOf(GetSchedule.class)
            )
            .thenApply(resources -> {
                CollectionModel<GetSchedule> model = CollectionModel.of(
                    resources
                );

                return new ResponseEntity<>(model, HttpStatus.OK);
            });
    }

    @GetMapping("/getSchedules/{id}")
    public CompletableFuture findById(@PathVariable("id") Long id) {
        GetScheduleSingleQuery query = new GetScheduleSingleQuery();
        query.setId(id);

        return queryGateway
            .query(query, ResponseTypes.optionalInstanceOf(GetSchedule.class))
            .thenApply(resource -> {
                if (!resource.isPresent()) {
                    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
                }

                EntityModel<GetSchedule> model = EntityModel.of(resource.get());
                model.add(
                    Link
                        .of("/getSchedules/" + resource.get().getId())
                        .withSelfRel()
                );

                return new ResponseEntity<>(model, HttpStatus.OK);
            })
            .exceptionally(ex -> {
                throw new RuntimeException(ex);
            });
    }
}
