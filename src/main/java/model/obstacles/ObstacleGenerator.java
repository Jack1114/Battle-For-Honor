package model.obstacles;

import java.util.List;
import java.util.Random;

import model.enemies.Global_Generator;
import model.player.Pair;

public class ObstacleGenerator {

	private static final int NUM_FENCE_OBST = 10;
	private static final int NUM_ROCK_OBST = 10;
	private static final int GRID_SIZE = 15;
    private final List<Obstacle> obstacles;
    private Global_Generator GG; // l'ho aggiunto per importare la funzione randPosition

    public ObstacleGenerator(List<Obstacle> obstacles){
    	this.obstacles = obstacles;
    }

	/**
	 * Generate n Obstacles in random position
	 */
	public void generateObstacles(){
		for (int i = 0; i < NUM_ROCK_OBST; i++){
			addObstacle(createObstacle(GG.randPosition(GRID_SIZE), ObstacleImpl.Type.ROCK));
		}
		for (int i = 0; i < NUM_FENCE_OBST; i++){
			addObstacle(createObstacle(GG.randPosition(GRID_SIZE), ObstacleImpl.Type.POOL));
		}
	}
	
	private void addObstacle(Obstacle obstacle){
		obstacles.add(obstacle);
	}

	private Obstacle createObstacle(final Pair<Integer, Integer> pos, final ObstacleImpl.Type type){
		return new ObstacleImpl(pos, type);
	}
	
}