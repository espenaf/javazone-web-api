package no.javazone.api.sessions.dto;

import no.javazone.api.links.LinkDTO;
import no.javazone.api.links.LinkDTOMapper;
import no.javazone.api.speaker.SpeakerBildeUriCreator;
import no.javazone.devnull.DevNullUriCreator;
import no.javazone.sessions.Event;
import no.javazone.sessions.Foredragsholder;
import no.javazone.sessions.Session;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SessionDTOMapper {
    public static List<SessionDTO> toSessionDTOs(
            Event event,
            URI contextPath,
            URI contextRoot,
            DevNullUriCreator devNullUriCreator) {
        return event
                .getSessions()
                .stream()
                .map(session -> new SessionDTO(
                        session.getTittel(),
                        session.getFormat(),
                        session.getSlot().getStarter(),
                        session.getSlot().getStopper(),
                        toForedragsholderDTO(session.getForedragsholdere(), contextRoot),
                        session.getSprak(),
                        session.getNiva(),
                        createLinks(contextPath, session, devNullUriCreator),
                        session.getRom(),
                        session.getNokkelord()))
                .collect(Collectors.toList());
    }

    private static List<ForedragsholderDTO> toForedragsholderDTO(
            List<Foredragsholder> foredragsholdere,
            URI contextRoot)
    {
        return foredragsholdere
                .stream()
                .map(foredragsholder -> new ForedragsholderDTO(
                        foredragsholder.getNavn(),
                        SpeakerBildeUriCreator.createBildeUrl(foredragsholder, contextRoot)))
                .collect(Collectors.toList());
    }

    private static List<LinkDTO> createLinks(URI contextPath, Session session, DevNullUriCreator devNullUriCreator) {
        ArrayList<LinkDTO> links = new ArrayList<>();

        links.add(createDetaljerLink(session, contextPath));
        LinkDTOMapper.toLinkDTO("video", session.getVideoUri()).ifPresent(links::add);
        LinkDTOMapper.toLinkDTO("feedback", Optional.of(devNullUriCreator.mapToUri(session.getEmsIds()))).ifPresent(links::add);

        return links;
    }


    private static LinkDTO createDetaljerLink(Session session, URI contextPath) {
        UriBuilder absolutePathBuilder = UriBuilder.fromUri(contextPath);
        return new LinkDTO("detaljer", absolutePathBuilder.path(session.getId().getValue()).build());
    }
}
