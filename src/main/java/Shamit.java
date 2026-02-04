import java.util.Objects;
import java.util.Scanner;
public class Shamit {
    public static void main(String[] args) {
        greet();
        Task[] tasks = new Task[100]; //create an array of tasks object. Limit it to 100
        int counter = 0; //counts the number of tasks
        boolean isEnd = false; //this will be true when user types bye
        String userInput = "";
        while(!isEnd){
            Scanner obj = new Scanner(System.in);
            userInput = obj.nextLine();

            if(Objects.equals(userInput, "bye")){
                isEnd = true;
                bye();
            } else if (Objects.equals(userInput,"list")) {
                tasks = display(tasks,counter);
            } else if (userInput.contains("mark") || userInput.contains("unmark")) {
                tasks = mark_unmark(userInput,tasks);
            } else{
                counter++;
                tasks = add(userInput,counter,tasks); //updating the task variable
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

    public static Task[] add(String userInput,int counter, Task[] tasks){
        int index = counter-1; //this is the index in which the Task object should be stored
        tasks[index] = new Task(userInput);
        System.out.println("I have added the item : " + tasks[index].description);
        return tasks;
    }
    public static Task[] display(Task[] tasks, int counter){
        for(int i = 0; i<counter;i++){
            int number = i+1; //the list of numbers will be index+1. For eg, task 1 will be index 0
            System.out.println("" + number + ".[" + tasks[i].getStatusIcon() + "] " + tasks[i].description);
        }
        return tasks;
    }

    public static Task[] mark_unmark(String userInput, Task[] tasks){
//        System.out.println("The user entered either mark or unmark");
        int length = userInput.length(); //this is how long the input is. Last character of this has to be the task number
        char num = userInput.charAt(length-1);
        int number = Character.getNumericValue(num); //this is the number of the task the user is going to mark/unmark
        if(userInput.contains("unmark")){
//            System.out.println("User entered unmark");
            tasks[number-1].unmark_as_done();
            System.out.println("Ok, I have marked this task as undone: [" + tasks[number-1].getStatusIcon() + "] " + tasks[number-1].description);
        }
        else{
//            System.out.println("User entered mark");
            tasks[number-1].mark_as_done();
            System.out.println("Ok, I have marked this task as done: [" + tasks[number-1].getStatusIcon() + "] " + tasks[number-1].description);
        }
        return tasks;
    }
}