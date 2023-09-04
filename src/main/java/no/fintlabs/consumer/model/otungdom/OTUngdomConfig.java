package no.fintlabs.consumer.model.otungdom;

import no.fint.model.resource.utdanning.ot.OtUngdomResource;
import no.fintlabs.core.consumer.shared.ConsumerProps;
import no.fintlabs.core.consumer.shared.resource.ConsumerConfig;
import org.springframework.stereotype.Component;

@Component
public class OtUngdomConfig extends ConsumerConfig<OtUngdomResource> {

    public OtUngdomConfig(ConsumerProps consumerProps) {
        super(consumerProps);
    }

    @Override
    protected String resourceName() {
        return "otungdom";
    }
}
