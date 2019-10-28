import java.util.ArrayList;

public class EndGameState extends State {
	private Vector2 ironManPos;
	private boolean snaped;
	private boolean[] killed;
	private boolean[] stonesCollected; 
	
	
	
	public EndGameState()
	{
		super();
	}
	
	public EndGameState(int rawState)
	{
		super(rawState);
	}
	
	public EndGameState(EndGameState state)
	{
		super(state);
	}
	
	
	
	public boolean isSnapped()
	{
		return (state >> 30) > 0 ? true : false;
	}
	
	public void setSnapped()
	{
		state = state | (1<<30); 
	}

	public Vector2 getIronManPos() {
		
		byte x = (byte) ((state & 0x00F00000)>>20);
		byte y = (byte) ((state & 0x000F0000)>>16);
		return new Vector2(x,y);
	}
	
	public void setIronManPosition(int x, int y)
	{
		state = 0xFF00FFFF & state;
		state = (((x<<4) | y) << 16 ) | state;
	}
	
	public void setIronManPosition(Vector2 pos)
	{
		
		state = 0xFF00FFFF & state;
		
		state = (pos.rawPosition() << 16 ) | state;
	}
	
	public boolean areAllStoneCollected()
	{
		return ((0x3F000000 & state) == 0x3F000000) ? true : false; 
	}
	
	public boolean isStoneCollected(int i)
	{
		boolean out =  (state & (1<<i+24)) > 0 ? true : false;
		return out;
	}
	
	public void setStoneCollected(int i)
	{
		state = (state | (1<<i+24));
	}
	
	public boolean isWarriorKilled(int i)
	{
		boolean out =  (state & (1<<i)) > 0 ? true : false;
		return out;
	}
	
	public void setWarriorKilled(int i)
	{
		state = (state | (1<<i));
	}
	
	public String toString() {
		Vector2 pos = this.getIronManPos();
		boolean snaped = this.isSnapped();
		String stones = "Stones: ("+ loopBits(24, 6) + "), ";
		String warriors = "Warriors: ("+ loopBits(0, 16) + ") ";
	    return "Pos: "+pos+", Snapped: "+snaped+", "+stones+warriors;
	}
	
	private String loopBits(int offset, int size)
	{
		String result = "";
		for(int i = size - 1 ; i > -1 ; i--)
		{
			 result = result + ((state & (1<<i+offset))>>(i+offset)) +",";
		}
		return result;
	}
	
	
	
	
	
	
}
