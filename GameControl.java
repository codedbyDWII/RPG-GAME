package rpg;

/**
 * This is a simplified version of a role-playing game.
 */
public class GameControl {

  /**
   * Creates a human player to play the game.
   */
  HumanPlayer human = new HumanPlayer();

  /**
   * Creates a computer player to play the game.
   */
  ComputerPlayer computer = new ComputerPlayer();

  /**
   * Prints the game's context and rules.
   * Note: This method does not take any parameters and does not return anything.
   */
  public void printInstructions(){
    System.out.println();
    System.out.println("Welcome to the final battle against enemy forces. You will be facing off against the computer.");
    System.out.println("Each of you will have 3 units with randomly generated jobs and levels.");
    System.out.println("The jobs are: mage, knight, and archer. Archers are strong against mages, but weak against knights.");
    System.out.println("Mages are strong against knights, but weak against archers. Knights are strong against archers, but weak against mages.");
    System.out.println("There are two moves: attack (deal damage to one target) and block (temporarily increase defense).");
    System.out.println("Combat is turn based; all your love units will take a turn and then all the computer's live units will take a turn.");
    System.out.println("You have 10 turns to defeat the computer. If both players still have units standing, you only win ");
    System.out.println("if the combined HP of your units exceeds the computer's.");
    System.out.println();
  }
  
  /**
   * Prints the current status of all human units and all computer units.
   * Note: This method does not take any parameters and does not return anything.
   */
  public void printStatus(){
    System.out.println();
    System.out.println("Your units:");
    this.human.getFalia().printCurrentStatus();
    this.human.getErom().printCurrentStatus();
    this.human.getAma().printCurrentStatus();
    System.out.println();
    System.out.println("Computer units:");
    this.computer.getCriati().printCurrentStatus();
    this.computer.getLedde().printCurrentStatus();
    this.computer.getTyllion().printCurrentStatus();
    System.out.println();
  }

  /**
   * Takes the human player's turn by calling moveUnit on each of the human player's three units: Falia, Erom, and Ama.
   * Prints the unit's job and level before moving it. Checks if there is no winner before proceeding to the next move.
   * If there is a winner between the first and second unit's turn or between the second and third unit's turn,
   * then return out of the method to end the human turn.
   * Resets any computer temporary defense after all human units have made their move.
   * Note: This method does not return anything.
   * @param turn int representing the current turn that the game is on.
   */
  public void takeHumanTurn(int turn){

   System.out.println();
   System.out.println("=== HUMAN TURN " + turn + " ===");
   System.out.println();

   //FALIA'S TURN
   System.out.println("Falia (" + human.getFalia().getJob() + " - Level " + human.getFalia().getLevel() + ")");
   human.moveUnit (human.getFalia(), computer);
   if (getWinner(turn) != null) return;
   
   System.out.println();
   
   //EROM'S TURN
   System.out.println("Erom (" + human.getErom().getJob() + " - Level " + human.getErom().getLevel() + ")");
   human.moveUnit (human.getErom(), computer);
   if (getWinner(turn) != null) return;

   System.out.println();

   //AMA TURN
   System.out.println("Ama (" + human.getAma().getJob() + " - Level " + human.getAma().getLevel() + ")");
   human.moveUnit (human.getAma(), computer);

   //Reset computer's temp defenses
   computer.resetTemporaryDefense();
  
   
   System.out.println();
  }

  /**
   * Takes the computer player's turn and resets any human temporary defense after the computer has made its moves.
   * Note: This method does not take any parameters and does not return anything.
   */
  public void takeComputerTurn(){

	  System.out.println();
	  System.out.println("=== COMPUTER TURN ===");
	  System.out.println();
	  
	  // Computer Strategy
	  computer.strategy(human.getFalia(), human.getErom(), human.getAma());

	  //Reset human's temporary defenses
	  human.resetTemporaryDefense();
	  
	  System.out.println();
  }

  /**
   * Gets the winner of the game based on the turn parameter and whether one of the players has been knocked out.
   * If the turn is less than 10, return null if both players are alive, otherwise return the winner if the opposing player is knocked out.
   * If both players still have living units after 10 turns, then the player with the greatest sum of HP wins, otherwise it is a tie.
   * @param turn int representing the current turn that the game is on.
   * @return String representing who won the game ("human" or "computer") or "tie" if there is a tie.
   * Return null if both players are still alive and the current turn is less than 10.
   */
  public String getWinner(int turn){

	if (turn < 10) {
		boolean computerDefeated = 
			computer.getCriati().getHp() <= 0 &&
			computer.getLedde().getHp() <= 0 &&
			computer.getTyllion().getHp() <= 0;
		
		boolean humanDefeated =
				human.getFalia().getHp() <= 0 &&
				human.getErom().getHp() <= 0 &&
				human.getAma().getHp() <= 0;
		
		if (computerDefeated) return "human";
		if (humanDefeated) return "computer";
		return null;
	}
	
	int humanHP = human.getFalia().getHp()
			+ human.getErom().getHp()
			+ human.getAma().getHp();
	int computerHP = computer.getCriati().getHp()
			+ computer.getLedde().getHp()
			+ computer.getTyllion().getHp();
	
	if (humanHP > computerHP) return "human";
	if (computerHP > humanHP) return "computer";
	return "tie";
  }

  /**
   * Creates an instance of GameControl and contains the flow of this role-playing game.
   * Note: This method does not return anything.
   * @param args Not used.
   */
  public static void main(String[] args){

  /*
   * Creating a GameControl object and printing the game instructions
   */
	 
	 //Creating the GameControl Object
	 GameControl game = new GameControl();
	 
	 //Printing the game instructions
	 game.printInstructions();
	 
	 String winner = null;
	 
	 //Track if someone wins before hitting 10 turns
	 boolean someoneWon = false;
	 
	 int turn = 1;
	 
	 //Running the loop for up to 10 turns, or until a winner is found 
	 while (turn <= 10 && !someoneWon) {
		 
		 //Showing Number of Current Turn
		 System.out.println("TURN " + turn);
		 
		 game.printStatus();
		 
		 //Human's Turn
		 game.takeHumanTurn(turn);
		 
		 //Checking if there is a winner after the human's turn
		 winner = game.getWinner(turn);
		 if (winner != null) {
			 someoneWon = true;
			 break;
		 }
		 
		 game.printStatus();

		//Computer's Turn

		 game.takeComputerTurn();
		 
		 //Checking if there is a winner after the computers turn
		 winner = game.getWinner(turn);
		 if (winner != null) {
			 someoneWon = true; //If someone wins we exit the loop
			 break;
		 }
		 
		 //Moving to next turn
		 turn++;
	 }
	 
	 // After 10 turns checking for winner or if loop ends
	 if(winner == null) {
		 winner = game.getWinner(turn);
	 }
	 
	 // End result of the game
	 if ("human".equals(winner)) {
		 System.out.println("You've defeated the enemy!");
	 } else if ("computer".equals(winner)) {
		 System.out.println("All your heroes have been defeated, enemy forces have won!");
	 } else if ("tie".equals(winner)) {
		 System.out.println("Nobody wins!");
	 }
 
  }
}