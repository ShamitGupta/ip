import java.util.Objects;
import java.util.Scanner;
public class Shamit {
    public static void main(String[] args) {
        greet();
        boolean isEnd = false; //this will be true when user types bye
        String[] tasks = new String[10]; //initialised to a 5 length array first, will need to monitor while adding tasks
        String userInput = "";
        int counter = 0; //totals the number of items added to the list
        while(!isEnd){
            Scanner obj = new Scanner(System.in);
            userInput = obj.nextLine();

            if(Objects.equals(userInput, "bye")){
                isEnd = true;
                bye();
            }
            else{
                counter++; //increase the number of tasks
                tasks = add(userInput,tasks,counter); //updating the task variable


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

    public static String[] add(String content, String[] tasks,int counter){
        int size = tasks.length;
        if(counter > size){ //this means that the array is full, and we need to make a new bigger array
            String[] newArray = new String[size*2]; //creating twice the original size
            for(int j = 0; j<size; j++){
                newArray[j] = tasks[j]; //duplicating old Array's values inside new array
            }
            tasks = newArray; //changing the tasks array into the new bigger array
        }

        int index = counter - 1; //2nd item will be first index in the array
        tasks[index] = content; //adding the task inside the array
        for(int i = 0; i<counter;i++){
            int number = i+1; //the list of numbers will be index+1. For eg, task 1 will be index 0
            System.out.println("" + number + ": " + tasks[i]);
        }
        return tasks;
    }
}
