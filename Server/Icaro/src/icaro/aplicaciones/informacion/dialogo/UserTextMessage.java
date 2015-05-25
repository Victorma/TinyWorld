package icaro.aplicaciones.informacion.dialogo;

import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

/**
 * This class represents an user text message with annotations.
 *
 * @author Gorkin
 */
public final class UserTextMessage {
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

    public UserTextAnnotation getAnnotation(String value) {
        return (UserTextAnnotation) CollectionUtils.find(
                annotations_,
                new Predicate() {
                    @Override
                    public boolean evaluate(Object item) {
                        return ((UserTextAnnotation) item).getType().equalsIgnoreCase(value);
                    }
                });
    }

    public UserTextAnnotation getAnnotationEnabled(String value) {
        return (UserTextAnnotation) CollectionUtils.find(
                annotations_,
                new Predicate() {
                    @Override
                    public boolean evaluate(Object item) {
                        UserTextAnnotation victim = (UserTextAnnotation) item;
                        return victim.getType().equalsIgnoreCase(value) && victim.isEnable();
                    }
                });
    }

    public void setAllAnnotationsEnable(String victim, boolean value) {
        for (UserTextAnnotation item : annotations_) {
            if (item.getType().equalsIgnoreCase(victim)) {
                item.setEnable(value);
            }
        }
    }

    public void setAnnotationEnable(String victim, boolean value) {
        UserTextAnnotation annotation = getAnnotation(victim);
        if (annotation != null) {
            annotation.setEnable(value);
        }
    }

    public void disableAllAnnotations(String victim) {
        setAllAnnotationsEnable(victim, false);
    }

    public void disableAnnotation(String victim) {
        setAnnotationEnable(victim, false);
    }

    public boolean containsAnnotation(String value) {
        return getAnnotation(value) != null;
    }

    public boolean containsAnnotationEnabled(String value) {
        return getAnnotationEnabled(value) != null;
    }

    public boolean anyAnnotationEnabled() {
        for (UserTextAnnotation item : annotations_) {
            if (item.isEnable()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        String annotations = "";
        for (UserTextAnnotation annotation : annotations_) {
            annotations += annotation + ", ";
        }
        annotations = annotations.substring(0, annotations.length() - 2);
        return "{ owner : \"" + owner_ + "\", content : \"" + content_ +
               "\", annotations : [" + annotations + "] }";
    }
}
