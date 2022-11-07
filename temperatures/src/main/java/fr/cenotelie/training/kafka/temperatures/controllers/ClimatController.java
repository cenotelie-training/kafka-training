package fr.cenotelie.training.kafka.temperatures.controllers;

import fr.cenotelie.training.kafka.temperatures.data.ClimatEvent;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.sse.Event;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Controller("/climat-stream")
public class ClimatController {
    private Random random = new Random();

    private ClimatEvent newEvent() {
        return new ClimatEvent(random.nextDouble(30.0), random.nextDouble(100) + 1000,
                random.nextDouble(100.0), LocalDateTime.now());
    }

    @ExecuteOn(TaskExecutors.IO)
    @Get(produces = MediaType.TEXT_EVENT_STREAM)
    public Flux<Object> getClimat() {
        return Flux.generate(sink -> sink.next(Event.of(newEvent())))
                .sample(Duration.ofSeconds(5));
    }

}
