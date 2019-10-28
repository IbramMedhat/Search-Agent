import java.util.LinkedList;

public class VisitedStateList {
	
	private LinkedList<LinkedList<LinkedList<State>>> list;
	
	public VisitedStateList()
	{
		this.list = new LinkedList<LinkedList<LinkedList<State>>>();
		
		for(int i = 0; i < 7; i++)
		{
			LinkedList<LinkedList<State>> stoneList = new LinkedList<LinkedList<State>>();
			for(int j = 0; j < 17; j++)
			{
				stoneList.add(new LinkedList<State>());
			}
			list.add(stoneList);
		}
	}
	public void AddInitialState(State state) {
		if(list.size() == 0)
		{
			list.get(0).get(0).add(state);
		}
	}
	public boolean isStateRepeated(State state)
	{
		int foundState = 0;
		int stoneListIndex = ((EndGameState)state).CollectedStonesCount();
		int monsterListIndex = ((EndGameState)state).KilledWarriorsCount();
		LinkedList<State> searchList = list.get(stoneListIndex).get(monsterListIndex);
		foundState = findStateBinarySearch(state.getRawState(), searchList);
		if(foundState == -1) {
			searchList.addLast(state);
			return false;
		}
		else if(foundState == 0)
		{
			searchList.addFirst(state);
			return false;
		}
		else if(searchList.get(foundState).getRawState() != state.getRawState())
		{				
			searchList.add(foundState, state);
			return false;
		}
		return true;
	}
	
	private int findStateLinearSearch(int x, LinkedList<State> searchList)
	{
		int index = -1;
		for (int i = 0; i < searchList.size(); i++) {
			
			if(searchList.get(i).getRawState() >= x)
			{
				index = i;
				break;
			}
		}
		return index;
	}
	
	 private int findStateBinarySearch(int x, LinkedList<State> searchList) 
     { 
         int l = 0, r = searchList.size() - 1; 
         int finalIndex = 0;
         while (l <= r) { 
             int m = l + (r - l) / 2; 
            
             // Check if x is present at mid 
             if (searchList.get(m).getRawState() == x) 
                 return m; 
  
             // If x greater, ignore left half 
             if (searchList.get(m).getRawState()  < x) 
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
	
//	public static boolean CheckStateInt(LinkedList<Integer> list, int state)
//	{
//		int foundState = findStateInt(state, list);
//		
//		if(list.size() == 0 || list.get(foundState) != state)
//		{
////			System.out.println(foundState);
//			if(list.size() == 0)
//			list.add(foundState, state);
//			return false;
//		}
//		
//		return true;
//		
//	}
//	
//	private static int findStateInt(int x, LinkedList<Integer> list) 
//    { 
//        int l = 0, r = list.size() - 1; 
//        int finalIndex = 0;
//        
//        while (l <= r) { 
//            int m = l + (r - l) / 2; 
//            
//            // Check if x is present at mid 
//            if (list.get(m) == x) 
//                return m; 
//  
//            // If x greater, ignore left half 
//            if (list.get(m)  < x) 
//                l = m + 1; 
//  
//            // If x is smaller, ignore right half 
//            else
//                r = m - 1; 
//            finalIndex = m;
//        } 
//  
//        // if we reach here, then element was 
//        // not present 
//        return finalIndex; 
//    } 
//	
	public static void main(String [] args)
	{
		VisitedStateList list = new VisitedStateList();
		
		EndGameState state1 = new EndGameState(0);
		EndGameState state2 = new EndGameState(0);
		EndGameState state3 = new EndGameState(2);
		
		state2.setStoneCollected(0);
		
		list.isStateRepeated(state1);
		list.isStateRepeated(state2);
		list.isStateRepeated(state3);
		
		System.out.println(list.list);
//		
//		
//		state1.setIronManPosition(new Vector2(1,1));
//		state2.setIronManPosition(new Vector2(1,0));
//		
//		state1.setSnapped();
//		state1.setIronManPosition(new Vector2(16,16));
//		state2.setSnapped();
//		state2.setStoneCollected(5);
//		state2.setWarriorKilled(15);
//		state2.setIronManPosition(new Vector2(0,0));
//		
////		list.CheckState(state1);
//		list.CheckState(state2);
//		list.CheckState(state3);
//		
//		
//		for (State state : list.list) {
//			System.out.println(state);
//		}
//		LinkedList<Integer> test = new LinkedList<Integer>();
//		CheckStateInt(test, 5);
//		CheckStateInt(test, 6);
//
//		
//		
//		
//		System.out.println(test);
		
	}
}
