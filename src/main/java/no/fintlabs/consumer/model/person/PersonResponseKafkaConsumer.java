package no.fintlabs.consumer.model.person;

import no.fint.model.resource.felles.PersonResource;
import no.fintlabs.core.consumer.shared.resource.event.EventResponseKafkaConsumer;
import no.fintlabs.kafka.event.EventConsumerFactoryService;
import org.springframework.stereotype.Service;

@Service
public class PersonResponseKafkaConsumer extends EventResponseKafkaConsumer<PersonResource> {

    public PersonResponseKafkaConsumer(EventConsumerFactoryService eventConsumerFactoryService, PersonConfig personConfig, PersonLinker personLinker) {
        super(eventConsumerFactoryService, personConfig, personLinker);
    }

}
