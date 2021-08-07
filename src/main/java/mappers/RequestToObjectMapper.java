package mappers;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class RequestToObjectMapper<T> {
    private static final Map<Class<?>, RequestToObjectMapper<?>> instances = new HashMap<>();
    private Method[] setterMethods;
    private Class<T> associatedClass;

    @SuppressWarnings("unchecked")
    public static <E> RequestToObjectMapper<E> from(Class<E> cls) {
        if (instances.containsKey(cls))
            return (RequestToObjectMapper<E>) instances.get(cls);
        RequestToObjectMapper<E> newMapper = new RequestToObjectMapper<>(cls);
        instances.put(cls, newMapper);
        return newMapper;
    }

    private RequestToObjectMapper(Class<T> associatedClass) {
        this.associatedClass = associatedClass;
        setterMethods = Stream.of(associatedClass.getMethods()).filter(m -> m.getName().startsWith("set")).toArray(Method[]::new);
    }

    public T map(HttpServletRequest req) {
        T newInstance = createNewInstance();
        for (Method setter : setterMethods)
            callIfValuePresent(setter, req, newInstance);
        return newInstance;
    }

    private T createNewInstance() {
        try {
            return associatedClass.getConstructor().newInstance();
        } catch (NoSuchMethodException e) {
            throw new RequestMappingException("Class does not have a no-args constructor", e);
        } catch (Exception e) {
            throw new RequestMappingException("Encountered error while trying to call no-arg constructor", e);
        }
    }

    private void callIfValuePresent(Method toCall, HttpServletRequest req, T inst) {
        String associatedFieldName = getFieldNameFromSetterName(toCall.getName());
        Object value = req.getParameter(associatedFieldName);
        Class<?> paramType = toCall.getParameterTypes()[0];

        if (value != null) {
            Object convertedValue = convertValue(value, paramType);
            try {
                toCall.invoke(inst, convertedValue);
            } catch (Exception e) {
                throw new RequestMappingException("Error while trying to invoke setter: '" + toCall.getName() + "'", e);
            }
        }
    }

    private String getFieldNameFromSetterName(String setterName) {
        return Character.toLowerCase(setterName.charAt(3)) + setterName.substring(4);
    }

    private Object convertValue(Object value, Class<?> toClass) {
        try {
            return toClass.getConstructor(String.class).newInstance(value);
        } catch (NoSuchMethodException e) {
            throw new RequestMappingException("Class '" + toClass.getName() + "' does not provide a String arg constructor", e);
        } catch (Exception e) {
            throw new RequestMappingException("Error while trying to invoke String-arg constructor", e);
        }
    }

}
