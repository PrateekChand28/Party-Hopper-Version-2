import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import processing.core.PApplet;

///////////////////////////////////////////////////////////////////////////////////////////////////


/**
 * Class methods to create and interact with the GUI and manage different behaviors of Teams and
 * agents within it
 *
 * @author Prateek Chand
 */
public class TeamManagementSystem extends PApplet {

  // General data fields for this program
  private Random randGen;               // A random number generator for creating Team colors
  private ArrayList<Clickable> objects; // Storage for the interactive components of the program
  private ArrayList<Team> teams;        // Storage for all Teams with at least one member
  private int bgColor;                  // The background color of the application window

  // Selection-related fields:
  private boolean isSelecting; // Indicates whether the user is currently creating a selection box
  private int selectionStartX; // The x-coordinate where the user began creating a selection box
  private int selectionStartY; // The y-coordinate where the user began creating a selection box

  /**
   * Main Method of the Team Party Hopping App were everything begins
   *
   * @return
   */
  public static void main(String[] args) {
    PApplet.main("TeamManagementSystem"); // PROVIDED
  }

  /**
   * Method to set the size of the GUI screen popup window
   */
  @Override
  public void settings() {
    // calls PApplet's size() method giving it 800 as the width and 600 as the height
    size(800, 600);
  }

  /**
   * Method to set up the base initialization for visual representation of environment elements
   * including background, agent and party
   */
  @Override
  public void setup() {
    // adds setProcessing calls
    Agent.setProcessing(this);
    Party.setProcessing(this);


    // sets the imageMode so the x,y coordinates indicate the center of an object
    imageMode(CENTER);

    // initializes randGen and the ArrayLists
    randGen = new Random();
    objects = new ArrayList<>();
    teams = new ArrayList<>();

    // initializes the bgColor with R = 81, G = 125, B = 168
    bgColor = this.color(81, 125, 168);

    // adds the party objects

    objects.add(new Party(200, 125, this.loadImage("images" + File.separator + "cup.png")));
    objects.add(new Party(600, 150, this.loadImage("images" + File.separator + "dice.png")));
    objects.add(new Party(400, 450, this.loadImage("images" + File.separator + "ball.png")));


    // adds one agent at the center of the screen
    int centerX = this.width / 2;
    int centerY = this.height / 2;

    objects.add(new Agent(centerX, centerY));

  }

  /**
   * Method to draw background, selection box, clickable objects and their respective Team
   * representations.The method also calls the clearEmptyTeams() method to clears all empty teams
   * from the team list
   */
  @Override
  public void draw() {
    // draws the background using the bgColor value
    this.background(bgColor);

    // draws the selection box if the user is currently selecting (see helper method below)
    if (this.isSelecting) {
      this.drawSelectionBox();
    }

    // Draws all Clickables in the objects list to the application window in the order they appear
    for (Clickable currentObject : this.objects) {
      currentObject.draw();
    }

    this.clearEmptyTeams();

    // (1) begin with a y-coordinate of 20 and a text size of 16
    float yCoordinate = 20.0F;
    this.textSize(16);

    // (2) set PApplet's fill to (0,255,0) if the team is active, or just (255) if it is not
    for (Team currentTeam : teams) {
      if (currentTeam.isActive()) {
        this.fill(0, 255, 0);
      } else {
        this.fill(255);
      }
      // (3) print "Team " and the team's ID letter at x=10 and the current y-coordinate
      this.text("Team " + currentTeam.getTeamID(), 10, yCoordinate);
      // (4) move the y-coordinate down by 20 and repeat if there are any other teams
      yCoordinate += 20;
    }
  }

  /**
   * Method to remove all teams with No members from the team list
   */
  public void clearEmptyTeams() {

    ArrayList<Team> teamsToRemove = new ArrayList<>();

    for (Team currentTeam : this.teams) {
      if (currentTeam.getTeamSize() == 0) {
        teamsToRemove.add(currentTeam);
      }
    }

    // remove all teams with NO members from the teams list
    this.teams.removeAll(teamsToRemove);

  }

  /**
   * Method to draw user's selection box using the region between the coordinates of the initial
   * click position and the final release position of the mouse pointer.
   */
  public void drawSelectionBox() {

    // set PApplet's fill() to R=135, G=185, B=201
    this.fill(135, 185, 201);

    // calls PApplet's rect() method using the upper left corner coordinates and the width/height
    // CITE: understood how rect actually works - 2015 Deb Deppeler (Source in the File Header)
    this.rect(this.selectionStartX, this.selectionStartY,
        Math.abs(this.mouseX - this.selectionStartX), Math.abs(this.mouseY - this.selectionStartY));

    this.detectTeam();


  }

  /**
   * Method to handle different object specific behavior when the mouse is over that object
   *
   * CITE: TA Ruifeng Xu helped with debugging the logic error with the priority of clickable
   * objects
   */
  @Override
  public void mousePressed() {

    // Bad Implementation
    /*
    for (Clickable currentObject : objects) {
      if (currentObject.isMouseOver()) {
        currentObject.mousePressed();
        return;
      }
    }

     */

    // if the mouse is over any of the Clickable objects, calls only that object's mousePressed
    // method and ends the method
    for (int index = this.objects.size() - 1; index >= 0; index--) {
      if (this.objects.get(index).isMouseOver()) {
        this.objects.get(index).mousePressed();
        return;
      }
    }

    // if the mouse is NOT over any of the Clickable objects, sets isSelecting to true and
    // initialize the selectionStartX and selectionStartY values to the current mouse location
    this.isSelecting = true;
    this.selectionStartX = this.mouseX;
    this.selectionStartY = this.mouseY;

  }

  /**
   * Method to determine if a team should be created among agents within a selection box when the
   * mouse is released. If not, the method calls the corresponding mouseReleased() methods of the
   * objects in the Clickable object list
   */
  @Override
  public void mouseReleased() {

    // executes when the user is creating a selection box:
    if (this.isSelecting) {
      ArrayList<Agent> agentsInSelectionBox = this.getAllSelectedAgents();
      Team sameTeam = this.detectTeam();

      // determines whether all selected agents belong to a single team (see helper method below)
      if (sameTeam == null && !agentsInSelectionBox.isEmpty()) {
        // creates a team out of the selected agents (see helper method below)
        this.createTeam(agentsInSelectionBox);

      }
      this.isSelecting = false;
    }

    // clears any empty teams (see helper method above)
    this.clearEmptyTeams();

    // calls mouseReleased() on all Clickable objects
    for (Clickable currentObject : this.objects) {
      currentObject.mouseReleased();
    }
  }

  /**
   * Method to find all agents within the selection box. This method is only called when creating a
   * selection box.
   *
   * @return reference of the team when selected agents are in the same team; otherwise null
   */
  public Team detectTeam() {
    // finds all agents within the selection box (this method will only be called when the user
    // was creating a selection box -- see helper method below)
    ArrayList<Agent> agentsInTheSelectionBox = this.getAllSelectedAgents();

    // if no agents were selected, returns null
    if (agentsInTheSelectionBox == null || agentsInTheSelectionBox.isEmpty()) {
      return null;
    }

    // if all selected agents are on the same (non-null) team, returns a reference to that team,
    // otherwise return null
    for (int index = 1; index < agentsInTheSelectionBox.size(); ++index) {
      if (agentsInTheSelectionBox.get(0).getTeam() != agentsInTheSelectionBox.get(index)
          .getTeam()) {
        return null;
      }
    }

    return agentsInTheSelectionBox.get(0).getTeam();
  }

  /**
   * Method to verify which of the agents' center coordinates on the screen are bounded by the
   * selection box created by the user at a particular instance
   *
   * @return Arraylist of agents, if any, that are bounded by a specific instance of a selection box
   */
  public ArrayList<Agent> getAllSelectedAgents() {
    // CITE: TA Yiheng Su helped with understanding the math behind the bounds of the selection box
    // finds the bounds of the selection box described by the selectionStart coordinates and
    // the current mouse location
    float leftMostBound = Math.min(this.selectionStartX, this.mouseX);
    float rightMostBound = Math.max(this.selectionStartX, this.mouseX);
    float topMostBound = Math.max(this.selectionStartY, this.mouseY);
    float bottomMostBound = Math.min(this.selectionStartY, this.mouseY);


    // adds to this list all agents whose center (x,y) coordinate is within the bounds of the
    // selection box:
    ArrayList<Agent> agents = new ArrayList<>();

    // Checks for agent within the selected box
    for (Clickable currentObject : objects) {
      if (currentObject instanceof Agent agentToCheck) {
        if (agentToCheck.getX() >= leftMostBound && agentToCheck.getX() <= rightMostBound) {
          if (agentToCheck.getY() <= topMostBound && agentToCheck.getY() >= bottomMostBound) {
            agents.add(agentToCheck);
          }
        }
      }
    }

    // CITE: Prof Hobbes told to use print statement here to check the outcome of the logic
    return agents;
  }

  /**
   * Method to create a new team using the selected agents and assign each team with a random
   * color. If a team is successfully created, it gets added into the list of teams
   */
  public void createTeam(ArrayList<Agent> selected) {
    // if no agents were selected, end the method
    if (selected.isEmpty()) {
      return;
    }
    // generates a random color for this team with R, G, and B values between 0 and 255
    int red = randGen.nextInt(0, 226);
    int green = randGen.nextInt(0, 226);
    int blue = randGen.nextInt(0, 226);

    // attempts to create a new team using the selected agents and this color
    try {
      Team newTeam = new Team(this.color(red, green, blue), selected);

      // if the team is created successfully, adds it to the teams list; otherwise does nothing
      this.teams.add(newTeam);

    } catch (Exception e) {
      // Do nothing
    }
  }

  /**
   * Method to handle specific input commands from the keyboard, where each key corresponds to a
   * particular action. Commands Used: '.' - add a normal agent at the mouse's current location ','
   * - add a team lead at the mouse's current location 'r' and the mouse is over an agent - remove
   * that agent lower-case version of the existing Team IDs - have that Team's members line up
   */
  @Override
  public void keyPressed() {

    // if the key is a '.', adds a normal agent at the mouse's current location
    if (this.key == '.') {
      Agent normalAgent = new Agent(this.mouseX, this.mouseY);
      objects.add(normalAgent);
    }

    // if the key is a ',', adds a team lead at the mouse's current location
    if (this.key == ',') {
      Lead teamLead = new Lead(this.mouseX, this.mouseY);
      objects.add(teamLead);
    }

    // if the key is an 'r' and the mouse is over an agent, remove that agent
    if (this.key == 'r') {

      // Checks if the mouse is over any of the clickable object
      for (Clickable currentObject : this.objects) {

        // Checks if the clickable object is an Agent
        if (currentObject instanceof Agent) {

          // Checks if the mouse is over this Agent
          if (currentObject.isMouseOver()) {
            Agent agentToRemove = (Agent) currentObject;

            // Removes the agent from the Clickable objects list
            objects.remove(agentToRemove);

            // Removes the agent from  its current team
            if (agentToRemove.getTeam() != null) {
              agentToRemove.getTeam().removeMember(agentToRemove);
            }
            break;
          }
        }
      }
    }

    // if the key is the lower-case version of the existing Team IDs, Team's members line up
    for (Team currentTeam : teams) {
      if (Character.toLowerCase(currentTeam.getTeamID()) == Character.toLowerCase(this.key)) {
        currentTeam.lineUp();
      }
    }
  }

  /**
   * Method to find the first team in the teams list with all active members
   *
   * @return returns this active team; otherwise null if no team has all active members
   */
  public Team getActiveTeam() {

    for (Team firstTeamWithAllActiveMembers : this.teams) {
      // find the first team in the teams list with all members active and return it
      if (firstTeamWithAllActiveMembers.isActive()) {
        return firstTeamWithAllActiveMembers;
      }
    }
    // if no team has all members active, return null
    return null;
  }

}
