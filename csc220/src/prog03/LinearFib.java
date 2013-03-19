package prog03;

public class LinearFib implements Fib {

	@Override
	public double fib(int n) {
		if (n < 2){
			return n;
		}
		double a = 0;
		double b = 1;
		double a2 = 0;
		for (int i = 0; i < n; i++)
		{
			a = b;
			b = a2+b;
			a2=a;
		}
		return a;
	}

	@Override
	public double o(int n) {
		// TODO Auto-generated method stub
		return n;
	}

}
