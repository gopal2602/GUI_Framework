package testscripts;

public class Sample {
	public static void main(String[] args) {
		int n=4;
		 boolean flag=false;
		for(int i=2;i<n;i++)
		{
			if(i%n==0)
			{
				flag=true;
				break;
			}
		}
		
		if(flag==false)
		{
			System.out.println("Prime");
		}
		else
		{
			System.out.println("not prime");
		}
	}
}
