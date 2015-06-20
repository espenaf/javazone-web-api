package no.javazone.ems;

import net.hamnaberg.json.Item;
import no.javazone.sessions.SessionId;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class SessionIdMapper {

    private static final Logger LOG = LoggerFactory.getLogger(SessionIdMapper.class);

    public static SessionId generateId(final Item item) {
        String idAsString = generateIdString(item);
        return new SessionId(idAsString);
    }

    public static String generateIdString(Item item) {
        Optional<URI> uriOptional = item.getHref();
        if (!uriOptional.isPresent()) {
            throw new IllegalStateException("manglet href");
        }
        URI href = uriOptional.get();
        return new String(Hex.encodeHex(getMda().digest(href.toString().getBytes())));
    }

    private static MessageDigest getMda() {
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            LOG.error("Kunne ikke lage SHA-256", e);
            throw new IllegalStateException();
        }
    }
}
