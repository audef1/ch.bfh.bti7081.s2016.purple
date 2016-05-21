package ch.bfh.bti7081.s2016.purple.HealthVisitor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.persistence.logging.AbstractSessionLog;
import org.eclipse.persistence.logging.SessionLog;
import org.eclipse.persistence.logging.SessionLogEntry;

/**
 * Created by tgdflto1 on 21/05/16.
 */
public class CustomLogger extends AbstractSessionLog implements SessionLog {
    private static final Logger LOGGER = LogManager.getLogger(CustomLogger.class);

    @Override
    public void log(SessionLogEntry sessionLogEntry) {
        int level = sessionLogEntry.getLevel();
        String message = sessionLogEntry.getMessage();
        if (sessionLogEntry.getParameters() != null) {
            message += " [";
            int index = 0;
            for (Object object : sessionLogEntry.getParameters()) {
                message += (index++ > 0 ? "," : "") + object;
            }
            message += "]";
        }
        switch (level) {
            case SessionLog.SEVERE:
                LOGGER.error(message);
                break;
            case SessionLog.WARNING:
                LOGGER.warn(message);
                break;
            case SessionLog.INFO:
                LOGGER.info(message);
                break;
            case SessionLog.CONFIG:
                LOGGER.trace(message);
                break;
            default:
                LOGGER.debug(message);
                break;
        }
    }
}
