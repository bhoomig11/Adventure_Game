package controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * The {@code GameInputReader} class is responsible for reading and validating user input.
 * It ensures that only valid commands can be processed.
 */
public class GameInputReader {
  private final Readable input;
  private final Appendable output;
  private final Scanner scanner;

  /**
   * Construct a {@code GameInputReader} that takes in keyboard input as the source
   * and displays it to user as output destination.
   */
  public GameInputReader() {
    this.input = new InputStreamReader(System.in);
    this.output = System.out;
    this.scanner = new Scanner(this.input);
  }

  /**
   * Construct a {@code GameInputReader} with the specified input source and output destination.
   * @param input : input source.
   * @param output : output destination.
   */
  public GameInputReader(Readable input, Appendable output) {
    this.input = input;
    this.output = output;
    this.scanner = new Scanner(this.input);
  }

  /**
   * Reads and processes user input. It ensures that only valid commands are accepted.
   * If a command is invalid, it prompts the user to try again.
   * @return the valid user command as a {@code String}.
   */
  public String readInput() {
    String userCommand = null;
    try {
      boolean invalidInput = true; // Makes loop continue until a valid command is used

      while (invalidInput) {
        // Display available commands to user
        this.output.append("\n" + """
          ==========\s
          To move, enter: (N)orth, (S)outh, (E)ast or (W)est.
          Other actions: (I)nventory, (L)ook around the location, (U)se an item
          (T)ake an item, (D)rop an item, or e(X)amine something.\s
          (A)nswer a question or provide a text solution.\s
          To end the game, enter (Q)uit to quit and exit.
          Your choice:\s""");
        userCommand = scanner.nextLine().trim().toUpperCase(); // normalize input for validation

        // validate user input
        if (!this.validateInput(userCommand)) {
          this.output.append("Invalid Command. Please try again.\n\n");
        } else {
          invalidInput = false;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return userCommand;
  }

  /**
   * Prompts the user for their avatar username.
   *
   * @return the avatar username
   * @throws IOException if an I/0 error occurs while writing the output
   */
  public String getAvatarName() throws IOException {
    String userCommand = "";
    try {
      while (userCommand.isEmpty()) {
        // Ask user for an avatar
        this.output.append("Enter a name for your player avatar: ");
        userCommand = scanner.nextLine().trim().toUpperCase(); // normalize input for validation

        if (userCommand.isEmpty()) {
          this.output.append("Invalid Avatar Name. Please try again.\n\n");
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return userCommand;
  }

  /**
   * Validates if the given input command is a valid command for the adventure game.
   *
   * @param input : user string input.
   * @return true if command is valid, false otherwise.
   */
  public boolean validateInput(String input) {
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