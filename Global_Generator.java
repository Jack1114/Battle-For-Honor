import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

// si occupa tutto lui di generare i nemici e le loro statistiche, L' eroe � statico per ora con una sola posizione fissa 

public class Global_Generator {
	private static final int NUM_FENCE_OBST = 10;
	private static final int NUM_ROCK_OBST = 10;
	public static final int GRID_SIZE=15;
	//public static List<Pair<Integer,Integer>> enemypos = new ArrayList<>();
						      // ID           POS
	public static List<Pair<Integer,Pair<Integer,Integer>>> enemyposwithID = new ArrayList<>();
	
	//public static List<Pair<Integer,Pair<Integer,Integer>>> NEWenemyposwithID = new ArrayList<>(); // alla fine ho risolto con solo quella principale 
	
	public static List<Pair<Integer,Integer>> obstacles_pos = new ArrayList<>(); 
	public static List<Obstacle> obstacles = new ArrrayList<>()
	
	public static List<Enemy> enemies= new ArrayList<Enemy>();

	public static List<Integer> skipenemy= new ArrayList<>(); //contiene gli ID dei vari nemici morti 
	
	int NUM_ENEMIES=3;

	private int totactions=0;
	GUI g = new GUI(15);

	//private int hero_actions=0;
	
	void generation() {
		
		Hero.spawn(15);
		
		generateObstacles();
		generate_enemies();

		g.update();
		//System.out.println("geneneration done ok !! "); // stampa per vedere se finisce il ciclo [ ok ]
		/*   //stampa per vedere le statistiche pseudo-randomiche [ ok ]
		
		for(int i=0;i<NUM_ENEMIES;i++) {
			var elem=enemyposwithID.get(i);
			System.out.println("----------[ "+i+" ]-----------");
			System.out.println("pos x = "+elem.getX()+" pos y = "+elem.getY());
			
			var elem2=enemies.get(i);
			System.out.println(elem2.toString());
		}
		*/

		int turn=0;
		int totactions=0;
		while(true && totactions<80) {
			System.out.println("---- turn "+turn+" ----");	
			System.out.println("skipped enemy size = "+skipenemy.size());
			
			if(skipenemy.size()==NUM_ENEMIES) {
				reset();
				recover_HERO();
				generateObstacles();
				generate_enemies();
			} 
			
			playerTurn();
			enemyTurn();
			
			turn++;
			totactions++;	
		}
		
	}


	private void reset() {
		// TODO Auto-generated method stub
		enemyposwithID = new ArrayList<>();
		enemies= new ArrayList<Enemy>();
		skipenemy=new ArrayList<Integer>();
		obstacles_pos = new ArrayList<>(); 
	}


	private void recover_HERO() {
		// TODO Auto-generated method stub
		System.out.println("Ti senti pi� forte di prima e le tue ferite sono state curate !!");
		Hero.SetHP(Hero.MAX_HP);
	}


	private void generate_enemies() {
		// TODO Auto-generated method stub
		for(int i=0; i<NUM_ENEMIES;i++) {
			//System.out.println("gen enemies n "+i);   //stampa per vedere se genera i nemici [ ok ]
			Enemy e = new Enemy(i);
			enemies.add(e);
		}
	}

/*
	private void genrate_obstacles() {
		// TODO Auto-generated method stub
		for (int i=0; i<NUM_OBST;i++) {
			Obstacles obst = new Obstacles(15);
		}
}*/

	/**
	 * Generate n Obstacles in random position
	 */
	private void generateObstacles(){
		for (int i = 0; i < NUM_ROCK_OBST; i++){
			ObstacleType type = ObstacleType.ROCK;
			addObstacle(createObstacle(randPosition(GRID_SIZE), type));
		}
		for (int i = 0; i < NUM_FENCE_OBST; i++){
			ObstacleType type = ObstacleType.MUD;
			addObstacle(createObstacle(randPosition(GRID_SIZE), type));
		}
	}

	private void Pair<X, Y> randPosition(final int sizeArena){
		Random r = new Random();
		boolean success = false;
		while(!success){
			int x = nextInt(sizeArena);
			int y = nextInt(sizeArena);
			Pair<Integer,Integer> pos = new Pair<>(x,y);
			if(checkObsPos(pos) && checkHeroPos(pos) && checkEnemyPos(pos)){
				success = true;
				return pos;
			}
		}
	}

	private void addObstacle(Obstacle obstacle){
		obstacles.add(obstacle);
	}

	//funzione per controllare la posizione degli ostacoli
	private boolean checkObsPos(Pair<Integer, Integer> pos){
		//contains o equals?
		return obstacles.obstaclePos.contains(pos);
	}

	private boolean checkHeroPos(Pair<Integer, Integer> pos){
		return enemyposwithID.contains(pos);
	}

	private boolean checkEnemyPos(Pair<Integer, Integer> pos){
		return pos.getX() != Enemy.get
	}

	private Obstacle createObstacle(final Pair<Integer, Integer> pos, final ObstacleType type){
		return new Obstacle(pos, type);
	}


	private void playerTurn() {
		// TODO Auto-generated method stub
		//va bene qualsiasi cosa � solo per creare dello stacco tra player e nemico 
		//hero_actions = 0;
		while(Hero.getActions > 0) {
			Hero.removeAction();
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	        System.out.println("Enter Input : w=UP // s=DOWN // a=LEFT // d=RIGHT ");
	        try {
	            String s = br.readLine();
	            Pair<Integer,Integer> heropos= new Pair<>(Hero.getX(),Hero.getY());
	            switch(s) {
	            	case("w"):
	            		HeroMovement.up(heropos);
	            		g.update();
	            	break;
	            	case("s"):
	            		HeroMovement.down(heropos);
	            		g.update();
	            	break;
	            	case("a"):
	            		HeroMovement.left(heropos);
	            		g.update();
	            	break;
	            	case("d"):
	            		HeroMovement.right(heropos);
	            		g.update();
	            	break;
	            	default:
	            		HeroMovement.stop(heropos);
	            		g.update();
	            	break;
	            }
	            	
	            //System.out.println(s);
			}catch(Exception e) {
	            System.out.println(e);
	        }
		}
		Hero.resetActions();
	}


	private void enemyTurn() {
		// TODO Auto-generated method stub
		for(var item:enemyposwithID) {
			if(skipenemy.contains(item.getX())) {
				// � bruttissimo ma quando muoiono vengono teletrasportati ad una posizione fuore schermo a (99,99)
				// e vengono pure disattivati quindi non agiranno MAI PIU' !! 
				enemyposwithID.set(item.getX(), new Pair<>(item.getX(),new Pair<Integer,Integer>(99,99)));
			}else {
				int id= item.getX();
					 // stampa del turno (si ripete per ogni nemico )
				int actionpt=0;
				
				while (actionpt<2) {
					advance(item,actionpt);	
					item=enemyposwithID.get(id);
					actionpt++;
				}
			}	
		}
	}


	@SuppressWarnings("deprecation")
	private void advance(Pair<Integer, Pair<Integer, Integer>> item, int actionpt) {
		Enemy_move_control.nextMove(item,actionpt);
		
		//---- wait in between actions ---
		long end = System.currentTimeMillis()+300;
		while (end>System.currentTimeMillis()) {
			//System.out.println("Waiting ...");
			if(System.currentTimeMillis()>end) {
				break;
			}
		}
		//---------------------------------
		//--- then update enemy pos ---
		g.update();

	}

}
