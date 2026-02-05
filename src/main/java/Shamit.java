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
                dismiss();
            } else if (Objects.equals(userInput,"list")) {
                tasks = display(tasks,counter);
            } else if (userInput.contains("mark") || userInput.contains("unmark")) {
                tasks = updateStatus(userInput,tasks);
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

    public static void dismiss(){
        System.out.println("Bye, please come again!");
    }

    public static Task[] add(String userInput,int counter, Task[] tasks){
        int index = counter-1; //this is the index in which the Task object should be stored
//        tasks[index] = new Task(userInput);
        if(userInput.contains("deadline")){//this means we need to use the deadline class
            String description = "";
            String by = "";
            boolean reachedBy = false;
            for(int i = 9;i<userInput.length();i++){//start with i=9 because thats the length of the word 'deadline'
                if(userInput.charAt(i) == '/'){
                    reachedBy = true;
                    i = i+3;
                }

                if(!reachedBy){
                    description = description + userInput.charAt(i);
                } else{
                    by = by + userInput.charAt(i);
                }
            }
            tasks[index] = new Deadline(description,by);
//            System.out.println("I have added the deadline: " + tasks[index].toString());
        } else if (userInput.contains("todo")) {
            String description = "";
            String command = "todo";
            int startingIndex = command.length()+1;
            for(int i = startingIndex; i <userInput.length();i++){
                description = description + userInput.charAt(i);
            }
            tasks[index] = new ToDo(description);
        } else if (userInput.contains("event")) {
            String command = "event";
            int startingIndex = command.length(); //this is where we start iterating from
            String description = "";
            String from = "";
            String to = "";
            boolean reachedFrom = false;
            boolean reachedTo = false;

            for(int i = startingIndex; i<userInput.length();i++){
                if(!reachedFrom && !reachedTo && userInput.charAt(i)=='/'){//this means that the first / is reached
                    reachedFrom = true;
                    i = i+5; //because of the 'from'
//                    continue;
                }else if(reachedFrom && !reachedTo && userInput.charAt(i)=='/'){//this means that the second / is reached
                    reachedTo = true;
                    i = i+3; //because of the 'to'
//                    continue;
                }

                if(!reachedFrom && !reachedTo){
                    description = description + userInput.charAt(i);
                } else if (reachedFrom && !reachedTo) {
                    from = from + userInput.charAt(i);
                } else if (reachedFrom && reachedTo) {
                    to = to + userInput.charAt(i);
                }
            }
            tasks[index] = new Event(description,from,to);
        }

        System.out.println("Got it! I have now added the task: " + tasks[index].toString() + "\nNow you have " + counter + " tasks in the list");
        return tasks;
    }
    public static Task[] display(Task[] tasks, int counter){
        for(int i = 0; i<counter;i++){
            int number = i+1; //the list of numbers will be index+1. For eg, task 1 will be index 0
            System.out.println("" + number + "."+ tasks[i].toString());
        }
        return tasks;
    }

    public static Task[] updateStatus(String userInput, Task[] tasks){
//        System.out.println("The user entered either mark or unmark");
        int length = userInput.length(); //this is how long the input is. Last character of this has to be the task number
        char num = userInput.charAt(length-1);
        int number = Character.getNumericValue(num); //this is the number of the task the user is going to mark/unmark
        if(userInput.contains("unmark")){
//            System.out.println("User entered unmark");
            tasks[number-1].unmarkDone();
            System.out.println("Ok, I have marked this task as undone: " + tasks[number-1].toString());
        }
        else{
//            System.out.println("User entered mark");
            tasks[number-1].markDone();
            System.out.println("Ok, I have marked this task as done: " + tasks[number-1].toString());
        }
        return tasks;
    }
}