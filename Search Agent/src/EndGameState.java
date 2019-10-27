import java.util.ArrayList;

public class EndGameState extends State {
	private Vector2 ironManPos;
	private boolean snaped;
	private boolean[] killed;
	private boolean[] stonesCollected; 
	
	public EndGameState(int ironManX, int ironManY, boolean snaped, int numOfWarriors, int numOfInfintyStones) {
		//this(new Vector2(ironManX, ironManY), snaped, numOfWarriors, numOfInfintyStones);
	}
	
	public EndGameState(Vector2 ironManPos, boolean snaped, boolean[] killed, boolean[] stonesCollected) {
		super();
		this.ironManPos = ironManPos;
		this.stonesCollected = stonesCollected;
		this.snaped = snaped;
		this.killed = killed;
	}
	
	public EndGameState(Vector2 ironManPos, boolean[] killed, boolean[] stonesCollected, boolean snaped) {
		this(ironManPos, snaped, killed, stonesCollected);
	}
	
	public EndGameState(int x, int y, boolean snaped) {
		this(new Vector2(x, y), snaped);
	}
	public EndGameState(Vector2 ironManPos, boolean snaped) {
		this(ironManPos, snaped, 5, 6);
	}
	public EndGameState(Vector2 ironManPos, boolean snaped, int numOfWarriors, int numOfInfintyStones) {
		super();
		this.ironManPos = ironManPos;
		this.snaped = snaped;
		this.stonesCollected = new boolean[numOfInfintyStones];
		this.killed = new boolean[numOfWarriors];
	}

	public int getIronManX() {
		return ironManPos.x;
	}

	public int getIronManY() {
		return ironManPos.y;
	}
	
	public Vector2 getIronManPos()
	{
		return ironManPos;
	}
	
	public boolean[] getStonesCollected() {
		return stonesCollected;
	}

	public boolean isSnaped() {
		return snaped;
	}

	public boolean[] getKilled() {
		return killed;
	}

	public void setKilled(boolean[] killed) {
		this.killed = killed;
	}

	public void setSnaped(boolean snaped) {
		this.snaped = snaped;
	}

	public void setStonesCollected(boolean[] stonesCollected) {
		this.stonesCollected = stonesCollected;
	}

	
	
	
	
	
	
}
