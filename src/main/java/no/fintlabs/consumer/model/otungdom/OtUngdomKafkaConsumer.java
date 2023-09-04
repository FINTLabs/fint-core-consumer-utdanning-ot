package no.fintlabs.consumer.model.otungdom;

import no.fint.model.resource.utdanning.ot.OtUngdomResource;
import no.fintlabs.core.consumer.shared.resource.kafka.EntityKafkaConsumer;
import no.fintlabs.kafka.common.ListenerBeanRegistrationService;
import no.fintlabs.kafka.entity.EntityConsumerFactoryService;
import no.fintlabs.kafka.entity.topic.EntityTopicService;
import org.springframework.stereotype.Service;

@Service
public class OtUngdomKafkaConsumer extends EntityKafkaConsumer<OtUngdomResource> {
    public OtUngdomKafkaConsumer(
            EntityConsumerFactoryService entityConsumerFactoryService,
            ListenerBeanRegistrationService listenerBeanRegistrationService,
            EntityTopicService entityTopicService,
            OtUngdomConfig config) {
        super(entityConsumerFactoryService,
                listenerBeanRegistrationService,
                entityTopicService,
                config);
    }
}
