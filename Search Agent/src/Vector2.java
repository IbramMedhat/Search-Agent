
public class Vector2 {
	int x;
	int y;
	
	public Vector2(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Vector2()
	{
		this.x = 0;
		this.y = 0;
	}
	
	public String toString() {
	    return "("+this.x+","+this.y+")";
	}
}
