package no.fintlabs.consumer.model.person;

import lombok.extern.slf4j.Slf4j;
import no.fint.antlr.FintFilterService;
import no.fint.model.resource.felles.PersonResource;
import no.fint.relations.FintRelationsMediaType;
import no.fintlabs.consumer.config.RestEndpoints;
import no.fintlabs.core.consumer.shared.resource.CacheService;
import no.fintlabs.core.consumer.shared.resource.WriteableConsumerRestController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(name = "Person", value = RestEndpoints.PERSON, produces = {FintRelationsMediaType.APPLICATION_HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
public class PersonController extends WriteableConsumerRestController<PersonResource> {

    public PersonController(
            CacheService<PersonResource> cacheService,
            PersonLinker fintLinker,
            PersonConfig personConfig,
            PersonEventKafkaProducer personEventKafkaProducer,
            PersonResponseKafkaConsumer personResponseKafkaConsumer,
            FintFilterService odataFilterService,
            PersonRequestKafkaConsumer personRequestKafkaConsumer) {
        super(cacheService, fintLinker, personConfig, personEventKafkaProducer, personResponseKafkaConsumer, odataFilterService, personRequestKafkaConsumer);
    }
}
