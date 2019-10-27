
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
	
	public Vector2 Up()
	{
		return new Vector2(this.x -1 , this.y);
	}
	
	public Vector2 Down()
	{
		return new Vector2(this.x + 1 , this.y);
	}
	
	public Vector2 Right()
	{
		return new Vector2(this.x , this.y + 1);
	}
	
	public Vector2 Left()
	{
		return new Vector2(this.x, this.y - 1);
	}
	
	public String toString() {
	    return "("+this.x+","+this.y+")";
	}
}
