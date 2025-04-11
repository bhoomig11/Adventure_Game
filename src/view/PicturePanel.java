package view;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import org.apache.commons.text.WordUtils;
import javax.imageio.ImageIO;
import javax.swing.*;

import static view.ViewUtils.getPanelFont;

/**
 * A class representing the description panel in the graphical view of the game.
 */
public class PicturePanel extends JPanel {
  // Data fields
  private static final int WIDTH_SCALE = 450;
  private static final int HEIGHT_SCALE = 400;
  private static final Color TEXT_COLOR = new Color(40, 54, 24);
  private final static Color PANEL_COLOR = new Color(236, 240, 235);

  private JLabel roomLabel;
  private JLabel pictureLabel;
  BufferedImage image;

  public PicturePanel() {
    this.setLayout(new BorderLayout());
    this.setBackground(PANEL_COLOR);
    this.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    this.roomLabel = new JLabel();
    this.roomLabel.setFont(getPanelFont().deriveFont(Font.BOLD, 20));
    this.roomLabel.setForeground(TEXT_COLOR);
    this.add(roomLabel, BorderLayout.NORTH);

    this.pictureLabel = new JLabel("No picture found", SwingConstants.CENTER);
    this.pictureLabel.setFont(getPanelFont().deriveFont(Font.BOLD, 20));
    this.pictureLabel.setBorder(BorderFactory.createEmptyBorder(75, 0,0,0));
    this.add(pictureLabel, BorderLayout.CENTER);
  }

  public void updatePicturePanel(String roomName, String picturePath) {
    roomName = WordUtils.capitalizeFully(roomName);
    roomLabel.setText(roomName);

    try {
      this.pictureLabel.setText(null);
      image = ImageIO.read(getClass().getResource(picturePath));
      Image scaledImage = getScaledImage(image);
      this.pictureLabel.setIcon(new ImageIcon(scaledImage));
    } catch (IOException e) {
      this.pictureLabel.setIcon(null);
      this.pictureLabel.setText("No picture found");
      this.pictureLabel.setFont(getPanelFont().deriveFont(Font.BOLD, 12));
    }
  }

  private Image getScaledImage(BufferedImage image) {
    int imageWidth = image.getWidth();
    int imageHeight = image.getHeight();

    if (imageWidth > WIDTH_SCALE && imageHeight > HEIGHT_SCALE) {
      return image.getScaledInstance(WIDTH_SCALE, HEIGHT_SCALE, Image.SCALE_SMOOTH);
    } else if (imageWidth > WIDTH_SCALE) {
      return image.getScaledInstance(WIDTH_SCALE, imageHeight, Image.SCALE_SMOOTH);
    } else {
      return image.getScaledInstance(imageWidth, HEIGHT_SCALE, Image.SCALE_SMOOTH);
    }
  }
}
