package icaro.aplicaciones.informacion.dialogo;

import dasi.util.RandomUtil;
import java.util.List;
import java.util.ArrayList;

/**
 * This class represents a table of responses.
 *
 * @author Gorkin
 */
public final class TableOfResponses {
    //****************************************************************************************************
    // Types:
    //****************************************************************************************************

    private class Response {
        public String text;
        public boolean used;
        public Response(String text) {
            this.text = text;
            this.used = false;
        }
    }

    //****************************************************************************************************
    // Fields:
    //****************************************************************************************************

    private final List<Response> candidates_ = new ArrayList<>();

    //****************************************************************************************************
    // Constructors:
    //****************************************************************************************************

    public TableOfResponses() {
    }

    //****************************************************************************************************
    // Methods:
    //****************************************************************************************************

    public void clear() {
        candidates_.clear();
    }

    public boolean add(String value) {
        return candidates_.add(new Response(value));
    }

    public void resetUsedFlag() {
        for (Response item : candidates_) {
            item.used = false;
        }
    }

    public String getRandom() {
        List<Integer> notUsedIndexes = getNotUsedIndexes();
        if (notUsedIndexes.isEmpty()) {
            resetUsedFlag();
            return getAndUse(RandomUtil.getNextInt(candidates_.size()));
        } else {
            int index = RandomUtil.getNextInt(notUsedIndexes.size());
            return getAndUse(notUsedIndexes.get(index));
        }
    }

    private String getAndUse(int index) {
        Response victim = candidates_.get(index);
        victim.used = true;
        return victim.text;
    }

    private List<Integer> getNotUsedIndexes() {
        List<Integer> victims = new ArrayList<>();
        for (int i = 0; i < candidates_.size(); i++) {
            if (!candidates_.get(i).used) {
                victims.add(i);
            }
        }
        return victims;
    }
}
