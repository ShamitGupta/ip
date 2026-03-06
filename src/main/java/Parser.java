public class Parser {
    public static String getCommandWord(String fullCommand) {
        return fullCommand.split(" ")[0];
    }

    public static int parseIndex(String fullCommand, int listSize) throws ShamitException {
        try {
            String[] parts = fullCommand.split(" ");
            int index = Integer.parseInt(parts[1]) - 1;
            if (index < 0 || index >= listSize) {
                throw new ShamitException("That task number doesn't exist!");
            }
            return index;
        } catch (Exception e) {
            throw new ShamitException("Please provide a valid task number.");
        }
    }

    public static String parseKeyword(String fullCommand) throws ShamitException {
        if (fullCommand.trim().equals("find")) {
            throw new ShamitException("What keyword should I look for?");
        }
        return fullCommand.substring(5).trim();
    }

    public static Task parseTask(String fullCommand) throws ShamitException {
        if (fullCommand.startsWith("todo")) {
            if (fullCommand.trim().equals("todo")) throw new ShamitException("Todo description cannot be empty.");
            return new ToDo(fullCommand.substring(5).trim());
        } else if (fullCommand.startsWith("deadline")) {
            if (!fullCommand.contains("/by")) throw new ShamitException("Deadline needs a /by date.");
            String[] parts = fullCommand.substring(9).split("/by", 2);
            return new Deadline(parts[0].trim(), parts[1].trim());
        } else if (fullCommand.startsWith("event")) {
            if (!fullCommand.contains("/from") || !fullCommand.contains("/to")) {
                throw new ShamitException("Event needs both /from and /to.");
            }
            String[] parts = fullCommand.substring(6).split("/from", 2);
            String[] times = parts[1].split("/to", 2);
            return new Event(parts[0].trim(), times[0].trim(), times[1].trim());
        }
        throw new ShamitException("I don't know what that command means.");
    }
}