import java.util.Scanner;

public class Input {
   private String msg;



   public String inputStringDataFromTerminal(String msg)
   {
       this.msg = msg;
       Scanner scanner = new Scanner(System.in);
       System.out.println(msg);
       String data = scanner.nextLine();
//        System.out.println("Hello "+data);
       return data;
   }
   public int inputIntDataFromTerminal(String msg)
   {
       this.msg = msg;
       Scanner scanner = new Scanner(System.in);
       System.out.println(msg);
       int data = scanner.nextInt();
       return data;
   }

}

