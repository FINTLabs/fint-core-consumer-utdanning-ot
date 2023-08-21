package no.fintlabs.consumer.model.person;

import no.fint.model.resource.felles.PersonResource;
import no.fint.model.resource.felles.PersonResources;
import no.fint.relations.FintLinker;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

@Component
public class PersonLinker extends FintLinker<PersonResource> {

    public PersonLinker() {
        super(PersonResource.class);
    }

    public void mapLinks(PersonResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public PersonResources toResources(Collection<PersonResource> collection) {
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public PersonResources toResources(Stream<PersonResource> stream, int offset, int size, int totalItems) {
        PersonResources resources = new PersonResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(PersonResource resource) {
        return getAllSelfHrefs(resource).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(PersonResource resource) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(resource.getFodselsnummer()) && !StringUtils.isEmpty(resource.getFodselsnummer().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(resource.getFodselsnummer().getIdentifikatorverdi(), "fodselsnummer"));
        }

        return builder.build();
    }

    int[] hashCodes(PersonResource resource) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(resource.getFodselsnummer()) && !StringUtils.isEmpty(resource.getFodselsnummer().getIdentifikatorverdi())) {
            builder.add(resource.getFodselsnummer().getIdentifikatorverdi().hashCode());
        }

        return builder.build().toArray();
    }
}