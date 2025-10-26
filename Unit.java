package rpg;
import java.util.Random;

/**
 * Represents a unit that can belong to either the computer or human player.
 */
public class Unit {

  /**
   * The unit's name.
   */
  String name;
 
  /**
   * The unit's level, which is randomly generated based on a given level range.
   */
  private int level = 0;

  /**
   * The unit's job, such as mage, knight or archer.
   */
  private String job;

  /**
   * The unit's current health points or HP.
   */
  private int hp = 0;

  /**
   * The unit's attack stat, which is determined by level and impacts damage dealt to a target.
   */
  private int attack = 0;

  /**
   * The unit's defense stat, which is determined by level and impacts damage received by this unit.
   */
  private int defense = 0;

  /**
   * The unit's temporary defense stat, which is granted by the "block" move.
   */
  private int temporaryDefense = 0;

  /**
   * The unit's evasion stat, which is determined by level and impacts whether this unit dodges the incoming attack.
   */
  private int evasion = 0;

  /**
   * A random number generator to be used in this class.
   */
  Random random = new Random();

  /**
   * Constructs a unit by assigning the given name and job as well as calculating other stats.
   * The level must be randomly generated given the level range that is passed.
   * @param name String representing the name of this unit
   * @param levelRange String representing the(level range of this unit, such as low, medium or high
   * @param job String representing the job of this unit
   */
  public Unit(String name, String levelRange, String job){
	  this.name = name;
	  this.job = job;
	  
	  if (levelRange.equalsIgnoreCase("low")) {
		  this.level = random.nextInt(3) + 1; //1-3
	  	} else if (levelRange.equalsIgnoreCase("medium")) {
		  this.level =random.nextInt(3) + 4; //4-6
	  	} else if (levelRange.equalsIgnoreCase("high")) {
	  	  this.level = random.nextInt(4) + 7; //7-10
	  }

	  // Calculate stats using multiplier
	  double multiplier = this.level / 10.0;
	  
	  this.hp = (int) Math.round(100 * multiplier);
	  this.attack = (int) Math.round(20 * multiplier);
	  this.defense = (int) Math.round(20 * multiplier);
	  this.evasion = (int) Math.round(5 * multiplier);
  }

  // Getters and Setters

  /**
   * Returns this unit's level.
   * Note: This method does not take any parameters.
   * @return level
   */
  public int getLevel() {

	  return this.level;
  }

  /**
   * Returns this unit's job.
   * Note: This method does not take any parameters.
   * @return job
   */
  public String getJob() {
	  return this.job;
  }

  /**
   * Returns this unit's hp.
   * Note: This method does not take any parameters.
   * @return hp
   */
  public int getHp() {
	  return this.hp;
  }

  /**
   * Sets this unit's hp stat to the given hp.
   * Note: This method does not return anything.
   * @param hp int representing given health points value
   */
  public void setHp(int hp) {
	  this.hp = hp;
  }

  /**
   * Sets this unit's temporary defense stat to the given temporary defense.
   * Note: This method does not return anything.
   * @param temporaryDefense int representing given temporary defense value
   */
  public void setTemporaryDefense(int temporaryDefense) {
	  this.temporaryDefense = temporaryDefense;
  }

  /**
   * DO NOT MODIFY, REQUIRED FOR TESTING
   *
   * Returns this unit's temporary defense stat.
   * Note: This method does not take any parameters.
   * @return temporaryDefense
   */
  public int getTemporaryDefense() {
    return this.temporaryDefense;
  }

  /**
   * DO NOT MODIFY, REQUIRED FOR TESTING
   *
   * Sets this unit's evasion stat to the given evasion value.
   * Note: This method does not return anything.
   * @param evasion int representing given value for evasion
   */
  public void setEvasion(int evasion) {
    this.evasion = evasion;
  }

  /**
   * Prints the unit's name, level, job, and remaining HP.
   * If the unit has no remaining hp, prints that this unit is knocked out.
   * Note: This method does not take any parameters and does not return anything.
   */
  public void printCurrentStatus(){
	  if (this.hp < 1) {
		  System.out.println(this.name + " (" + this.job + ", Level " + this.level + ") is knocked out and cannot move.");
	  }  else {
		  System.out.println(this.name + " (" + this.job + ", Level " + this.level + ") has " + this.hp + " HP remaining.");
	  }
  }

  /**
   * Calculates damage based on this unit's attack stat, maximum attack, and attacker strength relative to target.
   * @param attackerStrength String representing the attacker's strength relative to the target
   * @return int representing the total damage this unit will deal when attacking
   */
  public int attack(String attackerStrength){
	  double multiplier = 1.0;
	  if (attackerStrength.equalsIgnoreCase("strong")) {
		  multiplier = 1.2;
	  } else if (attackerStrength.equalsIgnoreCase("weak")) {
		  multiplier = 0.5;
	  }
	  
	  double attackMax = 50.0;
	  double rawDamage = (this.attack / 30.0) * attackMax;
	  int damage = (int) Math.round(rawDamage * multiplier);
	  
	  return damage;
  }

  /**
   * Provides temporary defensive buff to reduce damage taken during the current turn.
   * Note: This method does not take any parameters and does not return anything.
   */
  public void block(){

    this.temporaryDefense += 2;
    System.out.println(this.name + " is blocking and gains +2 temporary defense!");
  }

  /**
   * Uses this unit's evasion, temporaryDefense, and defense stats to either dodge the attack or adjust damage.
   * If the attack is not dodged, applies the adjusted damage to this unit's remaining HP.
   * Prints a message containing the damage received and the remaining HP.
   * Note: This method does not return anything.
   * @param damage int representing the incoming damage from an opposing unit
   */
  public void receiveDamage(int damage){

    //Evasion check
	  
	 if (this.evasion > 0) {
		 int evasionCheck = random.nextInt(21); //0-20
		 if (evasionCheck <= this.evasion) {
			 System.out.println("They dodged!");
			 return;
		 }
	 }
    
	 // Calculate defense adjustment
	 float defenseAdjustment = (float) ((this.temporaryDefense + this.defense) / 10.0);
	 
	 // Calculate the actual damage recieved
	 int damageReceived = Math.round(damage / defenseAdjustment);
	 
	 // Update HP
	 this.hp -= damageReceived;
	 if (this.hp < 0) {
		 this.hp = 0;
	 }
	 
	 System.out.println(this.name + " received " + damageReceived + " damage and has " + this.hp + " HP remaining.");
  }
}