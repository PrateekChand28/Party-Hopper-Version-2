//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Lead Derived Class for Team Party Hopping GUI Interface
// Course:   CS 300 Spring 2025
//
// Author:   Prateek Chand
// Email:    pchand2@wisc.edu
// Lecturer: Hobbes LeGault
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name:    Not Applicable
// Partner Email:   Not Applicable
// Partner Lecturer's Name: Not Applicable
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
//   ___ Write-up states that pair programming is allowed for this assignment. X
//   ___ We have both read and understand the course Pair Programming Policy. X
//   ___ We have registered our team prior to the team registration deadline. X
//
//////////////////////// ASSISTANCE/HELP CITATIONS ////////////////////////////
//
// Persons: TA Ruifeng Xu helped me understand how the mouseReleased() method is expected to
// behavior based on different status of the corresponding agents
//
// Online Sources:
// - https://canvas.wisc.edu/courses/447785/files/44550491?wrap=1
//
///////////////////////////////////////////////////////////////////////////////


/**
 * Models a Team, Lead agent for the CS300 P05 Team Party Hopping project. event Team can have at
 * most one Lead, and clicking on that Team's Lead selects ALL members of the Team at the same time
 *
 * @author Prateek Chand
 */
public class Lead extends Agent {
  /**
   * Constructs a new Lead at the given x,y coordinates
   *
   * @param x - the x-position of this Lead
   * @param y - the y-position of this Lead
   */
  public Lead(int x, int y) {
    super(x, y);
  }

  /**
   * Annotates a Lead's agent representation by drawing an inverted black triangle over the circle
   * in the color corresponding to this Lead's selection/team status.
   *
   * See the writeup for details
   */
  @Override
  public void draw() {
    super.draw();
    Agent.processing.fill(0);
    Agent.processing.triangle(this.getX() - this.diameter() / 3.0F,
        this.getY() - this.diameter() / 5.0F, this.getX() + this.diameter() / 3.0F,
        this.getY() - this.diameter() / 5.0F, this.getX(), this.getY() +
            this.diameter() / 3.0F);
  }

  /**
   * Defines the specific behavior of this team lead when the mouse is released. If this lead was
   * activated by the mouse being released, this method activates ALl the members of this lead's
   * team (if this lead has a team);
   *
   * CITE: TA Ruifeng Xu helped me understand the expected behavior of this method
   */
  @Override
  public void mouseReleased() {
    super.mouseReleased();

    // activates other agents only when Lead is active
    if (this.isActive() && this.getTeam() != null) {
      this.getTeam().selectAll();
    }

  }
}
