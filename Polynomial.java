public class Polynomial{
	double [] coefficent; 
	
	public Polynomial(){
		this.coefficent = new double[]{0};
		
	}
	
	public Polynomial(double [] new_coefficent) {
		this.coefficent = new_coefficent; 
		
	}
	
	public Polynomial add(Polynomial polynomial) {
		int maxL = Math.max(this.coefficent.length, polynomial.coefficent.length);
		double [] result = new double[maxL]; 
		
		for (int i = 0; i < maxL; i++) {
            if (i < this.coefficent.length) {
                result[i] += this.coefficent[i];
            }
            if (i < polynomial.coefficent.length) {
                result[i] += polynomial.coefficent[i];
            }
        }

        return new Polynomial(result);
	}
	
	public double evaluate(double x) {
        double result = 0;
       
        for (int i = 0; i < coefficent.length; i++) {
        	result += coefficent[i] * Math.pow(x, i);
        }
        
        return result;
    }
	
	public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }

}