//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Test Bench for Team Party Hopping GUI Interface
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
// Persons: Not Used
//
// Online Sources:
// - https://canvas.wisc.edu/courses/447785/pages/p05
//
///////////////////////////////////////////////////////////////////////////////

import java.util.ArrayList;

/**
 * A short tester class for verifying some of the Agent and Team behaviors in P05.
 *
 * @author Prateek Chand
 */
public class TeamTester {

  /**
   * Verifies that an Agent’s initial position is set correctly upon creation.
   *
   * This test should: - Create two agents at different (x,y) coordinates - Verify that their getX()
   * and getY() methods return the expected values - Verify that their initial positions match the
   * coordinates provided to their constructors
   *
   * @return true if both agents are created with correct coordinates; false otherwise
   */
  public static boolean testAgentInitialPosition() {
    {
      Agent agent1 = new Agent(0, 3);
      Agent agent2 = new Agent(5, 7);

      if (agent1.getX() != 0.0F || agent2.getX() != 5.0F) {
        return false;
      }

      if (agent1.getY() != 3.0F || agent2.getY() != 7.0F) {
        return false;
      }
    }
    return true;
  }

  /**
   * Verifies that an Agent moves correctly when given a destination.
   *
   * This test should: - Create an agent at a known position - Set a destination that is at least 10
   * pixels away in both x and y directions - Call the move() method once - Verify that the agent
   * has moved closer to the destination but has not reached it - Verify that the movement follows
   * the expected trajectory
   *
   * @return true if agent movement behavior is correct; false otherwise
   */
  public static boolean testAgentMovement() {
    {
      Agent agent1 = new Agent(0, 3);
      agent1.setDestination(12, 12);

      float dx = 12.0F - agent1.getX();
      float dy = 12.0F - agent1.getY();

      // Distance between the initial and destination points of movement
      float initialDistance = (float) Math.sqrt(Math.pow(dx, 2.0) + Math.pow(dy, 2.0));

      agent1.move();

      float newDx = 12.0F - agent1.getX();
      float newDy = 12.0F - agent1.getY();

      // Distance between the initial and destination points of movement
      float totalDistMoved = (float) Math.sqrt(Math.pow(newDx, 2.0) + Math.pow(newDy, 2.0));

      // Checks if the agent reached or moved past the destination coordinates
      if (totalDistMoved >= initialDistance) {
        return false;
      }

      // Checks if the agent moved the right unit of distance in both the x and y direction
      if (agent1.getX() != (3 * (dx / initialDistance)) && agent1.getY()
          != (3 * (dy / initialDistance))) {
        return false;
      }

    }

    return true;
  }

  /**
   * Verifies that an Agent without a destination remains stationary.
   *
   * This test should: - Create an agent at a specific position - Record its initial position - Call
   * the move() method - Verify that the agent’s position has not changed
   *
   * @return true if agent remains stationary when no destination is set; false otherwise
   */
  public static boolean testAgentStationary() {
    {
      Agent agent1 = new Agent(0, 3);

      float initialXPosition = agent1.getX();
      float initialYPosition = agent1.getY();

      agent1.move();

      // checks if the center of the Agent changed when trying to move it
      if (agent1.getX() != initialXPosition || agent1.getY() != initialYPosition) {
        return false;
      }

    }

    return true;
  }

  /**
   * Verifies that creating a Team with multiple Leads throws an IllegalArgumentException.
   *
   * This test should: - Create an ArrayList of Agents that includes multiple Lead instances -
   * Attempt to create a Team with this ArrayList - Verify that an IllegalStateException is thrown
   *
   * @return true if the correct exception is thrown; false otherwise
   */
  public static boolean testMultipleLeadsException() {
    {
      // Checks if the Team constructor correctly handles arraylists with multiple leads
      try {
        Agent testAgentOne = new Agent(0, 0);
        Lead testAgentTwo = new Lead(13, 25);
        Lead testAgentThree = new Lead(5, 20);
        Agent testAgentFour = new Agent(13, 56);

        ArrayList<Agent> testAgents = new ArrayList<>();
        testAgents.add(testAgentOne);
        testAgents.add(testAgentTwo);
        testAgents.add(testAgentThree);
        testAgents.add(testAgentFour);

        Team testTeam = new Team(3, testAgents);

        return false;

      } catch (IllegalStateException e) {
        // Returns true
      } catch (Exception e) {
        return false;
      }
    }

    return true;
  }

  /**
   * Verifies behavior around empty teams.
   *
   * This test should: - Create an empty ArrayList - Attempt to create a Team with this ArrayList -
   * Verify that an IllegalArgumentException is thrown - Add at least one Agent to the ArrayList and
   * create a valid team - Remove all agents from the team - Verify that the Team's size is now
   * zero
   *
   * @return
   */
  public static boolean testEmptyTeam() {
    {
      // Checks for exceptional handling when an empty Arraylist is provided to the Team constructor
      ArrayList<Agent> testAgents = new ArrayList<>();
      Team testTeam;

      try {
        testTeam = new Team(3, testAgents);
        return false;
      } catch (IllegalArgumentException e) {
        // Returns true
      } catch (Exception e) {
        return false;
      }

      // Checks if the .removeMember() method properly removes the specified agent
      try {
        Agent testAgentOne = new Agent(0, 0);
        Agent testAgentTwo = new Agent(13, 25);
        Agent testAgentThree = new Agent(5, 20);
        Agent testAgentFour = new Agent(13, 56);

        testAgents.add(testAgentOne);
        testAgents.add(testAgentTwo);
        testAgents.add(testAgentThree);
        testAgents.add(testAgentFour);

        testTeam = new Team(3, testAgents);

        int initialTeamSize = testTeam.getTeamSize();

        if (initialTeamSize != testAgents.size()) {
          return false;
        }

        testTeam.removeMember(testAgentOne);
        testTeam.removeMember(testAgentTwo);
        testTeam.removeMember(testAgentThree);
        testTeam.removeMember(testAgentFour);

        int finalTeamSize = testTeam.getTeamSize();

        if (finalTeamSize != 0) {
          return false;
        }

      } catch (Exception e) {
        return false;
      }

    }
    return true;
  }

  /**
   * Verifies that a Team can be created successfully with exactly one Lead.
   *
   * This test should: - Create an ArrayList with one Lead and multiple regular Agents - Create a
   * Team with this ArrayList - Verify that the Team is created successfully - Verify that the Team
   * size matches the ArrayList size - Verify that all Agents are properly added to the Team -
   * Verify that the hasLead method correctly reports that this team has a Lead
   *
   * @return true if Team creation succeeds with correct composition; false otherwise
   */
  public static boolean testValidTeamCreation() {
    {
      // Checks if the Team's constructor correctly works when provided with valid agents list
      try {
        Agent testAgentOne = new Agent(0, 0);
        Agent testAgentTwo = new Lead(13, 25);
        Agent testAgentThree = new Agent(5, 20);
        Agent testAgentFour = new Agent(13, 56);

        ArrayList<Agent> testAgents = new ArrayList<>();
        testAgents.add(testAgentOne);
        testAgents.add(testAgentTwo);
        testAgents.add(testAgentThree);
        testAgents.add(testAgentFour);

        Team testTeam = new Team(3, testAgents);

        if (testTeam == null) {
          return false;
        }

        // Checks if the lead is present in the team
        if (!testTeam.hasLead()) {
          return false;
        }

        int currentTeamSize = testTeam.getTeamSize();
        int currentArraySize = testAgents.size();

        // Checks if all the agents are present in the team
        if (currentTeamSize != currentArraySize) {
          return false;
        }

      } catch (Exception e) {
        return false;
      }
    }
    return true;
  }

  /**
   * Verifies that a new Agent can be added to an existing Team.
   *
   * This test should: - Create a valid Team with one Lead and at least one Agent - Create a new
   * Agent - Add the new Agent to the Team using addAgent() - Verify that the Team size has
   * increased - Verify that the new Agent is now a member of the Team
   *
   * @return true if Agent is successfully added to Team; false otherwise
   */
  public static boolean testAddAgentToTeam() {

    Agent testAgentOne = new Agent(3, 3);
    Lead testAgentTwo = new Lead(13, 25);
    Agent testAgentThree = new Agent(5, 20);
    Agent testAgentFour = new Agent(13, 56);

    ArrayList<Agent> testAgents = new ArrayList<>();

    testAgents.add(testAgentOne);
    testAgents.add(testAgentTwo);
    testAgents.add(testAgentThree);

    Team testTeam = new Team(3, testAgents);

    int initialTeamSize = testTeam.getTeamSize();

    // adds new agent into the team
    testTeam.addMember(testAgentFour);

    int finalTeamSize = testTeam.getTeamSize();

    if (finalTeamSize != initialTeamSize + 1) {
      return false;
    }

    // checks if the specified agent is present in the team
    if (!testTeam.contains(testAgentFour)) {
      return false;
    }

    return true;
  }

  /**
   * Verifies that Team’s center coordinates are calculated correctly.
   *
   * This test should: - Create a Team with at least three Agents at known positions - Calculate the
   * expected center coordinates manually - Compare the expected values with getCenterX() and
   * getCenterY() results - Verify that adding a new Agent updates the center coordinates correctly
   *
   * @return true if center coordinates are calculated correctly; false otherwise
   */
  public static boolean testTeamCenter() {

    Agent testAgentOne = new Agent(3, 3);
    Agent testAgentTwo = new Lead(13, 25);
    Agent testAgentThree = new Agent(5, 20);
    Agent testAgentFour = new Agent(13, 56);

    ArrayList<Agent> testAgents = new ArrayList<>();
    testAgents.add(testAgentOne);
    testAgents.add(testAgentTwo);
    testAgents.add(testAgentThree);
    testAgents.add(testAgentFour);

    Team testTeam = new Team(3, testAgents);

    float expectedCurrentCenterX = (3F + 13F) / 2.0F;
    float expectedCurrentCenterY = (3F + 56F) / 2.0F;

    if (Math.abs(testTeam.getCenterX() - expectedCurrentCenterX) > 0.001 || Math.abs(
        testTeam.getCenterY() - expectedCurrentCenterY) > 0.001) {
      return false;
    }

    // Adds another agent to verify if the center x and y coordinates change as expected
    Agent testAgentFive = new Agent(33, 24);
    testTeam.addMember(testAgentFive);

    float expectedNewCenterX = (3F + 33F) / 2.0F;
    float expectedNewCenterY = (3F + 56F) / 2.0F;

    if (Math.abs(testTeam.getCenterX() - expectedNewCenterX) > 0.001 || Math.abs(
        testTeam.getCenterY() - expectedNewCenterY) > 0.001) {
      return false;
    }

    return true;
  }

  /**
   * Runs all tests and displays results
   *
   * @param args unused
   */
  public static void main(String[] args) {
    System.out.println("-----------------------------------------------------------");
    System.out.println(
        "testAgentInitialPosition: " + (testAgentInitialPosition() ? "Pass" : "Failed!"));
    System.out.println("-----------------------------------------------------------");
    System.out.println("testAgentMovement: " + (testAgentMovement() ? "Pass" : "Failed!"));
    System.out.println("-----------------------------------------------------------");
    System.out.println("testAgentStationary: " + (testAgentStationary() ? "Pass" : "Failed!"));
    System.out.println("-----------------------------------------------------------");
    System.out.println(
        "testMultipleLeadsException: " + (testMultipleLeadsException() ? "Pass" : "Failed!"));
    System.out.println("-----------------------------------------------------------");
    System.out.println("testEmptyTeam: " + (testEmptyTeam() ? "Pass" : "Failed!"));
    System.out.println("-----------------------------------------------------------");
    System.out.println("testValidTeamCreation: " + (testValidTeamCreation() ? "Pass" : "Failed!"));
    System.out.println("-----------------------------------------------------------");
    System.out.println("testAddAgentToTeam: " + (testAddAgentToTeam() ? "Pass" : "Failed!"));
    System.out.println("-----------------------------------------------------------");
    System.out.println("testTeamCenter: " + (testTeamCenter() ? "Pass" : "Failed!"));
    System.out.println("-----------------------------------------------------------");
  }

}
