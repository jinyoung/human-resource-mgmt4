package human.resource.mgmt.query;


import human.resource.mgmt.event.*;
import human.resource.mgmt.aggregate.*;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.io.IOException;

@Service
@ProcessingGroup("getSchedule")
public class GetScheduleCQRSHandler {

//<<< EDA / CQRS
    @Autowired
    private CalendarReadModelRepository repository;

    @QueryHandler
    public List<CalendarReadModel> handle(GetScheduleQuery query) {
        return repository.findAll();
    }

    @QueryHandler
    public Optional<CalendarReadModel> handle(GetScheduleSingleQuery query) {
        return repository.findById(query.getUserId());
    }


    @EventHandler
    public void whenScheduleAdded_then_UPDATE (ScheduleAddedEvent event) throws Exception{
        repository.findById(event.get())
            .ifPresent(entity -> {

                CalendarAggregate aggregate = new CalendarAggregate();
       
                BeanUtils.copyProperties(entity, aggregate);
                aggregate.on(event);
                BeanUtils.copyProperties(aggregate, entity);

                repository.save(entity);

            });

    }

    @EventHandler
    public void whenScheduleCanceled_then_UPDATE (ScheduleCanceledEvent event) throws Exception{
        repository.findById(event.get())
            .ifPresent(entity -> {

                CalendarAggregate aggregate = new CalendarAggregate();
       
                BeanUtils.copyProperties(entity, aggregate);
                aggregate.on(event);
                BeanUtils.copyProperties(aggregate, entity);

                repository.save(entity);

            });

    }
    @EventHandler
    public void whenCalendarRegistered_then_CREATE (CalendarRegisteredEvent event) throws Exception{
        CalendarReadModel entity = new CalendarReadModel();
        CalendarAggregate aggregate = new CalendarAggregate();
        aggregate.on(event);

        BeanUtils.copyProperties(aggregate, entity);
        
        repository.save(entity);

    }

//>>> EDA / CQRS
}
