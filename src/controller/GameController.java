package controller;

import java.io.IOException;

import commands.ICommand;
import model.IGameModel;

/**
 * The {@code GameController} class manages the flow of the game.
 * It handles the user input, the command processing, and interacting with the game model.
 */
public class GameController implements IController {
  private final IGameModel model;
  private final Readable input;
  private final Appendable output;

  /**
   * Constructs a {@code GameController} with the specified game model,
   * input source and output destination.
   *
   * @param model : game model.
   * @param input : input source.
   * @param output : output destination.
   */
  public GameController(IGameModel model, Readable input, Appendable output) {
    this.model = model;
    this.input = input;
    this.output = output;
  }

  /**
   * Starts the game loop, continues prompting the user for input, getting the appropriate commands
   * and updating the game state until the user quits the game.
   *
   * @throws IOException if an I/O error occurs while writing output.
   */
  @Override
  public void go() throws IOException {
    // Initialize input reader
    GameInputReader inputReader = new GameInputReader(this.input, this.output);

    // Prompt user for name
    String avatarName = inputReader.getAvatarName();
    this.model.getPlayer().setName(avatarName);
    this.output.append("You shalt now be named: ").append(avatarName.toUpperCase()).append("\n\n");

    // Display initial game state
    this.output.append(this.model.look());

    // Read string input from user
    String userInput = inputReader.readInput();

    // Initialize command finder
    GameCommandFinder commandFinder = new GameCommandFinder(this.output);
    ICommand associatedCommand;

    // Continuous game loop until player quits
    while (!userInput.equalsIgnoreCase("Q")) {
      associatedCommand = commandFinder.getCommand(userInput); // find command associated to input
      associatedCommand.execute(this.model); // executes command
      if (this.model.getPlayer().getHealth() <= 0) {
        break;
      }
      userInput = inputReader.readInput(); // prompt user for next command
    }

    // Game Ending Message
    this.output.append(this.model.getEndingMessage());
  }

  /**
   * Executes a command from the GUI.
   *
   * @param command : command that needs to be executed.
   * @throws IOException if an I/O error occurs.
   */
  public void executeCommand(String command) throws IOException {
    if (validateInput(command)) {
      GameCommandFinder commandFinder = new GameCommandFinder(this.output);
      ICommand associatedCommand = commandFinder.getCommand(command);
      associatedCommand.execute(this.model);
    }
  }

  /**
   * Validates if the given input command is a valid command for the adventure game.
   *
   * @param input : user string input.
   * @return true if command is valid, false otherwise.
   */
  private boolean validateInput(String input) {
    String[] parts = input.split(" ", 2);
    String action = parts[0];

    Commands commandType = Commands.getEnum(action);
    if (commandType == null) {
      return false;
    }

    return commandType.getRequiresArgument() ? parts.length == 2
      && !parts[1].trim().isEmpty() : parts.length == 1;
  }
}