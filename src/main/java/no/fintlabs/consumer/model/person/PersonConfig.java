package no.fintlabs.consumer.model.person;

import no.fint.model.resource.felles.PersonResource;
import no.fintlabs.core.consumer.shared.ConsumerProps;
import no.fintlabs.core.consumer.shared.resource.ConsumerConfig;
import org.springframework.stereotype.Component;

@Component
public class PersonConfig extends ConsumerConfig<PersonResource> {

    public PersonConfig(ConsumerProps consumerProps) {
        super(consumerProps);
    }

    @Override
    protected String resourceName() {
        return "person";
    }
}
