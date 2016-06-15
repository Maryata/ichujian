/**
 * 
 */
package com.org.mokey.basedata.sysinfo.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

/**
 * @author vpc
 */
public class ProcessUtil {
	private static final int DEFAULT_BUFFER_SIZE = 1024;
	private static Logger log = Logger.getLogger( ProcessUtil.class );

	private InputStream in;

	private OutputStream out;
	private OutputStream err;

	public ProcessUtil( OutputStream out, OutputStream err, InputStream in ) {
		if ( out == null ) {
			out = new NullOutputStream();
		}
		if ( err == null ) {
			err = new NullOutputStream();
		}
		this.out = out;
		this.err = err;
		this.in = in;

	}

	public int process(String cmd) throws IOException,
			InterruptedException {
		Process p = Runtime.getRuntime().exec( cmd );
		return process( p );
	}

	private int process(Process p) throws IOException, InterruptedException {
		try {
			OutputStream pin = p.getOutputStream();

			StreamGobbler outg = new StreamGobbler( p.getInputStream(), out );
			StreamGobbler errg = new StreamGobbler( p.getErrorStream(), err );

			outg.start();
			errg.start();

			if ( in != null ) {
				byte[] inBuf = new byte[DEFAULT_BUFFER_SIZE];
				int inN = 0;
				while ( -1 != (inN = in.read( inBuf )) ) {
					pin.write( inBuf, 0, inN );
				}
				pin.flush();
			}

			int code = p.waitFor();
			outg.join();
			errg.join();
			
			return code;
		} finally {
			closeQuietly( p.getOutputStream() );
			closeQuietly( p.getInputStream() );
			closeQuietly( p.getErrorStream() );
		}

	}

	private void closeQuietly(Closeable closeable) {
		try {
			if ( closeable != null )
				closeable.close();
		} catch ( Exception e ) {
			log.warn( "close error", e );
		}
	}

}

class StreamGobbler implements Runnable {
	private static final int DEFAULT_BUFFER_SIZE = 1024;
	private static Logger log = Logger.getLogger( StreamGobbler.class );

	private InputStream is;
	private OutputStream os;
	private Thread thread;

	public StreamGobbler( InputStream is, OutputStream os ) {
		this.is = is;
		this.os = os;
	}

	public void start() {
		thread = new Thread( this );
		thread.setDaemon( true );
		thread.start();
	}

	public void run() {
		try {
			byte[] buf = new byte[DEFAULT_BUFFER_SIZE];
			int n = 0;
			while ( -1 != (n = is.read( buf )) ) {
				os.write( buf, 0, n );
			}
			os.flush();
		} catch ( Exception ex ) {
			log.warn( "stream error", ex );
		}
	}

	public void join() throws InterruptedException {
		thread.join();
	}

}

class NullOutputStream extends OutputStream {

	public static final NullOutputStream NULL_OUTPUT_STREAM = new NullOutputStream();

	@Override
	public void write(byte[] b, int off, int len) {
	}

	@Override
	public void write(int b) {
	}

	@Override
	public void write(byte[] b) throws IOException {
	}
}
