
public enum EndGameCellType {
	EMPTY(' '),WARRIOR('W'),THANOS('T'),IRONMAN('I'),STONE('S');
	
	
	private char cellChar;
	
	EndGameCellType(char e)
	{
		this.cellChar = e;
	}
	
	public char getCellChar()
	{
		return cellChar;
	}
}
