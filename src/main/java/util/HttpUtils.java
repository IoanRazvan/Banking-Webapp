package util;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class HttpUtils {
    private static ObjectMapper jsonMapper = new ObjectMapper();

    private HttpUtils() {}

    public static void sendResponse(HttpServletResponse resp, String message, int statusCode) throws IOException {
        ResponseBody error = new ResponseBody(message);
        resp.setContentType("application/json");
        resp.setStatus(statusCode);
        PrintWriter out = resp.getWriter();
        out.println(jsonMapper.writeValueAsString(error));
    }
}
