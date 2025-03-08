/**
 * This interface models Clickable objects in a graphic application
 *
 * @author Prateek Chand
 */
public interface Clickable {
  /**
   * Renders the clickable object to the application window
   */
  void draw();

  /**
   * Determines whether the cursor is currently over this object
   */
  boolean isMouseOver();

  /**
   * Implements the behavior to be run each time the mouse is pressed
   */
  void mousePressed();

  /**
   * Implements the behavior to be run each time the mouse is released
   */
  void mouseReleased();

}
