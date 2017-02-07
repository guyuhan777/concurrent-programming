package ch7.list_7_1;

import java.math.BigInteger;
import java.util.List;

public class TestClient {
	
	public static List<BigInteger> aSecondOfPrimes() throws InterruptedException {
		PrimeGenerator generator = new PrimeGenerator();
		new Thread(generator).start();
		System.out.println("--------------start---------------");
		try {
			Thread.sleep(1000);
		}finally{
			generator.cancel();
		}
		
		return generator.get();
	}
	
	public static void main(String[] args) {
		try {
			List<BigInteger> primes = aSecondOfPrimes();
			
			for(int i=0;i<primes.size();i++){
				System.out.println(primes.get(i));
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
