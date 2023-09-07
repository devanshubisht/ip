package devybot.util;

import devybot.exceptions.DevyBotException;
import devybot.exceptions.EmptyDescriptionException;
import devybot.exceptions.NonIntegerInputException;
import devybot.exceptions.UnknownCommandException;

public class Parser {
    private static enum CommandType {
        TODO, DEADLINE, EVENT, MARK, UNMARK, DELETE, LIST, BYE, UNKNOWN, FIND
    }

    public static CommandType getCommandType(String command) {
        try {
            return CommandType.valueOf(command.toUpperCase());
        } catch (IllegalArgumentException e) {
            return CommandType.UNKNOWN;
        }
    }

    public static void parse(String userInput, TaskList taskList) throws DevyBotException {
        String[] wordsArray = userInput.split("\\s+");
        CommandType commandType = getCommandType(wordsArray[0]);

        switch (commandType) {
            case TODO:
                taskList.addTodoTask(userInput);
                break;
            case DEADLINE:
                taskList.addDeadlineTask(userInput);
                break;
            case EVENT:
                taskList.addEventTask(userInput);
                break;
            case MARK:
                int markIndex = getIndex(wordsArray);
                taskList.markTaskAsDone(markIndex);
                break;
            case UNMARK:
                int unmarkIndex = getIndex(wordsArray);
                taskList.markTaskAsUndone(unmarkIndex);
                break;
            case DELETE:
                int deleteIndex = getIndex(wordsArray);
                taskList.deleteTask(deleteIndex);
                break;
            case BYE:
                Ui.exit();
                break;
            case LIST:
                taskList.listTasks();
                break;
            case FIND:
                taskList.findTasks(userInput);
                break;
            default:
                throw new UnknownCommandException();
        }

    }

    public static int getIndex(String[] wordsArray) throws EmptyDescriptionException, NonIntegerInputException {
        try {
            if (wordsArray.length <= 1) {
                throw new EmptyDescriptionException(wordsArray[0].toString());
            }
            int index = Integer.parseInt(wordsArray[1]) - 1;
            return index;
        } catch (NumberFormatException e) {
            throw new NonIntegerInputException();
        }
    }

}
