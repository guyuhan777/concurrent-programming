package ch7.list_7_13;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LogWriter {
	public static final int CAPACITY = 50;
	
	private final BlockingQueue<String> queue;
	
	private final LoggerThread logger;
	
	public LogWriter(PrintWriter writer){
		queue = new LinkedBlockingQueue<String>(CAPACITY);
		logger = new LoggerThread(writer);
	}
	
	public void start(){logger.start();}
	
	public void log(String msg) throws InterruptedException{
		queue.put(msg);
	}
	
	private class LoggerThread extends Thread{
		private final PrintWriter writer;
		
		public LoggerThread(PrintWriter writer){
			super();
			this.writer = writer;
		}
		
		@Override
		public void run() {
			try{
				while(true){
					writer.println(queue.take());
				}
			}catch(InterruptedException ingnored){
			}finally{
				writer.close();
			}
		}
	}
}
