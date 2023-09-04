package no.fintlabs.consumer.model.otungdom;


import no.fint.model.resource.utdanning.ot.OtUngdomResource;
import no.fint.model.resource.utdanning.ot.OtUngdomResources;
import no.fint.relations.FintLinker;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

@Component
public class OtUngdomLinker extends FintLinker<OtUngdomResource> {

    public OtUngdomLinker() {
        super(OtUngdomResource.class);
    }

    public void mapLinks(OtUngdomResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public OtUngdomResources toResources(Collection<OtUngdomResource> collection) {
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public OtUngdomResources toResources(Stream<OtUngdomResource> stream, int offset, int size, int totalItems) {
        OtUngdomResources resources = new OtUngdomResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(OtUngdomResource resource) {
        return getAllSelfHrefs(resource).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(OtUngdomResource resource) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(resource.getSystemId()) && !StringUtils.isEmpty(resource.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(resource.getSystemId().getIdentifikatorverdi(), "systemid"));
        }

        return builder.build();
    }

    int[] hashCodes(OtUngdomResource resource) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(resource.getSystemId()) && !StringUtils.isEmpty(resource.getSystemId().getIdentifikatorverdi())) {
            builder.add(resource.getSystemId().getIdentifikatorverdi().hashCode());
        }

        return builder.build().toArray();
    }
}