package com.org.mokey.common.util;

import java.text.MessageFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 */
public class LogUtils{

    /**
     */
    private static final int INTER_STACKTRACE_DEEP = 2;
    private static final int BEGIN_STATE = 0;
    private static final int SUCCESS_STATE = 1;
    private static final int ERROR_STATE = -1;

    /**
     * 
     * @param message
     * @param params
     */
    public static void debug(String message, Object... params) {
        Log logger = getLogger();
        if (logger.isDebugEnabled()) {
            if (params.length > 0) {
                message = MessageFormat.format(message, params);
            }
            logger.debug(message);
        }
    }

    /**
     * 
     * @param name
     * @param message
     * @param params
     */
    public static void debugByName(String name, String message, Object... params) {
        Log logger = getLogger(name);
        if (logger.isDebugEnabled()) {
            if (params.length > 0) {
                message = MessageFormat.format(message, params);
            }
            logger.debug(message);
        }
    }

    /**
     * 
     * @param message
     * @param params
     */
    public static void debug(String message, Throwable t, Object... params) {
        Log logger = getLogger();
        if (logger.isDebugEnabled()) {
            if (params.length > 0) {
                message = MessageFormat.format(message, params);
            }
            logger.debug(message, t);
        }
    }

    /**
     * 
     * @param message
     * @param params
     */
    public static void debugByName(String name, String message, Throwable t, Object... params) {
        Log logger = getLogger(name);
        if (logger.isDebugEnabled()) {
            if (params.length > 0) {
                message = MessageFormat.format(message, params);
            }
            logger.debug(message, t);
        }
    }

    /**
     * @param message
     */
    public static void debug(Object message) {
        Log logger = getLogger();
        if (logger.isDebugEnabled()) {
            logger.debug(message);
        }
    }

    /**
     * @param message
     * @param t
     */
    public static void debug(Object message, Throwable t) {
        Log logger = getLogger();
        if (logger.isDebugEnabled()) {
            logger.debug(message, t);
        }
    }

    /**
     * 
     * @param message
     * @param params
     */
    public static void info(String message, Object... params) {
        Log logger = getLogger();
        if (logger.isInfoEnabled()) {
            if (params.length > 0) {
                message = MessageFormat.format(message, params);
            }
            logger.info(message);
        }
    }

    /**
     * 
     * @param message
     * @param params
     */
    public static void infoByName(String name, String message, Object... params) {
        Log logger = getLogger(name);
        if (logger.isInfoEnabled()) {
            if (params.length > 0) {
                message = MessageFormat.format(message, params);
            }
            logger.info(message);
        }
    }

    /**
     * 
     * @param message
     * @param t
     * @param params
     */
    public static void info(String message, Throwable t, Object... params) {
        Log logger = getLogger();
        if (logger.isInfoEnabled()) {
            if (params.length > 0) {
                message = MessageFormat.format(message, params);
            }
            logger.info(message, t);
        }
    }

    /**
     * 
     * @param message
     * @param t
     * @param params
     */
    public static void infoByName(String name, String message, Throwable t, Object... params) {
        Log logger = getLogger(name);
        if (logger.isInfoEnabled()) {
            if (params.length > 0) {
                message = MessageFormat.format(message, params);
            }
            logger.info(message, t);
        }
    }

    /**
     * @param message
     */
    public static void info(Object message) {
        Log logger = getLogger();
        if (logger.isInfoEnabled()) {
            logger.info(message);
        }
    }

    /**
     * @param message
     * @param t
     */
    public static void info(Object message, Throwable t) {
        Log logger = getLogger();
        if (logger.isInfoEnabled()) {
            logger.info(message, t);
        }
    }

    /**
     * 
     * @param message
     * @param params
     */
    public static void warn(String message, Object... params) {
        Log logger = getLogger();
        if (logger.isWarnEnabled()) {
            if (params.length > 0) {
                message = MessageFormat.format(message, params);
            }
            logger.warn(message);
        }
    }

    /**
     * 
     * @param message
     * @param params
     */
    public static void warnByName(String name, String message, Object... params) {
        Log logger = getLogger(name);
        if (logger.isWarnEnabled()) {
            if (params.length > 0) {
                message = MessageFormat.format(message, params);
            }
            logger.warn(message);
        }
    }

    /**
     * 
     * @param message
     * @param t
     * @param params
     */
    public static void warn(String message, Throwable t, Object... params) {
        Log logger = getLogger();
        if (logger.isWarnEnabled()) {
            if (params.length > 0) {
                message = MessageFormat.format(message, params);
            }
            logger.warn(message, t);
        }
    }

    /**
     * 
     * @param message
     * @param t
     * @param params
     */
    public static void warnByName(String name, String message, Throwable t, Object... params) {
        Log logger = getLogger(name);
        if (logger.isWarnEnabled()) {
            if (params.length > 0) {
                message = MessageFormat.format(message, params);
            }
            logger.warn(message, t);
        }
    }

    /**
     * @param message
     */
    public static void warn(Object message) {
        Log logger = getLogger();
        if (logger.isWarnEnabled()) {
            logger.warn(message);
        }
    }

    /**
     * @param message
     * @param t
     */
    public static void warn(Object message, Throwable t) {
        Log logger = getLogger();
        if (logger.isWarnEnabled()) {
            logger.warn(message, t);
        }
    }

    /**
     * 
     * @param message
     * @param params
     */
    public static void error(String message, Object... params) {
        Log logger = getLogger();
        if (logger.isErrorEnabled()) {
            if (params.length > 0) {
                message = MessageFormat.format(message, params);
            }
            logger.error(message);
        }
    }

    /**
     * 
     * @param message
     */
    public static void errorByName(String name, String message, Object... params) {
        Log logger = getLogger(name);
        if (logger.isErrorEnabled()) {
            if (params.length > 0) {
                message = MessageFormat.format(message, params);
            }
            logger.error(message);
        }
    }

    /**
     * 
     * @param message
     * @param t
     * @param params
     */
    public static void error(String message, Throwable t, Object... params) {
        Log logger = getLogger();
        if (logger.isErrorEnabled()) {
            if (params.length > 0) {
                message = MessageFormat.format(message, params);
            }
            logger.error(message, t);
        }
    }

    /**
     * 
     * @param message
     * @param t
     * @param params
     */
    public static void errorByName(String name, String message, Throwable t, Object... params) {
        Log logger = getLogger(name);
        if (logger.isErrorEnabled()) {
            if (params.length > 0) {
                message = MessageFormat.format(message, params);
            }
            logger.error(message, t);
        }
    }

    /**
     * @param message
     */
    public static void error(Object message) {
        Log logger = getLogger();
        if (logger.isErrorEnabled()) {
            logger.error(message);
        }
    }

    /**
     * @param message
     * @param t
     */
    public static void error(Object message, Throwable t) {
        Log logger = getLogger();
        if (logger.isErrorEnabled()) {
            logger.error(message, t);
        }
    }

    /**
     * @return
     */
    public static int getBeginstate() {
        return BEGIN_STATE;
    }

    /**
     * @return
     */
    public static int getSuccessstate() {
        return SUCCESS_STATE;
    }

    /**
     * @return
     */
    public static int getErrorstate() {
        return ERROR_STATE;
    }

    /**
     */
    private static Log getLogger() {
        /**
         */
        final Throwable t = new Throwable();
        final StackTraceElement[] methodCaller = t.getStackTrace();
        return LogFactory.getLog(methodCaller[INTER_STACKTRACE_DEEP].getClassName());
    }

    /**
     * 
     * @param name
     * @return
     */
    private static Log getLogger(String name) {
        return LogFactory.getLog(name);
    }

}
