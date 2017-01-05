package cache;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class Memoizer<A,V> implements Computable<A,V>{ 
	
	private final Computable<A,V> c;
	
	private final ConcurrentHashMap<A, Future<V>> cache = new ConcurrentHashMap<A,Future<V>>(); 
	
	public Memoizer(Computable<A,V> c){
		this.c = c;
	}
	
	public V compute(final A arg) throws InterruptedException {
		while(true){
			Future<V> f = cache.get(arg);
			if(f == null){
				Callable<V> eval = new Callable<V>(){

					@Override
					public V call() throws Exception {
						return c.compute(arg);
					}};
					
				FutureTask<V> ft = new FutureTask<V>(eval);
				f = cache.putIfAbsent(arg, ft);
				if(f == null){
					f = ft;
					ft.run();
				}
				try {
					return f.get();
				}catch(CancellationException ce){
					cache.remove(arg);
				}catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
