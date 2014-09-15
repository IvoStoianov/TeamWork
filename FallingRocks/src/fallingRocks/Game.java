package fallingRocks;
import java.awt.Canvas;
import java.awt.Graphics;

public class Game extends Canvas implements Runnable{
	
	public static int END_OF_FIELD = 30;
	public static Ship ship;
	public static Rocks rocks;
	public static FastRocks fastrocks;
	public static Graphics globalGraphics;
	private Thread gameThread;
	public static boolean gameRunning = false;
	public static int threadSpeed = 10;
	
	public Game(){
		ship = new Ship();
		rocks = new Rocks();
		fastrocks = new FastRocks();
		
	}
	
	
	
	public void paint(Graphics g){
		globalGraphics = g.create();
		
		if(gameThread == null){
			gameThread = new Thread(this);
			gameThread.start();
			gameRunning = true;
		}
	}
	
	
	public void run() {
		int rockCreationDelay = 0;
		int rockLevels = 35;
		int i = 0;
		int currentLevel = 1;
		System.out.println("Level 1");
		while(gameRunning) {
			rockCreationDelay++;
			ship.tick();
			
			if(rockCreationDelay > rockLevels) {
				rocks.tick();
				fastrocks.tick();
				if (currentLevel > 10) {
					fastrocks.tick();
				}				
				rockCreationDelay = 0;
				i++;
				if (i == 50) {
					rockLevels--;
					currentLevel++;
					System.out.println("Level up " + currentLevel);
					i = 0;
				}	
			}
			
			render(globalGraphics);

			try {
				Thread.sleep(this.threadSpeed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void render(Graphics g){
		g.clearRect(0, 0, 400, 600);
		ship.drawShip(globalGraphics);
		rocks.drawRocks(globalGraphics);
		fastrocks.drawfastRocks(globalGraphics);
	}
}
