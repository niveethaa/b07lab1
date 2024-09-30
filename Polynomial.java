import java.io.*;
import java.io.File; 
import java.util.Arrays;
import java.util.Scanner;

public class Polynomial{
	double [] coefficients;
	int [] exponents; 
	
	
	public Polynomial(){
		this.coefficients = new double[]{0};
		this.exponents = new int[]{0}; 
		
	}
	
	public Polynomial(double [] new_coefficients, int [] new_exponents) {
		this.coefficients = new_coefficients.clone(); 
		this.exponents = new_exponents.clone(); 
		
	}
	
	
	public Polynomial add(Polynomial polynomial) {
		int maxL = this.coefficients.length +  polynomial.coefficients.length;
        double[] resultCoefficients = new double[maxL];
        int[] resultExponents = new int[maxL];
        int index = 0;
        
        for (int i = 0; i < this.coefficients.length; i++) {
            resultCoefficients[index] = this.coefficients[i];
            resultExponents[index] = this.exponents[i];
            index++;
        }

        for (int i = 0; i < polynomial.coefficients.length; i++) {
            int foundIndex = findExponent(resultExponents, polynomial.exponents[i], index);
            if (foundIndex != -1) {
                resultCoefficients[foundIndex] += polynomial.coefficients[i];
            } else {
                resultCoefficients[index] = polynomial.coefficients[i];
                resultExponents[index] = polynomial.exponents[i];
                index++;
            }
        }

        return new Polynomial(Arrays.copyOf(resultCoefficients, index), Arrays.copyOf(resultExponents, index));
	
	}
	
	public double evaluate(double x) {
        double result = 0;
       
        for (int i = 0; i < coefficients.length; i++) {
        	result += coefficients[i] * Math.pow(x, exponents[i]);
        }
        
        return result;
    }
	
	public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }
	
	private int findExponent(int[] exponents, int exponent, int length) {
        for (int i = 0; i < length; i++) {
            if (exponents[i] == exponent) {
                return i;
            }
        }
        
        return -1;
    }
	
	public Polynomial multiply(Polynomial polynomial) {
        int resultLength = this.coefficients.length * polynomial.coefficients.length;
        double[] resultCoefficients = new double[resultLength];
        int[] resultExponents = new int[resultLength];

        int index = 0;
        for (int i = 0; i < this.coefficients.length; i++) {
            for (int j = 0; j < polynomial.coefficients.length; j++) {
                resultCoefficients[index] = this.coefficients[i] * polynomial.coefficients[j];
                resultExponents[index] = this.exponents[i] + polynomial.exponents[j];
                index++;
            }
        }

        return combineLikeTerms(resultCoefficients, resultExponents, index);
    }

    // Helper method 
	
    private Polynomial combineLikeTerms(double[] coefficients, int[] exponents, int length) {
        double[] newCoefficients = new double[length];
        int[] newExponents = new int[length];
        int index = 0;

        for (int i = 0; i < length; i++) {
            int foundIndex = findExponent(newExponents, exponents[i], index);
            if (foundIndex != -1) {
                newCoefficients[foundIndex] += coefficients[i];
            } else {
                newCoefficients[index] = coefficients[i];
                newExponents[index] = exponents[i];
                index++;
            }
        }

        return new Polynomial(Arrays.copyOf(newCoefficients, index), Arrays.copyOf(newExponents, index));
    }

 
    public Polynomial(File file) throws IOException {
        this();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            parseFromString(line);
        }
    }

    private void parseFromString(String polyString) {
    	polyString = polyString.replaceAll("-", "+-");
        String[] terms = polyString.split("\\+");

        for (String term : terms) {
            String[] parts = term.split("x");

            if (parts.length == 2) {
                double coefficient = Double.parseDouble(parts[0]);
                int exponent = Integer.parseInt(parts[1]);
                if (coefficient != 0.0) {
                    addTerm(coefficient, exponent);
                }
            } else if (parts.length == 1) {
                double coefficient = Double.parseDouble(parts[0]);
                if (coefficient != 0.0) {
                    addTerm(coefficient, 0);
                }
            }
        }
    }
    
    //Helper
    private void addTerm(double coefficient, int exponent) {
        int newSize = coefficients.length + 1;
        coefficients = Arrays.copyOf(coefficients, newSize);
        exponents = Arrays.copyOf(exponents, newSize);
        coefficients[newSize - 1] = coefficient;
        exponents[newSize - 1] = exponent;
    }

    
    public void saveToFile(String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(this.toString());
        }
    }

    public String toString() {
        StringBuilder polynomialString = new StringBuilder();
        
        for (int i = 0; i < coefficients.length; i++) {
            double coefficient = coefficients[i];
            int exponent = exponents[i];
            
            if (i != 0 && coefficient >= 0) {
                polynomialString.append("+");
            }
            
            if (exponent == 0) {
                polynomialString.append(coefficient);
            } else {
                polynomialString.append(coefficient).append("x").append(exponent);
            }
        }
        
        return polynomialString.toString();
    }

}
