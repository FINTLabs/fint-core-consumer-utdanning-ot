package no.fintlabs.consumer.model.otungdom;

import lombok.extern.slf4j.Slf4j;
import no.fint.antlr.FintFilterService;
import no.fint.model.resource.utdanning.ot.OTUngdomResource;
import no.fint.relations.FintRelationsMediaType;
import no.fintlabs.consumer.config.RestEndpoints;
import no.fintlabs.core.consumer.shared.resource.ConsumerRestController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(name = "OTUngdom", value = RestEndpoints.OTUNGDOM, produces = {FintRelationsMediaType.APPLICATION_HAL_JSON_VALUE, MediaType.APPLICATION_JSON_VALUE})
public class OTUngdomController extends ConsumerRestController<OTUngdomResource> {

    public OTUngdomController(OTUngdomService service, OTUngdomLinker linker, FintFilterService oDataFilterService) {
        super(service, linker, oDataFilterService);
    }
}
