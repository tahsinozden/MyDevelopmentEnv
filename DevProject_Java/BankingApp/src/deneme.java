import java.io.*;
import java.lang.Integer;

public class deneme {
    public static void main(String args[] ) throws Exception {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT */
        int maxDiff = Integer.parseInt( args[1]) - Integer.parseInt(args[0]) ;
        for( int i=1; i < args.length-1; i++){
            int tmpMax = Integer.parseInt( args[i+1]) - Integer.parseInt(args[i]) ;
            if ( tmpMax > maxDiff){
                int number =  Integer.parseInt(args[i])  + tmpMax / 2;
                System.out.println(number);
            }
            
        }
    }
}
