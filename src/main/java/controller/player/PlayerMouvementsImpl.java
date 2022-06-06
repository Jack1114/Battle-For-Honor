/**
 * 
 */
package controller.player;

import java.util.Optional;

import controller.globalGenerator.Global_Generator;
import controller.obstacles.Obstacle;
import controller.obstacles.ObstacleImpl;
import model.player.Pair;
import model.player.PlayerImpl;

/**
 * @author Olivia
 *
 */
public class PlayerMouvementsImpl implements PlayerMouvement {

	private Pair<Integer,Integer> new_player_pos;
	private final PlayerImpl player;
	private Global_Generator gg = Global_Generator.getInstance();
	
	public PlayerMouvementsImpl(PlayerImpl newPlayer) {
		this.player = newPlayer;
	}
	
	
	/**
	 * the player move to the left
	 */

	public void left() {
		//dopo aver saltato un ostacolo di tipo POOL, il player dovrebbe andare alla colona successiva a quella con l'ostacolo POOl
			new_player_pos=new Pair<>(player.getPlayerPosition().getX()-1,player.getPlayerPosition().getY());

			if(check_advancement(new_player_pos)) {
				move(new_player_pos);
			}
			else
				stop();
			
	}

	/**
	 * the player move to the right
	 */

	public void right() {
		new_player_pos=new Pair<>(player.getPlayerPosition().getX()+1,player.getPlayerPosition().getY());
		if(check_advancement(new_player_pos)) {
			move(new_player_pos);
		}
		else
			stop();
	
	}

	/**
	 * the player move to down
	 */
	public void down() {
		new_player_pos=new Pair<>(player.getPlayerPosition().getX(),player.getPlayerPosition().getY()+1);
	
		if(check_advancement(new_player_pos)) {
			move(new_player_pos);
			
		}
		else
			stop();
		
	}

	/**
	 * the player move to up
	 */

	public void up() {
		new_player_pos=new Pair<>(player.getPlayerPosition().getX(),player.getPlayerPosition().getY()-1);
		if(check_advancement(new_player_pos)) {
		}
		else
			stop();
		
	}

	
	/**
	 * the player can not mot because of an obstacle or something else
	 */
	public void stop() {
		new_player_pos=new Pair<>(player.getPlayerPosition().getX(),player.getPlayerPosition().getY());	
	}
	
	
	private void move(Pair<Integer, Integer> newPos) {
		
			player.setPlayerPosition(newPos);
	}

	
	/**
	 * @return 
	 * @return true or false
	 * before moving, the player check if there is an ennemie and if the is an obstacle 
	 * if there is an obstacle, he check if the obstacle's type is the one that can be cross
	 */
	
	public boolean check_advancement(Pair<Integer, Integer> new_player_pos) {
		// verifico se tra la lista degli ostacoli, c'� un ostacolo dove il player vuole spostarsi
		//questa funzione non va bene, devo prendere il tipo di ostacolo che e' in quella posizione
		//filtro la lista finche' non trovo l'ostacolo in quella pos e poi gaccio getType di quell'ostacolo
		Optional<Obstacle> type = gg.obstacles
				.stream()
				.filter(o -> o.getObstaclePos().equals(new_player_pos))
				.findFirst();
		if (type.isPresent()) {
			switch(type.get().getObstacleType()) {
				case POOL:
					player.getPlayer_action().removeAction();
					return true;
				case ROCK:
					player.getLife().setLifePoints(player.getLife().getLifePoints()-2);
					return false;
			}
		}

		// controllo se va fuori dai bordi schermo
		if( (new_player_pos.getX()<0 || new_player_pos.getX()>gg.GRID_SIZE-1) || new_player_pos.getY()<0 || new_player_pos.getY()>gg.GRID_SIZE-1 ){
			System.out.println("vado fuori dai bordi !!!!!!!");
			return false;
		}

		//controllo se nella newPos ho un nemico e nel caso lo attacco
		//problema risolvibile con un for normale
		gg.enemyposwithID.forEach(item->{
			//TODO: cambiare la pos e mettere new_player
		     if(item.getY().getX()== new_player_pos.getX() && item.getY().getX() == new_player_pos.getY())
			 {
			     PlayerAttackImpl playerAttackImpl = new PlayerAttackImpl(player);
			     playerAttackImpl.attack(new_player_pos);
			 }
		  });
		return false;
	}


}
