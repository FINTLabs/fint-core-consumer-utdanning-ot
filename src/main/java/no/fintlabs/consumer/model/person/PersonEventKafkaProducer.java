package no.fintlabs.consumer.model.person;

import no.fintlabs.core.consumer.shared.resource.kafka.EventKafkaProducer;
import no.fintlabs.kafka.event.EventProducerFactory;
import no.fintlabs.kafka.event.topic.EventTopicService;
import org.springframework.stereotype.Service;

@Service
public class PersonEventKafkaProducer extends EventKafkaProducer {
    public PersonEventKafkaProducer(EventProducerFactory eventProducerFactory, PersonConfig personConfig, EventTopicService eventTopicService) {
        super(eventProducerFactory, personConfig, eventTopicService);
    }
}
