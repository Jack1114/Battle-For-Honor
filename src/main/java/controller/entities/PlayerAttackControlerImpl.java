/**
 * 
 */
package controller.entities;

import battleforhonor.Global_Generator;
import javafx.util.Pair;

/**
 * @author Olivia
 *
 */

public class PlayerAttackControlerImpl implements PlayerAttackController {
	private static final int ATTACK_POINTS = 5; 
	PlayerControllerImpl player;

	public PlayerAttackControlerImpl( PlayerControllerImpl new_player) {
		this.player=new_player;
	}
	/** @return attack_ponits
	 * 
	 */

	public int getAttackPoints() {
		return ATTACK_POINTS;
	}
	/**
	 * attack beetwen the player and the enemie
	 */
	public void attack(controller.entities.Pair<Integer, Integer> new_heropos2) {
		Global_Generator.enemyposwithID.forEach(item->{
		if(item.getY().getX()==player.getPlayerPosition().getX() && item.getY().getY()==player.getPlayerPosition().getY());
		  	{
		  	Global_Generator.enemies.get(item.getX()).GetHit(getAttackPoints());
		  	}
		});
	}
   
	
	/**
	 * after an attack from the enemie, the playerlost some health points
	 */
	public void getHit(int enemy_atk, int enemyID) { 
		player.life.setLifePoints(player.life.getLifePoints()-getAttackPoints()); //considero che ennemy_attack e hero_attack valgono la stessa cosa= attackPoints
		if(player.life.getLifePoints()<=0) {   //se il player non ha pi� vita,il gplayer muore e il turno � finito
			System.out.println("L' eroe � morto !! ");
			System.exit(0);
		}
		
	}







}
