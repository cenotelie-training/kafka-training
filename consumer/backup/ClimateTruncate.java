package fr.cenotelie.training.kafka.consumer.services;

import fr.cenotelie.training.kafka.consumer.data.ClimatEvent;
import io.micronaut.configuration.kafka.streams.ConfiguredStreamBuilder;
import io.micronaut.context.annotation.Factory;
import io.micronaut.serde.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import java.io.IOException;
import java.util.Properties;
@Factory
public class ClimateTruncate {

    public static final String STREAM_CLIMATE_TRUNC = "climat-truncate";
    public static final String INPUT = "mytopic";
    public static final String OUTPUT = "mytopic2";

    private ObjectMapper objectMapper;

    @PostConstruct
    private void init() {
        objectMapper = ObjectMapper.getDefault();
    }


    private String doTruncate(String value) {
        ClimatEvent event = null;
        try {
            event = objectMapper.readValue(value, ClimatEvent.class);
            ClimatEvent tEvent = new ClimatEvent(
                    Math.round(event.temperature()),
                    Math.round(event.pressure()),
                    Math.round(event.humidity()),
                    event.datetime()
            );
            return  objectMapper.writeValueAsString(tEvent);
        } catch (IOException e) {
            return "";
        }
    }

    @Singleton
    @Named(STREAM_CLIMATE_TRUNC)
    KStream<String, String> wordCountStream(ConfiguredStreamBuilder builder) {
        Properties props = builder.getConfiguration();
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KStream<String, String> source = builder.stream(INPUT);
        source.mapValues(this::doTruncate).to(OUTPUT);

        return source;
    }

}
