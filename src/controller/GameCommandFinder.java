package controller;

import java.util.Arrays;
import java.util.List;

import commands.AnswerCommand;
import commands.DropCommand;
import commands.ExamineCommand;
import commands.ICommand;
import commands.InventoryCommand;
import commands.LookCommand;
import commands.MoveCommand;
import commands.RestoreCommand;
import commands.SaveCommand;
import commands.TakeCommand;
import commands.UseCommand;
import io.IGameInput;
import io.IGameOutput;

/**
 * The {@code GameCommandFinder} class is responsible for parsing and retrieving the correct
 * commands based on the user input.
 */
public class GameCommandFinder {
  private final IGameOutput output;

  /**
   * Constructs a {@code GameCommandFinder} class with the given output destination.
   *
   * @param output the {@code IGameOutput} object where command outputs will be written.
   */
  public GameCommandFinder(IGameOutput output) {
    this.output = output;
  }

  /**
   * Splits the user command into an action and a noun, and matches the action to the corresponding
   * {@code Command} enum and uses the enum to trigger the corresponding {@link ICommand} instance.
   *
   * @param command : the user input string.
   * @return an instance of {@code ICommand} corresponding to the user input.
   * @throws IllegalArgumentException if the input is null or empty.
   */
  public ICommand getCommand(String command) throws IllegalArgumentException {
    if (command == null || command.isEmpty()) {
      throw new IllegalArgumentException("Input cannot be null or empty!");
    }

    List<String> commandTokens = splitCommand(command);
    String action = commandTokens.getFirst().toUpperCase();
    String noun = commandTokens.size() > 1 ? commandTokens.get(1) : null;

    Commands commandType = Commands.getEnum(action);
    if (commandType == null) {
      return null;
    }

    return switch (commandType) {
      case MOVE -> new MoveCommand(action, this.output);
      case INVENTORY -> new InventoryCommand(this.output);
      case LOOK -> new LookCommand(this.output);
      case USE -> new UseCommand(noun, this.output);
      case TAKE -> new TakeCommand(noun, this.output);
      case DROP -> new DropCommand(noun, this.output);
      case EXAMINE -> new ExamineCommand(noun, this.output);
      case ANSWER -> new AnswerCommand(noun, this.output);
      case SAVE -> new SaveCommand(this.output);
      case RESTORE -> new RestoreCommand(this.output);
      case QUIT -> null;
    };
  }

  /**
   * Helper functions that splits the user input string into a verb and a noun.
   *
   * @param command user input string.
   * @return a list of string with two elements.
   *          - first element is the action.
   *          - second element is the noun.
   *         If the command only has one word then the array will only have one element.
   */
  private List<String> splitCommand(String command) {
    String[] splitCommand = command.split(" ", 2);
    return Arrays.asList(splitCommand);
  }
}
