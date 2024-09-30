import java.io.*;
import java.io.File; 
import java.io.IOException;

public class Driver {
	public static void main(String [] args) {
		Polynomial p = new Polynomial();
		System.out.println(p.evaluate(3));
		double [] c1 = {6,0};
		int [] e1 = {0,1}; 
		Polynomial p1 = new Polynomial(c1, e1);
		double [] c2 = {0,-2,0,1};
		int [] e2 = {0,1,2,3};
		Polynomial p2 = new Polynomial(c2, e2);
		System.out.println("p1 = " + p1.toString());
		System.out.println("p2 = " + p2.toString());
		
		//Lab 1 
		Polynomial s = p1.add(p2);
		System.out.println("s(0.1) = " + s.evaluate(0.1));
		
		if(s.hasRoot(1))
			System.out.println("1 is a root of s");
		else
			System.out.println("1 is not a root of s");
		
		//Multiply Test
		Polynomial k = p1.multiply(p2);
		System.out.println(k.toString());
		
		//Test d and e
		String fileName = "polynomial.txt";
        try {
            p1.saveToFile(fileName);
            System.out.println("Polynomial 1 saved to file: " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving polynomial to file.");
        }

        
        try {
            File file = new File(fileName);
            Polynomial polyFromFile = new Polynomial(file);
            System.out.println("Polynomial loaded from file: " + polyFromFile);  // Output should match poly1
        } catch (IOException e) {
            System.out.println("Error loading polynomial from file.");
        }
		
		
	}
}
