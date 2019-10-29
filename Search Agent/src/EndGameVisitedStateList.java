import java.util.LinkedList;

public class EndGameVisitedStateList extends VisitedStateList{
	
	private LinkedList<LinkedList<LinkedList<LinkedList<LinkedList<State>>>>> list;
	
	public EndGameVisitedStateList()
	{
		super();
		InitializeList();
	}
	
	@Override
	public void InitializeList() {
		this.list = new LinkedList<LinkedList<LinkedList<LinkedList<LinkedList<State>>>>>();
		
		for(int i = 0; i < 7; i++)
		{
			LinkedList<LinkedList<LinkedList<LinkedList<State>>>> stoneList = new LinkedList<LinkedList<LinkedList<LinkedList<State>>>>();
			for(int j = 0; j < 17; j++)
			{
				LinkedList<LinkedList<LinkedList<State>>> monsterList = new LinkedList<LinkedList<LinkedList<State>>>();
				for(int k = 0; k < 5; k++)
				{
					LinkedList<LinkedList<State>> xCordList = new LinkedList<LinkedList<State>>(); 
					for(int w = 0; w < 5; w++)
					{						
						xCordList.add(new LinkedList<State>());
					}
					monsterList.add(xCordList);
				}
				stoneList.add(monsterList);
			}
			list.add(stoneList);
		}		
	}
	
	public void AddInitialState(State state) {
		if(list.size() == 0)
		{
			int ModX = ((EndGameState)state).getIronManPos().x % 5;
			int ModY = ((EndGameState)state).getIronManPos().y % 5;
			list.get(0).get(0).get(ModX).get(ModY).add(state);
		}
	}
	public boolean isStateRepeated(State state)
	{
		int stoneListIndex = ((EndGameState)state).CollectedStonesCount();
		int monsterListIndex = ((EndGameState)state).KilledWarriorsCount();
		Vector2 pos = ((EndGameState)state).getIronManPos();
		int ModX = pos.x % 5;
		int ModY = pos.y % 5;
		LinkedList<State> searchList = list.get(stoneListIndex).get(monsterListIndex).get(ModX).get(ModY);
		if(findStateLinearSearch(state.getRawState(), searchList))
		{
			return true;
		}
		else
		{
			searchList.add(state);
			return false;
		}
//		foundState = findStateLinearSearch(state.getRawState(), searchList);
//		if(foundState == -1) {
//			searchList.addLast(state);
//			return false;
//		}
//		else if(foundState == 0)
//		{
//			searchList.addFirst(state);
//			return false;
//		}
//		else if(searchList.get(foundState).getRawState() != state.getRawState())
//		{				
//			searchList.add(foundState, state);
//			return false;
//		}
//		return true;
	}
	
	private boolean findStateLinearSearch(int x, LinkedList<State> searchList)
	{
		for (int i = 0; i < searchList.size(); i++) {
			
			if(searchList.get(i).getRawState() == x)
			{
				return true;
			}
		}
		return false;
	}
	
	 @SuppressWarnings("unused")
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
		EndGameVisitedStateList list = new EndGameVisitedStateList();
		
		EndGameState state1 = new EndGameState(0);
		EndGameState state2 = new EndGameState(0);
		EndGameState state3 = new EndGameState(0);
		
//		state2.setStoneCollected(0);
		state1.setIronManPosition(new Vector2(1,3));
		state2.setIronManPosition(new Vector2(1,3));
		state3.setIronManPosition(new Vector2(1,3));
		
		System.out.println(state1.getIronManPos().x % 5);
		System.out.println(state1.getIronManPos().y % 5);
		
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
