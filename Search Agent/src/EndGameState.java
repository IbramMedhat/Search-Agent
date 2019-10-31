public class EndGameState extends State {
	
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
		
		state = (pos.x << 20 ) | state;
		state = (pos.y << 16 ) | state;
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
	
	public int CollectedStonesCount()
	{
		return SumloopBits(24,6);
	}
	
	public int KilledWarriorsCount()
	{
		return SumloopBits(0,16);
	}
	
	public String toString() {
		
		Vector2 pos = this.getIronManPos();
		boolean snaped = this.isSnapped();
		String stones = "Stones: ("+loopBits(24, 6) +" :"+SumloopBits(24,6)+") ";
		String warriors = "Warriors: ("+loopBits(0, 16)+" :"+ SumloopBits(0,16)+") ";
	    return "Pos: "+pos+", Snapped: "+snaped+", "+stones+warriors;
//		return loopBits(24,8) +" "+loopBits(16,8) +" "+loopBits(8,8) +" "+loopBits(0,8) +" ";
		
		//if you want to print sums only
//		return pos+" "+snaped+" S:"+SumloopBits(24,6)+"  W:"+SumloopBits(0,5);
		
	    
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
	
	private int SumloopBits(int offset, int size)
	{
		int result = 0;
		for(int i = size - 1 ; i > -1 ; i--)
		{
			 result = result + ((state & (1<<i+offset))>>(i+offset));
		}
		return result;
	}
	
	
	
	
	
	
}
