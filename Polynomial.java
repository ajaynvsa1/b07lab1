public class Polynomial{
    double[] coefficients;
    public Polynomial(){
        coefficients = new double[1];
        coefficients[0] = 0.0;
    }
    public Polynomial(double[] coefficients){
        this.coefficients = new double[coefficients.length];
        for(int i = 0; i < coefficients.length; i++){
            this.coefficients[i] = coefficients[i];
        }
    }
    public Polynomial add(Polynomial that){
        int len1 = this.coefficients.length;
        int len2 = that.coefficients.length;
        int min_len = len1 < len2 ? len1: len2;
        boolean this_min_len = min_len == len1;
        double[] ces = new double[len1 > len2 ? len1: len2];
        for(int i = 0; i < min_len; i++){
            ces[i] = this.coefficients[i]+that.coefficients[i];
        }
        if(this_min_len){
            for(int i = min_len; i < ces.length; i++){
                ces[i] = that.coefficients[i];
            }
        }
        else{
            for(int i = min_len; i < ces.length; i++){
                ces[i] = this.coefficients[i];
            }
        }
        Polynomial result = new Polynomial(ces);
        return result;
    }
    public double evaluate(double x){
        double sum = 0;
        for(int i = 0; i < coefficients.length; i++){
            double product = 1;
            for(int j = 0; j < i; j++){
                product *= x;
            }
            sum += coefficients[i]*product;
        }
        return sum;
    }
    public boolean hasRoot(double x){
        double res = evaluate(x);
        double epsilon = 0.000001;
        if(res < 0.0){
            return (-(res - 0.0)) < epsilon;
        }
        else{
            return (res - 0.0) < epsilon;
        }
    }
}