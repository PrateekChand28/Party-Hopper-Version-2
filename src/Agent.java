import processing.core.PApplet;

/**
 * Models an Agent for the CS300 P05 Team Party Hopping project. Note: The methods that are listed
 * as protected in this documentation are intended to be helper methods, for use only inside the
 * class. They are listed as protected for testing purposes.
 *
 * @author Prateek Chand
 */
public class Agent implements Clickable {
  // This will store the x-coordinate that an Agent is actively moving to
  private float destX;

  // This will store the y-coordinate that an Agent is actively moving to
  private float destY;

  // The standard diameter of all Agent representations, protected for access in the child
  protected static int diameter = 20;

  // Indicator of whether this Agent is currently active
  private boolean isActive;

  // Indicator of whether this Agent is currently being dragged
  protected boolean isDragging;

  // Storage for the previous x-position of the mouse, for use in dragging Agents around the window
  private int oldMouseX;

  // Storage for the previous y-position of the mouse, for use in dragging Agents around the window
  private int oldMouseY;

  // This will store the original x-coordinate of an agent when the mouse is pressed, for
  // determining whether the agent was dragged at all when the mouse is released
  private float originalX;

  // This will store the original x-coordinate of an agent when the mouse is pressed, for
  // determining whether the agent was dragged at all when the mouse is released
  private float originalY;

  // A reference to the application window, for creating visual representations
  protected static processing.core.PApplet processing;

  // A reference to this Agent's assigned team, or null if this Agent does not yet have a team
  protected Team team;

  // The x-position of the center of this Agent
  private float xPos;

  // The y-position of the center of this Agent
  private float yPos;

  /**
   * Constructs a new agent at the given x,y coordinate, and initializes any data fields with
   * non-default values
   *
   * @param x - the initial x-coordinate of this agent
   * @param y - the initial y-coordinate of this agent
   */
  public Agent(int x, int y) {
    this.xPos = x;
    this.yPos = y;

    this.destX = -1F;
    this.destY = -1F;

    this.originalX = -1F;
    this.originalY = -1F;

    this.isActive = false;
    this.isDragging = false;

    this.oldMouseX = -1;
    this.oldMouseY = -1;

    this.team = null;

  }

  /**
   * Accesses the value of the class variable diameter
   *
   * @return the diameter of every Agent's representation
   */
  public static int diameter() {
    return diameter;
  }

  /**
   * Initializes the class PApplet reference to the provided value
   *
   * @param processing - a reference to the PApplet object representing this program's application
   *                   window
   */
  public static void setProcessing(processing.core.PApplet processing) {
    // CITE: handling static variable and parameter with same name - StackOverflow
    Agent.processing = processing;
  }

  /**
   * Reports whether this Agent has been selected
   *
   * @return true if this agent is active, false otherwise
   */
  public boolean isActive() {
    return this.isActive;
  }

  /**
   * Helper method, reports whether this Agent is currently moving. A currently-moving Agent will
   * have destination coordinates that are not (-1,-1)
   *
   * @return true if this agent is moving, false otherwise
   */
  protected boolean isMoving() {
    // CITE: TA Ben helped with debugging the logic error
    return (this.destX != -1 || this.destY != -1);
  }

  /**
   * Accessor method for the current x-coordinate of this Agent
   *
   * @return the current x-coordinate of this agent
   */
  public float getX() {
    return this.xPos;
  }

  /**
   * Accessor method for the current y-coordinate of this Agent
   *
   * @return the current y-coordinate of this agent
   */
  public float getY() {
    return this.yPos;
  }

  /**
   * Accessor method for the Team reference of this Agent
   *
   * @return a direct reference to the team that this agent is a member of, or null if this agent is
   * not a member of any team
   */
  public Team getTeam() {
    return this.team;
  }

  /**
   * Helper method to determine the color to use for drawing this agent: = When active, always
   * color(0,255,0) (green) - Otherwise if part of a team, that team's color - Otherwise,
   * color(255,255,0) (yellow)
   *
   * @return an integer representing the color that this agent should be drawn in
   */
  protected int getColor() {
    if (this.isActive) {
      return processing.color(0, 255, 0);

    } else if (this.team != null) {
      return processing.color(this.team.getColor());

    } else {
      return processing.color(255, 255, 0);
    }
  }

  /**
   * Switches the active status of this agent to its opposite-if false, makes it true; if true,
   * makes it false
   */
  public void toggleActive() {
    this.isActive = !this.isActive;
  }

  /**
   * Sets the team of this agent to be the provided value. If this agent was already a member of a
   * team, this method should ALSO remove them from their previous team.
   *
   * @param t-the team to add this agent to
   */
  public void setTeam(Team t) {
    if (this.team != null) {
      this.team.removeMember(this);
    }
    this.team = t;
  }

  /**
   * Sets the destination coordinates of this agent to be the provided values and deactivates the
   * agent
   *
   * @param x - this agent's new destination x-coordinate
   * @param y - this agent's new destination y-coordinate
   */
  public void setDestination(float x, float y) {
    this.destX = x;
    this.destY = y;
    this.isActive = false;
  }

  /**
   * Helper method, sets this agent to be dragging and initializes the oldMouseX and oldMouseY
   * fields to the current location of the mouse
   */
  protected void startDragging() {
    this.isDragging = true;
    this.oldMouseX = Agent.processing.mouseX;
    this.oldMouseY = Agent.processing.mouseY;
  }

  /**
   * Helper method, sets this agent to no longer be dragging
   */
  protected void stopDragging() {
    this.isDragging = false;
  }

  /**
   * Helper method containing the logic to update this agent's position correctly while being
   * dragged CITE: debugged this method with the help of TA Ben
   */
  protected void drag() {

    if (this.isDragging) {
      int dx = Agent.processing.mouseX - oldMouseX;
      int dy = Agent.processing.mouseY - oldMouseY;

      xPos += dx;
      yPos += dy;

      this.oldMouseX = processing.mouseX;
      this.oldMouseY = processing.mouseY;
    }
  }

  /**
   * Helper method to move an agent 3 units towards its destination, if one is set; if the agent is
   * within 3 units of its destination, moves the agent directly to its destination and resets the
   * destination coordinates to (-1,-1)
   *
   * If no destination is set, the method does nothing
   */
  protected void move() {
    float dx = this.destX - this.xPos;
    float dy = this.destY - this.yPos;

    float totalDist = (float) Math.sqrt(Math.pow(dx, 2.0) + Math.pow(dy, 2.0));

    if (this.destX != -1F && this.destY != -1F) {
      if (totalDist <= 3) {
        this.xPos = this.destX;
        this.yPos = this.destY;
        this.destX = -1F;
        this.destY = -1F;
      } else {
        this.xPos += 3 * (dx / totalDist);
        this.yPos += 3 * (dy / totalDist);
      }
    }
  }

  /**
   * Renders this agent to the application window after making any required updates to its position,
   * that is, if being dragged or if moving to a destination. Agents are rendered as a circle of the
   * class' diameter at their specific x,y coordinates, drawn in the color returned by the helper
   * method getColor(). (See the writeup for more details)
   */
  @Override
  public void draw() {
    Agent.processing.fill(this.getColor());
    Agent.processing.circle(this.xPos, this.yPos, Agent.diameter());

    if (this.isDragging) {
      this.drag();
    }

    if (this.isMoving()) {
      this.move();
    }

  }

  /**
   * Determines whether the mouse is over this agent
   *
   * @return true if the mouse is over this agent,false otherwise
   */
  @Override
  public boolean isMouseOver() {

    float minimumXDimension = this.getX() - ((float) Agent.diameter() / 2);
    float maximumXDimension = this.getX() + ((float) Agent.diameter() / 2);

    float minimumYDimension = this.getY() - ((float) Agent.diameter() / 2);
    float maximumYDimension = this.getY() + ((float) Agent.diameter() / 2);

    // check if the mouse cursor is hovering within the x (horizontal) dimensions of the agent
    // image
    if ((Agent.processing.mouseX > minimumXDimension || Math.abs(
        Agent.processing.mouseX - minimumXDimension) < 0.0001) &&
        (Agent.processing.mouseX < maximumXDimension || Math.abs(
        Agent.processing.mouseX - maximumXDimension) < 0.0001)) {

      // check if the mouse cursor is hovering within the y (vertical) dimensions of the
      // agent image
      if ((Agent.processing.mouseY > minimumYDimension || Math.abs(
          Agent.processing.mouseY - minimumYDimension) < 0.0001) &&
          (Agent.processing.mouseY < maximumYDimension || Math.abs(
          Agent.processing.mouseY - maximumYDimension) < 0.0001)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Defines the behavior of this agent when it is clicked on. This method is called only when the
   * mouse is over the agent.
   *
   * An agent that has been clicked on should begin dragging and note its current (x,y) position in
   * the originalX/originalY fields, but only if it is not already moving
   */
  @Override
  public void mousePressed() {
    if (!this.isMoving() && this.isMouseOver()) {
      this.originalX = this.xPos;
      this.originalY = this.yPos;
      this.startDragging();
    }

  }

  /**
   * Defines the behavior of this agent when the mouse is released.
   *
   * When the mouse is released, all agents STOP dragging; if this agent's current (x,y) position is
   * identical to the values stored in its originalX/originalY, it has been activated. Regardless,
   * all agents' originalX/originalY values are reset to -1.
   */
  @Override
  public void mouseReleased() {
    this.stopDragging();

    if (Math.abs(this.xPos - this.originalX) < 1 && Math.abs(this.yPos - this.originalY) < 1) {
      this.toggleActive();
    }

    this.originalX = -1.0F;
    this.originalY = -1.0F;

  }
}
