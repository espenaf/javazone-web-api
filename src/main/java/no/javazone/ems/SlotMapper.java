package no.javazone.ems;

import net.hamnaberg.json.Item;
import net.hamnaberg.json.Link;
import no.javazone.sessions.Slot;

import java.util.Optional;

class SlotMapper {
    public static Slot mapToSlot(final Item item) {
        Optional<Link> slotLink = item.linkByRel("slot item");
        if (!slotLink.isPresent()) {
            return Slot.tom();
        }
        Optional<String> slotPrompt = slotLink.get().getPrompt();
        if (!slotPrompt.isPresent()) {
            return Slot.tom();
        }
        return mapStringToSlot(slotPrompt.get());
    }

    private static Slot mapStringToSlot(String slotString) {
        String starter = null;
        String stopper = null;

        if (slotString != null) {
            String[] strings = slotString.split("\\+");
            if (strings.length == 2) {
                starter = strings[0];
                stopper = strings[1];
            }
        }
        return new Slot(starter, stopper);
    }
}
