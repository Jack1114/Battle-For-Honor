/**
 * 
 */
package controller.player;

import model.player.Pair;

/**
 * @author Olivia
 *
 */
public interface PlayerMovement {
	void left();
	
	void right();
	
	void down();
	
	void up();
	
	void stop();
	
	boolean check_advancement(Pair<Integer, Integer> player_pos);
}