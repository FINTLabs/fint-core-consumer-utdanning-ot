package no.fintlabs.consumer.model.person;

import no.fint.model.resource.felles.PersonResource;
import no.fintlabs.core.consumer.shared.resource.event.EventRequestKafkaConsumer;
import no.fintlabs.kafka.event.EventConsumerFactoryService;
import org.springframework.stereotype.Service;

@Service
public class PersonRequestKafkaConsumer extends EventRequestKafkaConsumer<PersonResource> {
    public PersonRequestKafkaConsumer(EventConsumerFactoryService eventConsumerFactoryService, PersonConfig personConfig) {
        super(eventConsumerFactoryService, personConfig);
    }
}
