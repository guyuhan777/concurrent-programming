package ch8.list_8_4;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;

public class BoundedExecutor {
	
	private final Executor exec;
	private final Semaphore semaphore;
	
	public BoundedExecutor(Executor exec,int size){
		this.exec = exec;
		semaphore = new Semaphore(size);
	}
	
	public void submitTask(final Runnable command) throws InterruptedException{
		semaphore.acquire();
		try{
			exec.execute(new Runnable() {
							@Override
							public void run() {
								try{
									command.run();
								}finally {
									semaphore.release();
								}
							}
			});
		}catch (RejectedExecutionException e) {
			semaphore.release();
		}
	}
}
