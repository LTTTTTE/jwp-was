package utils;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import java.io.IOException;

public class TemplateBuilder {

    private static final TemplateLoader HTML_TEMPLATE_LOADER = new ClassPathTemplateLoader("/templates", ".html");
    private static final Handlebars HANDLEBARS = new Handlebars(HTML_TEMPLATE_LOADER);

    public static String build(String location, Object context) throws IOException {
        Template template = HANDLEBARS.compile(location);

        return template.apply(context);
    }
}
