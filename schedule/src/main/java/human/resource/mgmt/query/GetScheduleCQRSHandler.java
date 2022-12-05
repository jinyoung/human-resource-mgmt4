package human.resource.mgmt.query;

import human.resource.mgmt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("getSchedule")
public class JPAGetScheduleQueryHandler {

    @Autowired
    private CalendarReadModelRepository repository;

    @QueryHandler
    public List<Calendar> handle(GetScheduleQuery query) {
        return repository.findAll();
    }

    @QueryHandler
    public Optional<Calendar> handle(GetScheduleSingleQuery query) {
        return repository.findById(query.getUserId());
    }

    @EventHandler
    public void whenScheduleAdded_then_CREATE(ScheduleAddedEvent event)
        throws Exception {
        CalendarReadModel entity = new CalendarReadModel();
        CalendarAggregate aggregate = new CalendarAggregate();
        aggregate.on(event);

        BeanUtils.copyProperties(aggregate, entity);

        repository.save(entity);
    }

    @EventHandler
    public void whenScheduleCanceled_then_CREATE(ScheduleCanceledEvent event)
        throws Exception {
        CalendarReadModel entity = new CalendarReadModel();
        CalendarAggregate aggregate = new CalendarAggregate();
        aggregate.on(event);

        BeanUtils.copyProperties(aggregate, entity);

        repository.save(entity);
    }

    @EventHandler
    public void whenCalendarRegistered_then_CREATE(
        CalendarRegisteredEvent event
    ) throws Exception {
        CalendarReadModel entity = new CalendarReadModel();
        CalendarAggregate aggregate = new CalendarAggregate();
        aggregate.on(event);

        BeanUtils.copyProperties(aggregate, entity);

        repository.save(entity);
    }
}
