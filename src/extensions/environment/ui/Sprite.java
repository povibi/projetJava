package extensions.environment.ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class Sprite {

  private String currentAnimation;
  private boolean animated = false;
  private HashMap<String, Animation> animations = new HashMap<>();
  private BufferedImage spriteImage;

  public Sprite(String fileName) {
    try {
      spriteImage = ImageIO.read(new File(fileName));
    } catch (IOException e) {
      System.err.println("Could not Initialize sprite: " + e);
    }
  }

  public int getWidth() {
    return animated ? animations.get(currentAnimation).getWidth()
                    : spriteImage.getWidth();
  }

  public int getHeight() {
    return animated ? animations.get(currentAnimation).getHeight()
                    : spriteImage.getHeight();
  }

  public void registerAnimation(String animationName, Animation animation) {
    animations.put(animationName, animation);
  }

  public void setAnimation(String animationName) {
    if (currentAnimation != animationName) {
      animations.get(animationName).reset();
      currentAnimation = animationName;
    }
    animated = true;
  }

  public void draw(int x, int y, Graphics g) {
    if (animated) {
      Animation animation = animations.get(currentAnimation);
      animation.update();
      g.drawImage(spriteImage, x, y, x + animation.getWidth(),
                  y + animation.getHeight(), animation.getX(), animation.getY(),
                  animation.getX() + animation.getWidth(),
                  animation.getY() + animation.getHeight(), null);
    } else
      g.drawImage(spriteImage, x, y, null);
  }
}
