package ch7.list_7_5;

import java.math.BigInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class TestClient {
	
	
	public static void consume() throws InterruptedException{
		BlockingQueue<BigInteger> primes = new ArrayBlockingQueue<BigInteger>(20);
		PrimeProducer producer = new PrimeProducer(primes);
		producer.start();
		
		try{
			Thread.sleep(1000);
		}finally{
			producer.cancel();
			producer.print();
		}
	}
	
	public static void main(String[] args) {
		try {
			consume();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
