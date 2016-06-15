package net.jeeshop.core.p6spy;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.FormattedLogger;
import com.p6spy.engine.spy.appender.P6Logger;

public class LogbackLogger extends FormattedLogger implements P6Logger {
    protected String lastEntry;
    private static Logger log;

    public LogbackLogger() {
        //P6SpyProperties properties = new P6SpyProperties();
        log = LoggerFactory.getLogger("p6spy");
//        log.setAdditivity(false);
    }

    public void logException(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        this.logText(sw.toString());
    }

    public void logText(String text) {
        log.info(text);
       // this.setLastEntry(text);
    }

	@Override
	public boolean isCategoryEnabled(Category category) {
		// TODO Auto-generated method stub
		return false;
	}
}
