
public abstract class State {
	
	
	protected  int state;
	
	public State() {
		state = 0;
	}
	
	public State(State s)
	{
		state = s.getRawState();
	}
	
	public State(int state)
	{
		this.state = state;
	}
	public int getRawState()
	{
		return state;
	}
	
	
	
}
