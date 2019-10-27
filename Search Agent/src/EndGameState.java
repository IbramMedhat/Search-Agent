import java.util.ArrayList;

public class EndGameState extends State {
	private Vector2 ironManPos;
	private boolean snaped;
	private boolean[] killed;
	private boolean[] stonesCollected; 
	
	public EndGameState(int ironManX, int ironManY, boolean snaped, int numOfWarriors, int numOfInfintyStones) {
		this(new Vector2(ironManX, ironManY), snaped, numOfWarriors, numOfInfintyStones);
	}
	
	public EndGameState(Vector2 ironManPos, boolean snaped, int numOfWarriors, int numOfInfintyStones) {
		super();
		this.ironManPos = ironManPos;
		this.stonesCollected = new boolean[numOfInfintyStones];
		this.snaped = snaped;
		this.killed = new boolean[numOfWarriors];
	}
	
	public EndGameState(int ironManX, int ironManY, boolean snaped) {
		this(ironManX, ironManY, snaped, 5, 6);
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
