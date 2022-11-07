package fr.cenotelie.training.kafka.temperatures.data;

import io.micronaut.serde.annotation.Serdeable;

import java.time.LocalDateTime;

//Issue: datetime serialisation causes bug

@Serdeable
public record ClimatEvent(double temperature, double pressure, double humidity, LocalDateTime datetime) {}
