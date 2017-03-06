package ch7.list_7_21;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class TrackingExecutor extends AbstractExecutorService {
	
	private final ExecutorService exec;
	
	private final Set<Runnable> tasksCancelledAtShutdown = Collections.synchronizedSet(new HashSet<Runnable>());
	
	public TrackingExecutor(ExecutorService exec){
		super();
		this.exec = exec;
	}
	
	public List<Runnable> getCancelledTasks(){
		if(!isTerminated()){
			throw new IllegalStateException("Exectuor hasn't been shut down");
		}
		return new ArrayList<Runnable>(tasksCancelledAtShutdown);
	}
	
	@Override
	public boolean awaitTermination(long arg0, TimeUnit arg1) throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isShutdown() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTerminated() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Runnable> shutdownNow() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override 
	public void execute(final Runnable r) {
		exec.execute(new Runnable(){

			@Override
			public void run() {
				try{
					r.run();
				}finally {
					if(isShutdown()&&Thread.currentThread().isInterrupted()){
						tasksCancelledAtShutdown.add(r);
					}
				}
			}
			
		});
	}

}
