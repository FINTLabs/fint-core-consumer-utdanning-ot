package no.fintlabs.consumer.model.otungdom;

import no.fint.model.felles.kompleksedatatyper.Identifikator;
import no.fint.model.resource.utdanning.ot.OTUngdomResource;
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
public class OTUngdomService extends CacheService<OTUngdomResource> {

    private final OTUngdomKafkaConsumer elevfravarKafkaConsumer;
    private final OTUngdomLinker linker;

    public OTUngdomService(
            OTUngdomConfig config,
            CacheManager cacheManager,
            OTUngdomKafkaConsumer kafkaConsumer,
            OTUngdomLinker linker) {
        super(config, cacheManager, kafkaConsumer);
        this.elevfravarKafkaConsumer = kafkaConsumer;
        this.linker = linker;
    }

    @Override
    protected Cache<OTUngdomResource> initializeCache(CacheManager cacheManager, ConsumerConfig<OTUngdomResource> consumerConfig, String s) {
        return cacheManager.create(PackingTypes.POJO, consumerConfig.getOrgId(), consumerConfig.getResourceName());
    }

    @PostConstruct
    private void registerKafkaListener() {
        long retension = elevfravarKafkaConsumer.registerListener(OTUngdomResource.class, this::addResourceToCache);
        getCache().setRetentionPeriodInMs(retension);
    }

    private void addResourceToCache(ConsumerRecord<String, OTUngdomResource> consumerRecord) {
        this.eventLogger.logDataRecieved();
        if (consumerRecord.value() == null) {
            getCache().remove(consumerRecord.key());
        } else {
            OTUngdomResource OTUngdomResource = consumerRecord.value();
            linker.mapLinks(OTUngdomResource);
            getCache().put(consumerRecord.key(), OTUngdomResource, linker.hashCodes(OTUngdomResource));
        }
    }

    @Override
    public Optional<OTUngdomResource> getBySystemId(String systemId) {
        return getCache().getLastUpdatedByFilter(systemId.hashCode(),
                resource -> Optional
                        .ofNullable(resource)
                        .map(OTUngdomResource::getSystemId)
                        .map(Identifikator::getIdentifikatorverdi)
                        .map(systemId::equals)
                        .orElse(false));
    }
}