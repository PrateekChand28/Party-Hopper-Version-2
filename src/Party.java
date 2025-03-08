
/**
 * Models a Party for CS300 P05 Team Party Hopping, similarly to P02. However, unlike P02 we will
 * send Agents to a Party by clicking on it, so it will no longer have a character ID as in P02.
 *
 * @author Prateek Chand
 */
public class Party implements Clickable {

  // A reference to the TeamManagementSystem for this application window-the Party may need to
  // access specific methods you have defined, not just the PApplet rendering methods, so you
  // will need the reference to be of type TeamManagementSystem here (vs Agent's PApplet reference)
  private static TeamManagementSystem tms;

  // The x-position of the center of this Party
  private float x;

  // The y-position of the center of this Party
  private float y;

  // The image associated with this Party
  private processing.core.PImage image;


  /**
   * Constructs a Party represented by the given image at the given (x,y) coordinates
   *
   * @param x     - the x-position of this Party
   * @param y     - the y-position of this Party
   * @param image - the image representing this Party
   */
  public Party(int x, int y, processing.core.PImage image) {
    this.x = x;
    this.y = y;
    this.image = image;
  }

  /**
   * Initializes the class TeamManagementSystem reference to the provided value
   *
   * @param processing - a reference to the TeamManagementSystem object representing this program's
   *                   application window
   */
  public static void setProcessing(TeamManagementSystem processing) {
    Party.tms = processing;
  }

  /**
   * Access method for the current x-coordinate of this Party
   *
   * @return - the current x-coordinate of this agent
   */
  public float getX() {

    return this.x;
  }

  /**
   * Access method for the current y-coordinate of this Party
   *
   * @return - the current y-coordinate of this agent
   */
  public float getY() {

    return this.y;
  }

  /**
   * Draws the image associated with this party to its (x,y) location
   */
  @Override
  public void draw() {
    tms.image(this.image, this.getX(), this.getY());

  }

  /**
   * Determines whether the mouse is over this party
   *
   * @return true if the mouse is anywhere over the image associated with this party, false
   * otherwise
   */
  @Override
  public boolean isMouseOver() {
    float minimumXDimension = this.getX() - ((float) this.image.width / 2);
    float maximumXDimension = this.getX() + ((float) this.image.width / 2);

    float minimumYDimension = this.getY() - ((float) this.image.height / 2);
    float maximumYDimension = this.getY() + ((float) this.image.height / 2);

    // check if the mouse cursor is hovering within the x (horizontal) dimensions of the image
    if ((tms.mouseX > minimumXDimension || Math.abs(
        tms.mouseX - minimumXDimension) < 0.0001) && (tms.mouseX < maximumXDimension || Math.abs(
        tms.mouseX - maximumXDimension) < 0.0001)) {

      // check if the mouse cursor is hovering within the y (vertical) dimensions of the image
      if ((tms.mouseY > minimumYDimension || Math.abs(
          tms.mouseY - minimumYDimension) < 0.0001) && (tms.mouseY < maximumYDimension || Math.abs(
          tms.mouseY - maximumYDimension) < 0.0001)) {
        return true;
      }
    }
    return false;
  }

  /**
   * This method is required by the Clickable interface, but does nothing
   */
  @Override
  public void mousePressed() {
    // This method is intentionally left empty
  }

  /**
   * Defines the behavior of this Party when the mouse is released.
   *
   * If the mouse is over this party, the Party gets the active team from the TeamManagementSystem
   * and sends them to this party
   */
  @Override
  public void mouseReleased() {
    if (this.isMouseOver()) {
      Team currentActiveTeam = tms.getActiveTeam();

      if (currentActiveTeam != null) {
        currentActiveTeam.sendToParty(this);
      }
    }
  }
}
