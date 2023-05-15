package no.fintlabs.consumer.model.otungdom;

import no.fint.model.resource.utdanning.ot.OTUngdomResource;
import no.fintlabs.core.consumer.shared.ConsumerProps;
import no.fintlabs.core.consumer.shared.resource.ConsumerConfig;
import org.springframework.stereotype.Component;

@Component
public class OTUngdomConfig extends ConsumerConfig<OTUngdomResource> {

    public OTUngdomConfig(ConsumerProps consumerProps) {
        super(consumerProps);
    }

    @Override
    protected String resourceName() {
        return "larling";
    }
}
