import java.util.Objects;
import java.util.Scanner;
public class Shamit {
    public static void main(String[] args) {
        greet();
        boolean isEnd = false; //this will be true when user types bye
        String userInput = "";

        while(!isEnd){
            Scanner obj = new Scanner(System.in);
            userInput = obj.nextLine();

            if(Objects.equals(userInput, "bye")){
                isEnd = true;
                bye();
            }
            else{
                echo(userInput);

            }
        }


    }

    public static void greet(){
        String name = "Shamit";
        System.out.println("Hello! I'm "+ name + "\nHow can I help you?");
    }

    public static void bye(){
        System.out.println("Bye, please come again!");
    }

    public static void echo(String content){
        System.out.println("-----------------------");
        System.out.println(content);
        System.out.println("-----------------------");
    }
}
