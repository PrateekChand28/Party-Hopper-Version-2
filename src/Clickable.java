//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Clickable Interface for Team Party Hopping GUI Interface
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
// Persons: No Help Used
//
// Online Sources:
// - https://docs.oracle.com/javase/tutorial/java/concepts/interface.html
// - https://canvas.wisc.edu/courses/447785/files/44550488?wrap=1
//
///////////////////////////////////////////////////////////////////////////////


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
