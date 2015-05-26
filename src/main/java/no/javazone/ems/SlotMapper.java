package no.javazone.ems;

import net.hamnaberg.json.Item;
import net.hamnaberg.json.Link;
import no.javazone.sessions.Slot;

import java.util.Optional;

class SlotMapper {
    public static Slot mapToSlot(final Item item) {
        return item
                .linkByRel("slot item")
                .flatMap(Link::getPrompt)
                .map(SlotMapper::mapStringToSlot)
                .orElse(Slot.tom());
    }

    private static Slot mapStringToSlot(String slotString) {
        if (slotString != null) {
            String[] strings = slotString.split("\\+");
            if (strings.length == 2) {
                String starter = strings[0];
                String stopper = strings[1];
                return new Slot(starter, stopper);
            }
        }
        return Slot.tom();
    }
}
