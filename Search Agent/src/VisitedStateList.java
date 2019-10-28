import java.util.LinkedList;

public class VisitedStateList {
	
	private LinkedList<State> list;
	
	public VisitedStateList()
	{
		this.list = new LinkedList<State>();
	}
	
	public boolean CheckState(State state)
	{
		int foundState = findState(state.getRawState());
		if(list.size() == 0 || list.get(foundState).getRawState() != state.getRawState())
		{
			list.add(foundState, state);
			return false;
		}
		return true;
		
	}
	
	private int findState(int x) 
    { 
        int l = 0, r = list.size() - 1; 
        int finalIndex = 0;
        while (l <= r) { 
            int m = l + (r - l) / 2; 
            
            // Check if x is present at mid 
            if (list.get(m).getRawState() == x) 
                return m; 
  
            // If x greater, ignore left half 
            if (list.get(m).getRawState()  < x) 
                l = m + 1; 
  
            // If x is smaller, ignore right half 
            else
                r = m - 1; 
            finalIndex = m;
        } 
  
        // if we reach here, then element was 
        // not present 
        return finalIndex; 
    } 
	
	public static void main(String [] args)
	{
		VisitedStateList list = new VisitedStateList();
		
		EndGameState state1 = new EndGameState();
		EndGameState state2 = new EndGameState();
		EndGameState state3 = new EndGameState();
		
		
		state1.setIronManPosition(new Vector2(1,1));
		state2.setIronManPosition(new Vector2(1,0));
		
		state1.setSnapped();
		state1.setIronManPosition(new Vector2(16,16));
		state2.setSnapped();
		state2.setStoneCollected(5);
		state2.setWarriorKilled(15);
		state2.setIronManPosition(new Vector2(0,0));
		
//		list.CheckState(state1);
		list.CheckState(state2);
		list.CheckState(state3);
		
		
		for (State state : list.list) {
			System.out.println(state);
		}
	}
}
