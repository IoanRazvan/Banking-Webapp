package mappers;

import business.User;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

public class RequestToUserMapper implements RequestToObjectMapper {
    private static RequestToUserMapper instance;

    private RequestToUserMapper() {}

    public static RequestToUserMapper getMapper() {
        if (instance == null)
            instance = new RequestToUserMapper();
        return instance;
    }

    private String getFieldNameFromSetterName(String setterName) {
        return Character.toLowerCase(setterName.charAt(3)) + setterName.substring(4);
    }

    @Override
    public User map(HttpServletRequest req) {
        User u = new User();
        Method[] methods = User.class.getMethods();
        String methodName;
        Object value;
        for (Method m : methods)
            callIfSetterAndValuePresent(u, m, req);
        return u;
    }

    private void callIfSetterAndValuePresent(User u, Method m, HttpServletRequest req) {
        String methodName = m.getName();
        if (methodName.startsWith("set")) {
            String fieldName = getFieldNameFromSetterName(methodName);
            Object value = req.getParameter(fieldName);
            if (value != null)
                callSetter(u, m, value);
        }
    }

    private void callSetter(User u, Method m, Object value) {
        try {
            m.invoke(u, value);
        } catch (Exception e) {
            throw new RequestMappingException("Error while calling invoke", e);
        }
    }
}
