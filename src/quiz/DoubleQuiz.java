package quiz;

/**
 * Created by upom on 29/09/2016.
 */
public class DoubleQuiz {

    public static void main(String[] args){


        double a = 0.0;
        double b = -0.0;

        System.out.println(a == b);
        System.out.println(new Double(a).equals(new Double(b)));

        double c = Double.NaN;
        double d = Double.NaN;

        System.out.println(c == d);
        System.out.println(new Double(c).equals(new Double(d)));

        double e = 1000.0;
        double f = 0.0;
        while(f < e){
            f += 0.01;
        }

        System.out.println(e);
        System.out.println(f);
        System.out.println(e == f);
    }

}
