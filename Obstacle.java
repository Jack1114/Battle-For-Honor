import java.util.Random;

public class Obstacle {

	private final Pair<Integer, Integer> obstaclePos;
	private final int x;
	private final int y;
	enum Type{
		ROCK,
		MUD;
	}

	private final Type obstacleType;
	
	public Obstacle(final Pair<Integer, Integer> pos, Type type){
		this.obstaclePos = pos;
		this.obstacleType = type;
	}

	public Pair<Integer, Integer> getObstaclePos(final Pair<Integer, Integer> pos){

	}

/*	private void generate_obst(int sizeArena) {
		// TODO Auto-generated method stub
		x=r.nextInt(sizeArena);
		y=r.nextInt(sizeArena);
		Pair<Integer,Integer> obst_pos = new Pair<>(x,y);
		if(!Global_Generator.obstacles_pos.contains(obst_pos) && ( obst_pos.getX()!=Hero.getX() && obst_pos.getY()!=Hero.getY()) ) {
			Global_Generator.obstacles_pos.add(obst_pos);
			success=true;
			System.out.println("generated obstacle in pos X = "+obst_pos.getX()+" Y = "+obst_pos.getY());
		}
	}
*/
	


}