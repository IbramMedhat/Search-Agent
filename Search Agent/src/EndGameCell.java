
public enum EndGameCell {
	EMPTY(' '),WARRIOR('W'),THANOS('T'),IRONMAN('I'),STONE('S');
	
	
	private char cellChar;
	
	EndGameCell(char e)
	{
		this.cellChar = e;
	}
	
	public char getCellChar()
	{
		return cellChar;
	}
}
