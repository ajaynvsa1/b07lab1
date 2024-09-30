import java.util.*;
import java.io.*;
public class Polynomial{
    double[] non_zero_coefficients;
    int[] exponents;
    HashMap<Integer,Double> expToCE;
    public Polynomial(){
        non_zero_coefficients = new double[0];
        exponents = new int[0];
        expToCE = new HashMap<Integer,Double>();
    }
    public Polynomial(double[] coefficients){
        int non_zero_count = 0;
        for(int i = 0; i < coefficients.length; i++){
            if(coefficients[i] != 0){
                non_zero_count++;
            }
        }
        non_zero_coefficients = new double[non_zero_count];
        exponents = new int[non_zero_count];
        expToCE = new HashMap<Integer,Double>();
        int index  = 0;
        for(int i = 0; i < coefficients.length; i++){
            if(coefficients[i] == 0){
                continue;
            }
            non_zero_coefficients[index] = coefficients[i];
            exponents[index] = i;
            expToCE.put(i,coefficients[i]);
            index++;
        }

    }
    public Polynomial(double[] non_zero_coefficientss, int[] exponentss){
        expToCE = new HashMap<Integer,Double>();
        this.non_zero_coefficients = new double[non_zero_coefficientss.length];
        this.exponents = new int[non_zero_coefficientss.length];
        for(int i = 0; i < non_zero_coefficientss.length; i++){
            this.non_zero_coefficients[i] = non_zero_coefficientss[i];
            this.exponents[i] = exponentss[i];
        }

        //Sanitation
        for(int i = 0; i < exponents.length; i++){
            if(expToCE.containsKey(exponents[i])){
                expToCE.put(exponents[i],expToCE.get(exponents[i]) + non_zero_coefficients[i]);
            }
            else{
                expToCE.put(exponents[i],non_zero_coefficients[i]);
            }

        }
        int theSize = expToCE.keySet().size();
        for(Integer k : expToCE.keySet()){
            if(expToCE.get(k) == 0){
                theSize--;
            }
        }
        exponents = new int[theSize];
        non_zero_coefficients = new double[theSize];
        int j = 0;
        for(Integer k : expToCE.keySet()){
            if(expToCE.get(k) == 0){
                continue;
            }
            exponents[j] = k;
            non_zero_coefficients[j] = expToCE.get(k);
            j++;
        }
    }
    public Polynomial add(Polynomial that){
        int len1 = this.non_zero_coefficients.length;
        int len2 = that.non_zero_coefficients.length;
        int min_len = len1 < len2 ? len1: len2;
        HashSet<Integer> exponents = new HashSet<Integer>();
        for(int i = 0; i < len1; i++){
            exponents.add(this.exponents[i]);
        }
        for(int i = 0; i < len2; i++){
            exponents.add(that.exponents[i]);
        }
        int[] eponents = new int[exponents.size()];
        double[] goefficients = new double[exponents.size()];
        int j = 0;
        for(Integer i : exponents){
            eponents[j] = i;
            double coHere = (this.expToCE.containsKey(i) ? this.expToCE.get(i) : 0);
            double coThere = (that.expToCE.containsKey(i) ? that.expToCE.get(i) : 0);
            goefficients[j] = coHere + coThere;
            j++;
        }
        return new Polynomial(goefficients, eponents);
    }
    public double evaluate(double x){
        double sum = 0;
        for(int i = 0; i < non_zero_coefficients.length; i++){
            double product = 1;
            for(int j = 0; j < exponents[i]; j++){
                product *= x;
            }
            sum += non_zero_coefficients[i]*product;
        }
        return sum;
    }
    public boolean hasRoot(double x){
        return evaluate(x) == 0.0;
    }
    public Polynomial multiply (Polynomial that){
        Polynomial result = new Polynomial();
        for(int i = 0; i < this.exponents.length; i++){
            double[] ccoefficients = new double[that.exponents.length];
            int[] eexponents = new int[that.exponents.length];
            for(int j = 0; j < that.exponents.length; j++){
                ccoefficients[j] = this.non_zero_coefficients[i]*that.non_zero_coefficients[j];
                eexponents[j] = this.exponents[i]+that.exponents[j];
            }
            result = result.add(new Polynomial(ccoefficients,eexponents));
        }
        return result;

    }
    public Polynomial(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String polynomialString = br.readLine();
        br.close();
        polynomialString = polynomialString.replace("-","+-");
        polynomialString = polynomialString.replace("x+","x1+");
        polynomialString = polynomialString.replace("+x","+1x");
        polynomialString = polynomialString.replace("-x","-1x");
        if(polynomialString.charAt(0) == 'x'){
            polynomialString = "1"+polynomialString;
        }
        if(polynomialString.charAt(polynomialString.length()-1) == 'x'){
            polynomialString += "1";
        }
        String[] terms = polynomialString.split("\\+");
        exponents = new int[terms.length];
        non_zero_coefficients = new double[terms.length];
        expToCE = new HashMap<>();
        for(int i = 0; i < terms.length; i++){
            if(!terms[i].contains("x")){
                if (terms[i].isEmpty()) {
                    continue;
                }
                non_zero_coefficients[i] = Double.parseDouble(terms[i]);
                exponents[i] = 0;
            }
            else{
                String[] numbers = terms[i].split("x");
                if (numbers[0].isEmpty()) {
                    non_zero_coefficients[i] = 1.0;
                }
                else if (numbers[0].equals("-")) {
                    non_zero_coefficients[i] = -1.0;
                }
                else {
                    non_zero_coefficients[i] = Double.parseDouble(numbers[0]);
                }
                if (numbers.length > 1 && !numbers[1].isEmpty()) {
                    exponents[i] = Integer.parseInt(numbers[1]);
                }
                else {
                    exponents[i] = 1;
                }
            }
        }

        //Sanitation
        for(int i = 0; i < exponents.length; i++){
            if(expToCE.containsKey(exponents[i])){
                expToCE.put(exponents[i],expToCE.get(exponents[i]) + non_zero_coefficients[i]);
            }
            else{
                expToCE.put(exponents[i],non_zero_coefficients[i]);
            }

        }
        int theSize = expToCE.keySet().size();
        for(Integer k : expToCE.keySet()){
            if(expToCE.get(k) == 0){
                theSize--;
            }
        }
        exponents = new int[theSize];
        non_zero_coefficients = new double[theSize];
        int j = 0;
        for(Integer k : expToCE.keySet()){
            if(expToCE.get(k) == 0){
                continue;
            }
            exponents[j] = k;
            non_zero_coefficients[j] = expToCE.get(k);
            j++;
        }
    }
    public void saveToFile(String filename) throws IOException {
        String[] terms = new String[exponents.length];
        for(int i = 0; i < exponents.length; i++){
            terms[i] = non_zero_coefficients[i] + "x" + (exponents[i] == 1 ? "" : exponents[i]);
        }
        String res = String.join("+",terms);
        res = res.replace("+-","-");
        res = res.replace("+1x","+x");
        res = res.replace("-1x","-x");
        res = res.replace("x0","");
        if(exponents.length == 0){
            res = "0";
        }
        FileWriter writer = new FileWriter(filename);
        writer.write(res);
        writer.close();

    }
    public String toString(){
        return "Exponents: " + Arrays.toString(exponents) + "\nCoefficients: " + Arrays.toString(non_zero_coefficients);
    }
}