package no.fintlabs.consumer.model.person;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import no.fint.model.felles.kompleksedatatyper.Identifikator;
import no.fint.model.resource.felles.PersonResource;
import no.fint.model.resource.utdanning.ot.OtUngdomResource;
import no.fintlabs.cache.Cache;
import no.fintlabs.cache.CacheManager;
import no.fintlabs.cache.packing.PackingTypes;
import no.fintlabs.core.consumer.shared.resource.CacheService;
import no.fintlabs.core.consumer.shared.resource.ConsumerConfig;
import no.fintlabs.core.consumer.shared.resource.kafka.EntityKafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
@Service
public class PersonService extends CacheService<PersonResource> {

    private final EntityKafkaConsumer<PersonResource> entityKafkaConsumer;
    private final PersonLinker linker;

    public PersonService(
            PersonConfig consumerConfig,
            CacheManager cacheManager,
            PersonEntityKafkaConsumer entityKafkaConsumer,
            PersonLinker linker) {
        super(consumerConfig, cacheManager, entityKafkaConsumer);
        this.entityKafkaConsumer = entityKafkaConsumer;
        this.linker = linker;
    }

    @Override
    protected Cache<PersonResource> initializeCache(CacheManager cacheManager, ConsumerConfig<PersonResource> consumerConfig, String s) {
        return cacheManager.create(PackingTypes.POJO, consumerConfig.getOrgId(), consumerConfig.getResourceName());
    }

    @PostConstruct
    private void registerKafkaListener() {
        entityKafkaConsumer.registerListener(PersonResource.class, this::addResourceToCache);
    }

    private void addResourceToCache(ConsumerRecord<String, PersonResource> consumerRecord) {
        updateRetensionTime(consumerRecord.headers().lastHeader("topic-retension-time"));
        this.eventLogger.logDataRecieved();
        PersonResource resource = consumerRecord.value();
        if (resource == null) {
            getCache().remove(consumerRecord.key());
        } else {
            PersonResource personResource = consumerRecord.value();
            linker.mapLinks(personResource);
            getCache().put(consumerRecord.key(), personResource, linker.hashCodes(personResource));
        }
    }

    @Override
    public Optional<PersonResource> getBySystemId(String systemId) {
        return getCache().getLastUpdatedByFilter(systemId.hashCode(),
                (resource) -> Optional
                        .ofNullable(resource)
                        .map(PersonResource::getFodselsnummer)
                        .map(Identifikator::getIdentifikatorverdi)
                        .map(systemId::equals)
                        .orElse(false)
        );
    }
}
