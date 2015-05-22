package icaro.aplicaciones.informacion.dialogo;

import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

/**
 * This class represents an user text message with annotations.
 *
 * @author Gorkin
 */
public class UserTextMessage {
    //****************************************************************************************************
    // Fields:
    //****************************************************************************************************
    
    private final String owner_;
    private final String content_;
    private final List<UserTextAnnotation> annotations_;

    //****************************************************************************************************
    // Constructors:
    //****************************************************************************************************
    
    public UserTextMessage(String owner, String content, List<UserTextAnnotation> annotations) {
        owner_ = owner;
        content_ = content;
        annotations_ = annotations;
    }
    
    //****************************************************************************************************
    // Properties:
    //****************************************************************************************************

    public String getOwner() {
        return owner_;
    }

    public String getContent() {
        return content_;
    }

    public List<UserTextAnnotation> getAnnotations() {
        return annotations_;
    }

    //****************************************************************************************************
    // Methods:
    //****************************************************************************************************

    public boolean containsAnnotation(String value) {
        UserTextAnnotation victim = (UserTextAnnotation) CollectionUtils.find(
                annotations_,
                new Predicate() {
                    @Override
                    public boolean evaluate(Object item) {
                        return ((UserTextAnnotation) item).getType().equalsIgnoreCase(value);
                    }
                });
        return victim != null;
    }
    
    @Override
    public String toString() {
        String annotations = "";
        for (UserTextAnnotation annotation : annotations_) {
            annotations += annotation + ", ";
        }
        annotations = annotations.substring(0, annotations.length() - 2);
        return "{ owner : " + owner_ + ", content : " + content_ + ", annotations : [" + annotations + "] }";
    }
}
