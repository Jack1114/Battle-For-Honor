/**
 * 
 */
package controller.playerAttack;

import controller.globalGenerator.GlobalGenerator;
import model.enemies.Enemy;
import model.player.Player;
import model.player.PlayerImpl;

public class PlayerAttackImpl implements PlayerAttack {

	private int DEFAULT_ATTACK_POINTS =3; 
	private int attackPoints = DEFAULT_ATTACK_POINTS;

	private GlobalGenerator gg = GlobalGenerator.getInstance();
	Player player;

	public PlayerAttackImpl( Player player) {
		this.player=player;
	}

	public int getAttackPoints() {
		return attackPoints;
	}
	
	private void resetAttackPoints() {
		this.attackPoints = DEFAULT_ATTACK_POINTS;
	}
	public void attack(Enemy enemy) {
		System.out.println("Hero is attacking");
		enemy.GetHit(getAttackPoints());
		resetAttackPoints();	
	}

	public void setAttackPoints(int newAttackPoints) {
		this.attackPoints = newAttackPoints;
	}
	 public void increaseAtt() {
		 setAttackPoints(this.DEFAULT_ATTACK_POINTS+=3);
		 
	 }

	public void getHit(int enemyID,int enemyResponseHit) {
		gg.player.getLife().setLifePoints(gg.player.getLife().getLifePoints()-enemyResponseHit);
		if(gg.player.getLife().getLifePoints()<=0) {   //se il player non ha pi� vita,il gplayer muore e il turno � finito
			System.out.println("L'eroe � morto!");
			System.exit(0);
		}
	}
}