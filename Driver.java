import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Driver {

    public static void main(String[] args) throws IOException {
        boolean allTestsPassed = true;

        // Test default constructor
        Polynomial p = new Polynomial();
        if (p.evaluate(0) != 0) {
            allTestsPassed = false;
            System.out.println("Test case failed: Default constructor test");
        }

        // Test constructor with coefficients array
        double[] coefficients = {6, 0, 0, 5};
        Polynomial p1 = new Polynomial(coefficients);
        if (Math.abs(p1.evaluate(3) - (6 + 5 * Math.pow(3, 3))) >= 1e-9) {
            allTestsPassed = false;
            System.out.println("Test case failed: Constructor with coefficients test");
        }

        // Test case: 5-3x2+x+7x8 (File constructor)
        FileWriter writer1 = new FileWriter("test1.txt");
        writer1.write("5-3x2+x+7x8");
        writer1.close();
        File file1 = new File("test1.txt");
        Polynomial poly1 = new Polynomial(file1);
        if (Math.abs(poly1.evaluate(1) - (5 - 3 * Math.pow(1, 2) + 1 * Math.pow(1, 1) + 7 * Math.pow(1, 8))) >= 1e-9) {
            allTestsPassed = false;
            System.out.println("Test case failed: 5-3x2+x+7x8 test");
        }

        // Test case: Add two polynomials: (6 + 5x^3) + (3 + 2x^2 + x^1)
        double[] coefficients2 = {3, 1, 2};  // Corresponds to 3 + x + 2x^2
        Polynomial p2 = new Polynomial(coefficients2);
        Polynomial sum = p1.add(p2);  // Add p1 and p2

        if (Math.abs(sum.evaluate(1) - (6 + 5 * Math.pow(1, 3) + 3 + 1 * Math.pow(1, 1) + 2 * Math.pow(1, 2))) >= 1e-9) {
            allTestsPassed = false;
            System.out.println("Test case failed: Add polynomials test");
        }

        // Test case: Multiply two polynomials: (6 + 5x^3) * (3 + 2x^2 + x^1)
        Polynomial product = p1.multiply(p2);  // Multiply p1 and p2
        if (Math.abs(product.evaluate(1) - ((6 + 5 * Math.pow(1, 3)) * (3 + 1 * Math.pow(1, 1) + 2 * Math.pow(1, 2)))) >= 1e-9) {
            allTestsPassed = false;
            System.out.println("Test case failed: Multiply polynomials test");
        }

        // Test case: -x2 + 4x - 7 (File constructor)
        FileWriter writer2 = new FileWriter("test2.txt");
        writer2.write("-x2+4x-7");
        writer2.close();
        File file2 = new File("test2.txt");
        Polynomial poly2 = new Polynomial(file2);

        if (Math.abs(poly2.evaluate(2) - (-Math.pow(2, 2) + 4 * 2 - 7)) >= 1e-9) {
            System.out.println(poly2);
            allTestsPassed = false;
            System.out.println("Test case failed: -x2+4x-7 test");
        }

        // Test case: hasRoot method, check if 1 is a root of x^3 - x^2 + x - 1 (File constructor)
        FileWriter writer3 = new FileWriter("test3.txt");
        writer3.write("x3-x2+x-1");
        writer3.close();
        File file3 = new File("test3.txt");
        Polynomial poly3 = new Polynomial(file3);
        if (!poly3.hasRoot(1)) {
            allTestsPassed = false;
            System.out.println("Test case failed: hasRoot method test (should find 1 as root)");
        }

        // Test case: x^3 - x^2 + x - 1 (File constructor) for root failure
        if (poly3.hasRoot(2)) {
            allTestsPassed = false;
            System.out.println("Test case failed: hasRoot method test (should not find 2 as root)");
        }

        // Test case: Saving a polynomial to a file
        String filename = "saved_polynomial.txt";
        poly1.saveToFile(filename);

        // Reading the saved file and checking contents
        File savedFile = new File(filename);
        Polynomial savedPoly = new Polynomial(savedFile);

        if (Math.abs(savedPoly.evaluate(1) - poly1.evaluate(1)) >= 1e-9) {
            allTestsPassed = false;
            System.out.println("savedPoly: " + savedPoly);
            System.out.println("sentPoly: " + poly1);
            System.out.println("Test case failed: Save and read polynomial test");
        }

        if (allTestsPassed) {
            System.out.println("All test cases passed!");
        } else {
            System.out.println("Some test cases failed.");
        }
        FileWriter writerr = new FileWriter("ppoly1.txt");
        writerr.write("0.4x2+0.02x+0.1");
        writerr.close();
        FileWriter writerr2 = new FileWriter("ppoly2.txt");
        writerr2.write("-0.1");
        writerr2.close();
        Polynomial ppoly1 = new Polynomial(new File("ppoly1.txt"));
        Polynomial ppoly2 = new Polynomial(new File("ppoly2.txt"));
        System.out.println(ppoly2);
        Polynomial resultA = (ppoly1.multiply(ppoly2));
        System.out.println(resultA);
        resultA.saveToFile("resultA.txt");
        Polynomial resultB = ppoly2.add(ppoly1);
        System.out.println(resultB);
        resultB.saveToFile("resultB.txt");
    }
}
