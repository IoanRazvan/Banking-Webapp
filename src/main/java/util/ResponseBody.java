package util;

import lombok.Data;

@Data
public class ResponseBody {
    String message;

    public ResponseBody(String message) {
        this.message = message;
    }
}
