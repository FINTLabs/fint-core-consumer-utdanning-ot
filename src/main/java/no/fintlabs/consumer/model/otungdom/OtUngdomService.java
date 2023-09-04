package no.fintlabs.consumer.model.otungdom;

import no.fint.model.felles.kompleksedatatyper.Identifikator;
import no.fint.model.resource.utdanning.ot.OtUngdomResource;
import no.fintlabs.cache.Cache;
import no.fintlabs.cache.CacheManager;
import no.fintlabs.cache.packing.PackingTypes;
import no.fintlabs.core.consumer.shared.resource.CacheService;
import no.fintlabs.core.consumer.shared.resource.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class OtUngdomService extends CacheService<OtUngdomResource> {

    private final OtUngdomKafkaConsumer elevfravarKafkaConsumer;
    private final OtUngdomLinker linker;

    public OtUngdomService(
            OtUngdomConfig config,
            CacheManager cacheManager,
            OtUngdomKafkaConsumer kafkaConsumer,
            OtUngdomLinker linker) {
        super(config, cacheManager, kafkaConsumer);
        this.elevfravarKafkaConsumer = kafkaConsumer;
        this.linker = linker;
    }

    @Override
    protected Cache<OtUngdomResource> initializeCache(CacheManager cacheManager, ConsumerConfig<OtUngdomResource> consumerConfig, String s) {
        return cacheManager.create(PackingTypes.POJO, consumerConfig.getOrgId(), consumerConfig.getResourceName());
    }

    @PostConstruct
    private void registerKafkaListener() {
        long retension = elevfravarKafkaConsumer.registerListener(OtUngdomResource.class, this::addResourceToCache);
        getCache().setRetentionPeriodInMs(retension);
    }

    private void addResourceToCache(ConsumerRecord<String, OtUngdomResource> consumerRecord) {
        this.eventLogger.logDataRecieved();
        if (consumerRecord.value() == null) {
            getCache().remove(consumerRecord.key());
        } else {
            OtUngdomResource OtUngdomResource = consumerRecord.value();
            linker.mapLinks(OtUngdomResource);
            getCache().put(consumerRecord.key(), OtUngdomResource, linker.hashCodes(OtUngdomResource));
        }
    }

    @Override
    public Optional<OtUngdomResource> getBySystemId(String systemId) {
        return getCache().getLastUpdatedByFilter(systemId.hashCode(),
                resource -> Optional
                        .ofNullable(resource)
                        .map(OtUngdomResource::getSystemId)
                        .map(Identifikator::getIdentifikatorverdi)
                        .map(systemId::equals)
                        .orElse(false));
    }
}