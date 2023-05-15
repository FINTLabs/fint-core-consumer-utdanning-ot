package no.fintlabs.consumer.model.otungdom;


import no.fint.model.resource.utdanning.ot.OTUngdomResource;
import no.fint.model.resource.utdanning.ot.OTUngdomResources;
import no.fint.relations.FintLinker;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

@Component
public class OTUngdomLinker extends FintLinker<OTUngdomResource> {

    public OTUngdomLinker() {
        super(OTUngdomResource.class);
    }

    public void mapLinks(OTUngdomResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public OTUngdomResources toResources(Collection<OTUngdomResource> collection) {
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public OTUngdomResources toResources(Stream<OTUngdomResource> stream, int offset, int size, int totalItems) {
        OTUngdomResources resources = new OTUngdomResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(OTUngdomResource resource) {
        return getAllSelfHrefs(resource).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(OTUngdomResource resource) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(resource.getSystemId()) && !StringUtils.isEmpty(resource.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(resource.getSystemId().getIdentifikatorverdi(), "systemid"));
        }

        return builder.build();
    }

    int[] hashCodes(OTUngdomResource resource) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(resource.getSystemId()) && !StringUtils.isEmpty(resource.getSystemId().getIdentifikatorverdi())) {
            builder.add(resource.getSystemId().getIdentifikatorverdi().hashCode());
        }

        return builder.build().toArray();
    }
}