import java.util.HashMap;

public class VisitedStateList {

	private HashMap<Integer, State> map;
	
	public boolean isStateRepeated(State state) {
		if(map.containsKey(state.getRawState())) {
			return true;
		}
		else
		{
			map.put(state.getRawState(), state);
			return false;
		}
	}
	
	public void InitializeList() {
		map = new HashMap<Integer, State>();
	}
}
