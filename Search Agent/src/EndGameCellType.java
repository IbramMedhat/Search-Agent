
public enum EndGameCellType {
	EMPTY(' ',0),WARRIOR('W',1),THANOS('T',5),IRONMAN('I',0),STONE('S',0), OUTOFBOUND('X',0);
	
	
	private char cellChar;
	private int cellDamage; 
	
	EndGameCellType(char e, int damage)
	{
		this.cellChar = e;
		this.cellDamage = damage;
	}
	
	public char getCellChar()
	{
		return cellChar;
	}
	
	public int getCellDamage()
	{
		return cellDamage;
	}
}
