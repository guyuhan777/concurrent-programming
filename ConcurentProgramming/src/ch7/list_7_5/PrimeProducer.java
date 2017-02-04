package ch7.list_7_5;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

public class PrimeProducer extends Thread {
	private final BlockingQueue<BigInteger> queue;
	
	public PrimeProducer(BlockingQueue<BigInteger> queue){
		this.queue = queue;
	}
	
	public void run(){
		try{
			BigInteger p = BigInteger.ONE;
			while(!Thread.currentThread().isInterrupted()){
				queue.put(p=p.nextProbablePrime());
			}
		}catch(InterruptedException consumed){
			System.out.println("Stop");
		}
	}
	
	public void cancel(){
		interrupt();
	}
	
	public void print(){
		while(!queue.isEmpty()){
			try {
				System.out.println(queue.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

