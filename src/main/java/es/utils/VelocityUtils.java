package es.utils;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.runtime.RuntimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import java.io.StringWriter;
import java.util.Map;

/**
 * velocity模板工具类
 * Created by cao on 14-10-14.
 */
public class VelocityUtils {
    private static final Logger logger = LoggerFactory.getLogger(VelocityUtils.class);
    private static final String FILE_NAME = "velocity.properties";
    private volatile static boolean inited = false;
    private static VelocityEngine engine;

    public static synchronized void init(ServletContext sc) {
        if (!inited) {
            Velocity.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.NullLogChute");
            engine = new VelocityEngine();
            engine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.NullLogChute");
            engine.setApplicationAttribute("javax.servlet.ServletContext", sc);
            try {
                engine.init(VelocityUtils.class.getClassLoader().getResource("").getPath() + FILE_NAME);
            } catch (Exception e) {
                logger.error("init velocity engine error", e);
            }
        }
    }

    public static Template getTemplate(String templateName) {
        Template template = null;
        try {
            template = engine.getTemplate(templateName);
        } catch (Exception e) {
            logger.error("get velocity template error");
        }
        return template;
    }

    public static String getResult(Template template, Context ctx) throws Exception {
        StringWriter writer = new StringWriter();
        String result;
        try {
            template.merge(ctx, writer);
            result = writer.toString();
        } catch (Exception e) {
            logger.error("generate html error");
            throw e;
        }
        return result;
    }

    public static String getResult(Template template, Map<String, Object> map) throws Exception {
        Context ctx = new VelocityContext();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            ctx.put(entry.getKey(), entry.getValue());
        }
        return getResult(template, ctx);
    }
}
