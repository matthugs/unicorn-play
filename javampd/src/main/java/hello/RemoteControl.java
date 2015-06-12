package hello;
import org.bff.javampd.*;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class RemoteControl {  
    

    boolean debug = true;
    
    public static void main(String[] args) {

        BoomBox boomBox = null;
        int portNumber = 6001;
        Scanner sc;

        boomBox = new BoomBox(portNumber);
        sc = new Scanner(System.in);
    
        while(true){
            String inputString = sc.nextLine(); // you could swith it with nextInt(), nextFloat() etc based on yoir need
            try{
                if(inputString.equals("play") ){
                    boomBox.play();
                }
                else if(inputString.equals("stop")) {
                    boomBox.stop();
                }
                else {
                    System.out.println("try again dipshit");
                }
            }
            catch(Exception e) {
                e.printStackTrace();
                System.out.println("WTF");
            }
        }

    }

    public void debugOut(String str){
        if (debug) {
            System.out.println(str);
        }
    }
}