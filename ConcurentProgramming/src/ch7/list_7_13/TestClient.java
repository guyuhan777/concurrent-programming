package ch7.list_7_13;

public class TestClient {

	public static void main(String[] args) {
		
		int i = 0;
		
		while(true){
			try{
				i++;
				System.out.println(i);
				Thread.sleep(1000);
				if(i==2){
					throw new Exception(" Get Exception");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}

}
