package ch7.list_7_15;

import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class LogService {
	
	public static final int CAPCITY = 50;
	
	private final LoggerThread loggerThread;
	
	private final BlockingQueue<String> queue;
	
	private final PrintWriter writer;
			
	private boolean isShutDown;
	
	private int reservations;
	
	public LogService(PrintWriter writer){
		queue = new LinkedBlockingDeque<String>(CAPCITY);
		this.writer = writer;
		loggerThread = new LoggerThread();
	}
	
	public void start(){
		loggerThread.start();
	}
	
	public void stop(){
		synchronized (this) {
			isShutDown = true;
		}
		loggerThread.interrupt();
	}
	
	public void log(String msg) throws InterruptedException{
		synchronized (this) {
			if(isShutDown){
				throw new IllegalStateException("Service has been shut down");
			}
			reservations++;
		}
		queue.put(msg);
	}
	
	private class LoggerThread extends Thread{
		@Override
		public void run() {
			try{
				while(true){
					try{
						synchronized (LogService.this) {
							if(isShutDown&&reservations == 0){
								break;
							}
						}
						String msg = queue.take();
						synchronized(LogService.this){
							--reservations;
						}
						writer.println(msg);
					}catch(InterruptedException e){}
				}
			}finally{
				writer.close();
			}	
		}
	}
	
	}

