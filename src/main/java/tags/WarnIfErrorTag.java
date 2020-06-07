package tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class WarnIfErrorTag extends TagSupport {

    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int doStartTag() throws JspException {
        if (message != null && message.length() != 0) {
            try {
                JspWriter out = pageContext.getOut();
                out.print("<div class=\"alert alert-danger alert-dismissible fade show\" role=\"alert\">\n" +
                        "                          " + message + "\n" +
                        "                        <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">\n" +
                        "                            <span aria-hidden=\"true\">&times;</span>\n" +
                        "                        </button>\n" +
                        "                    </div>");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                return SKIP_BODY;
            }
        }
        return SKIP_BODY;
    }
}
