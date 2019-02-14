package util;

import java.util.ArrayList;
import java.util.List;
import org.picocontainer.DefaultPicoContainer;

public class PicoContainerHelper {

    private static PicoContainerHelper instance;
    private DefaultPicoContainer picoContainer;
    private List<Object> componentKeysAndComponents;

    private PicoContainerHelper() {
        picoContainer = new DefaultPicoContainer();
        componentKeysAndComponents = new ArrayList<>();
    }

    private static DefaultPicoContainer getContainer() {
        if (instance == null) {
            instance = new PicoContainerHelper();
        }
        return instance.picoContainer;
    }

    private static List<Object> getComponentKeysAndComponents() {
        if (instance == null) {
            instance = new PicoContainerHelper();
        }
        return instance.componentKeysAndComponents;
    }

    private static void removeKeyComponent(Object keyComponentToRemove) {
        getComponentKeysAndComponents()
                .removeIf(keyComponent -> keyComponent.equals(keyComponentToRemove));
    }

    public static void addToContainer(Object object) {
        if (getFromContainer(object.getClass()) != null) {
            updateInContainer(object);
            return;
        }
        getContainer().addComponent(object);
        getComponentKeysAndComponents().add(object);
    }

    public static void addToContainer(Object componentKey, Object object) {
        getContainer().addComponent(componentKey, object);
        getComponentKeysAndComponents().add(componentKey);
    }

    public static void removeFromContainer(Object keyComponent) {
        getContainer().removeComponent(keyComponent);
        getContainer().removeComponent(keyComponent.getClass());
        removeKeyComponent(keyComponent);
    }

    public static void updateInContainer(Object object) {
        getContainer().removeComponent(object);
        getContainer().removeComponent(object.getClass());
        getContainer().addComponent(object);
    }

    public static void updateInContainer(Object componentKey, Object object) {
        getContainer().removeComponent(componentKey);
        getContainer().addComponent(componentKey, object);
    }

    public static Object getFromContainer(Object componentKey) {
        return getContainer().getComponent(componentKey);
    }

    public static <T> T getFromContainer(Class<T> object) {
        return getContainer().getComponent(object);
    }

    public static <T> T getFromContainer(Object componentKey, Class<T> object) {
        return object.cast(getContainer().getComponent(componentKey));
    }

    public static void cleanContainer() {
        for (int i = getComponentKeysAndComponents().size() - 1; i > -1; i--) {
            removeFromContainer(getComponentKeysAndComponents().get(i));
        }
    }
}