//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title:    Team Class for Team Party Hopping GUI Interface
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
// Persons:
// - TA Yiheng Su helped debug a logic error with the way my id generator was behaving.
// - TA Ruifeng Xu helped me understand the functionality of selectALL() method and how it is
//   linked with the Lead Class's mouseReleased() method. He also helped me debug logic error with
//   the lineUp method and helped me understand the dimensions of the agents in a team
//
// Online Sources:
// - https://canvas.wisc.edu/courses/447785/files/44550502?wrap=1
// - https://canvas.wisc.edu/courses/447785/quizzes/589093?module_item_id=7928682
//
///////////////////////////////////////////////////////////////////////////////

import java.util.ArrayList;

/**
 * Models a Team for the CS300 P05 Team Party Hopping project
 *
 * @author Prateek Chand
 */
public class Team {

  // A shared variable containing the identifier character to be used by the next Team that is
  // successfully created; initialized to 'A'
  private static char idGenerator = 'A';

  // A list of the current members of this Team
  private ArrayList<Agent> members;

  // This Team's unique identifier, set at construction
  private final char TEAM_ID;

  // The color in which this team's members are drawn in the application window when not active
  private int color;


  /**
   * Attempts to create a new team from the provided list of agents, advancing the idGenerator to
   * the next value only if the team can be created successfully
   *
   * @param color  - the color for this team's agents
   * @param agents - a list of the agents to be added to this team
   */
  public Team(int color, ArrayList<Agent> agents) {
    if (agents == null || agents.isEmpty()) {
      throw new IllegalArgumentException();
    }

    int totalLeads = 0;
    for (Agent currentAgent : agents) {
      if (currentAgent instanceof Lead) {
        totalLeads++;
      }
    }

    if (totalLeads > 1) {
      throw new IllegalStateException();
    }

    this.color = color;
    this.members = new ArrayList<>(agents);

    for (Agent currentAgent : this.members) {
      currentAgent.setTeam(this);
    }

    // CITE: TA Yiheng Su helped with debugging the constructor's ID generation logic error
    this.TEAM_ID = idGenerator++;
  }

  /**
   * Accessor method for the color value of this team
   *
   * @return the color value of this team
   */
  public int getColor() {

    return this.color;
  }

  /**
   * Accessor method for the team's ID character
   *
   * @return the team ID value
   */
  public char getTeamID() {

    return this.TEAM_ID;
  }

  /**
   * Accessor method for the total number of agents on this team
   *
   * @return the size of this team
   */
  public int getTeamSize() {

    return this.members.size();
  }

  /**
   * Reports whether this team currently has a Lead member
   *
   * @return true if this team currently has a Lead member, false otherwise
   */
  public boolean hasLead() {
    boolean containsLead = false;

    // Check each member until lead is found
    for (Agent currentAgent : this.members) {
      if (currentAgent instanceof Lead) {
        containsLead = true;
        break;
      }
    }

    return containsLead;
  }

  /**
   * Adds the given agent to this team's list. If the agent is already present in this team's
   * members list, this method does nothing
   *
   * @param a - the agent to add to the list
   * @throws IllegalStateException - if the agent is a Lead that is not already present in the list
   */
  public void addMember(Agent a) throws IllegalStateException {

    if (a instanceof Lead && this.hasLead()) {
      throw new IllegalStateException();
    }

    if (!this.members.contains(a)) {
      a.setTeam(this);
      this.members.add(a);
    }
  }

  /**
   * Removes the provided agent from this team
   *
   * @param a - the agent to remove
   * @return true if the agent was removed from the team successfully, false otherwise
   */
  public boolean removeMember(Agent a) {

    return members.remove(a);

  }

  /**
   * Accessor to determine whether a given agent is a member of this team
   *
   * @param a - the agent that may be a member of this team
   * @return true if this agent is in the members list, false otherwise
   */
  public boolean contains(Agent a) {

    return members.contains(a);

  }

  /**
   * Activates ALL members of this team
   */
  public void selectAll() {

    // CITE: TA Ruifeng Xu helped with understanding how to handle clicks on different members
    // of a team
    for (Agent currentAgent : this.members) {
      if (!currentAgent.isActive()) {
        currentAgent.toggleActive();
      }


    }
  }

  /**
   * Checks whether All members of a team have been selected
   *
   * @return true if ALL members of this team are active, false otherwise
   */
  public boolean isActive() {
    boolean allActive = true;
    for (Agent currentAgent : this.members) {
      if (!currentAgent.isActive()) {
        allActive = false;
      }
    }
    return allActive;
  }

  /**
   * Finds the "center" x-coordinate of this team, defined as being halfway between the leftmost and
   * rightmost agents on this team
   *
   * @return - the center x-coordinate of this team
   */
  public float getCenterX() {

    // When there is only one agent
    if (this.getTeamSize() == 1) {
      for (Agent currentAgent : this.members) {
        if (currentAgent != null) {
          return currentAgent.getX();
        }
      }
    }

    // When there are multiple agents
    float leftMostXCoordinate = this.members.get(0).getX();
    float rightMostXCoordinate = this.members.get(0).getX();

    for (Agent currentAgent : this.members) {

      if (currentAgent.getX() < leftMostXCoordinate) {
        leftMostXCoordinate = currentAgent.getX();
      }
      if (currentAgent.getX() > rightMostXCoordinate) {
        rightMostXCoordinate = currentAgent.getX();
      }
    }

    return ((leftMostXCoordinate + rightMostXCoordinate) / 2F);

  }

  /**
   * Finds the "center" y-coordinate of this team, defined as being halfway between the topmost and
   * bottommost agents on this team
   *
   * @return - the center y-coordinate of this team
   */
  public float getCenterY() {

    // When there is only one agent
    if (this.getTeamSize() == 1) {
      for (Agent currentAgent : this.members) {
        if (currentAgent != null) {
          return currentAgent.getY();
        }
      }
    }

    // When there are multiple agents
    float topMostYCoordinate = this.members.get(0).getY();
    float bottomMostYCoordinate = this.members.get(0).getY();

    for (Agent currentAgent : this.members) {
      if (currentAgent.getY() > topMostYCoordinate) {
        topMostYCoordinate = currentAgent.getY();
      }
      if (currentAgent.getY() < bottomMostYCoordinate) {
        bottomMostYCoordinate = currentAgent.getY();
      }
    }
    return ((topMostYCoordinate + bottomMostYCoordinate) / 2F);
  }

  /**
   * Updates the destination of all team members so that the current team formation will be
   * maintained, but after movement is completed the team will be centered over the given Party
   *
   * @param p - the party to move the team to
   */
  public void sendToParty(Party p) {
    float currentCenterX = this.getCenterX();
    float currentCenterY = this.getCenterY();

    float movementAlongXAxis = p.getX() - currentCenterX;
    float movementAlongYAxis = p.getY() - currentCenterY;

    // Movement of each agent by the same unit along x and y-axes
    for (Agent currentAgent : this.members) {
      currentAgent.setDestination(currentAgent.getX() + movementAlongXAxis,
          currentAgent.getY() + movementAlongYAxis);
    }
  }

  /**
   * Updates the destination of all team members so that the team formation becomes a line centered
   * at getCenterX/getCenterY. Each member should be allocated their diameter + 3 pixels worth of
   * space in the line, to avoid overlapping with the agent next to them CITE: TA Ruifeng Xu helped
   * with debugging this method for proper length calculation Hint: the total width of this line is
   * equal to ((diameter + 3) * teamSize) - 3
   */
  public void lineUp() {
    float teamCenterX = this.getCenterX();
    float teamCenterY = this.getCenterY();

    float totalWidth = ((Agent.diameter() + 3) * this.members.size()) - 3;
    float xCoordinateOfAgent = (teamCenterX - (totalWidth / 2F)) + (Agent.diameter() / 2F);

    for (Agent currentAgent : this.members) {
      currentAgent.setDestination(xCoordinateOfAgent, teamCenterY);
      xCoordinateOfAgent += (Agent.diameter() + 3);
    }
  }
}
