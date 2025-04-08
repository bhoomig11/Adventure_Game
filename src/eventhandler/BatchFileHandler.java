package eventhandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class BatchFileHandler implements IEventHandler {
  private final BufferedReader reader;
  private final BufferedWriter writer;

  public BatchFileHandler(String sourceFile, String targetFile) throws IOException {
    this.reader = new BufferedReader(new FileReader(sourceFile));
    this.writer = new BufferedWriter(new FileWriter(targetFile));
  }

  @Override
  public String read() throws IOException {
    return reader.readLine();
  }

  @Override
  public void write(String s) throws IOException {
    writer.write(s);
    writer.flush();
  }

  @Override
  public void setCommandAction(String action) {

  }
}
