package mappers;

import javax.servlet.http.HttpServletRequest;

public interface RequestToObjectMapper {
    Object map(HttpServletRequest req);
}
