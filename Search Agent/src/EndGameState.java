import java.util.ArrayList;

public class EndGameState extends State {
	private int ironManX;
	private int ironManY;
	private boolean snaped;
	private boolean[] killed;
	private boolean[] stonesCollected; 
	
	
	public EndGameState(int ironManX, int ironManY, boolean snaped, int numOfWarriors, int numOfInfintyStones) {
		super();
		this.ironManX = ironManX;
		this.ironManY = ironManY;
		this.stonesCollected = new boolean[numOfInfintyStones];
		this.snaped = snaped;
		this.killed = new boolean[numOfWarriors];
	}
	
	public EndGameState(int ironManX, int ironManY, boolean snaped) {
		this(ironManX, ironManY, snaped, 5, 6);
	}

	public int getIronManX() {
		return ironManX;
	}

	public int getIronManY() {
		return ironManY;
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

	public void setIronManX(int ironManX) {
		this.ironManX = ironManX;
	}

	public void setIronManY(int ironManY) {
		this.ironManY = ironManY;
	}

	public void setSnaped(boolean snaped) {
		this.snaped = snaped;
	}

	public void setStonesCollected(boolean[] stonesCollected) {
		this.stonesCollected = stonesCollected;
	}

	
	
	
	
	
	
}
