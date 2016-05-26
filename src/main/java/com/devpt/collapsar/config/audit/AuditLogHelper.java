package com.devpt.collapsar.config.audit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by chenyong on 2016/5/24.
 * <p>
 * log helper is to log the core log to dedicated destination
 * <p>
 * The log output format is like:
 * timestamp, hostname, module ("updown", "xxx"), unique-session-id, metric, logs (a json string)
 * <p>
 * metric format suggestion: WITH01, BCV01, BCK02
 */
public class AuditLogHelper {
    private static final Logger logger = LoggerFactory.getLogger(AuditLogHelper.class);

    private static AtomicLong SEQ = new AtomicLong(0);
    private static String HOST_NAME = "unknown";
    private static String APP_ID = "collapsar";
    private static String SESSION_GID_PREFIX = String.format("%s-%x-", APP_ID, System.currentTimeMillis() - 45L * 365 * 24 * 3600 * 1000);
    private String moduleName;
    private String sessionGid = null;


    static {
        try {
            HOST_NAME = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private AuditLogHelper(String moduleName) {
        this.moduleName = moduleName;
        this.sessionGid = SESSION_GID_PREFIX + SEQ.getAndIncrement();
    }


    public static AuditLogHelper getLogger(String moduleName) {
        return new AuditLogHelper(moduleName);
    }

    /**
     * log the information with the specified metric
     *
     * @param metric the log's metric id. Its format is like 'WITH01', 'BCV01', etc.
     * @param logs   the log's contents. It must be a json string.
     */
    public void log(String metric, String logs) {
        if (metric == null || logs == null) {
            return;
        }

        StringBuffer sb = new StringBuffer("{");
        sb.append(String.format("\"m_name\": \"%s\", ", metric));
        sb.append(String.format("\"m_host\": \"%s\", ", HOST_NAME));
        sb.append(String.format("\"m_time\": %.3f, ", System.currentTimeMillis() / 1000.0));
        sb.append(String.format("\"m_appid\": \"%s\", ", APP_ID));
        sb.append(String.format("\"m_module\": \"%s\", ", moduleName));
        sb.append(String.format("\"m_id\": \"%s\", ", sessionGid != null ? sessionGid : SEQ.getAndIncrement()));
        if (logs.startsWith("{")) {
            // "{}"
            if (logs.substring(1).trim().equals("}")) {
                sb.append("\"value\": \"{}\"}");
            } else {
                sb.append(logs.substring(1));
            }
        } else if (logs.startsWith("[")) {
            sb.append("\"value\": ");
            sb.append(logs).append("}");
        } else {
            sb.append(String.format("\"value\": \"%s\"}", logs.replace("\"", "'")));
        }

        logger.info("{}", sb.toString());
    }

}
