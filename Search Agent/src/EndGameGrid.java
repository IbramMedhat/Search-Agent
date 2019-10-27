import java.util.ArrayList;

public class EndGameGrid {

	private EndGameCell [][] gridCells;
	private String gridString;
	private ArrayList<Vector2> remaingWarriorsPos;
	private ArrayList<Vector2> remaingStonesPos;
	private Vector2 currentIronManPos;
	private Vector2 thanosPos;

	
	public EndGameGrid(String GridString)
	{
		this.gridString = GridString;
		InitializeGrid();
	}
	
	private void InitializeGrid()
	{
		String[] gridSplitted = gridString.split(";");
		gridCells = new EndGameCell[Character.getNumericValue(gridSplitted[0].charAt(2))][Character.getNumericValue(gridSplitted[0].charAt(0))];
		for (int i = 0; i < gridCells.length; i++)
		{
			for (int j = 0; j < gridCells[i].length; j++)
			{
				gridCells[i][j] = EndGameCell.EMPTY;
			}
		}

		insertObjects(gridSplitted[4], EndGameCell.WARRIOR);
		insertObjects(gridSplitted[3], EndGameCell.STONE);
		insertObjects(gridSplitted[2], EndGameCell.THANOS);
		insertObjects(gridSplitted[1], EndGameCell.IRONMAN);
		
	}
	
	//Helper function to insert the objects in their places in the grid, according to the string input
	private void insertObjects(String posString, EndGameCell object)
	{
		int stringLength = posString.length();
		ArrayList<Vector2> positions = new ArrayList<Vector2>();
		for (int i = 0; i < stringLength - 2; i+=4)
		{
			int xCord = Character.getNumericValue(posString.charAt(i));
			int yCord = Character.getNumericValue(posString.charAt(i+2));
			gridCells[xCord][yCord] = object;
			positions.add(new Vector2(xCord, yCord));
		}
		
		switch(object)
		{
			case WARRIOR:
				remaingWarriorsPos = positions;
				break;
			case IRONMAN:
				currentIronManPos = positions.get(0);
				break;
			case STONE:
				remaingStonesPos = positions;
				break;
			case THANOS:
				thanosPos = positions.get(0);
				break;
			default:
				break;		
		}
	}
	
	//Visualize the Grid
	public void PrintGrid()
	{
		for (int i = 0; i < gridCells.length; i++)
		{
			for (int j = 0; j < gridCells[i].length; j++)
			{
				System.out.print(gridCells[i][j].getCellChar()+" | ");
			}
			System.out.println("");			
		}
	}
	
	public EndGameCell getGridCell(Vector2 pos)
	{
		return getGridCell(pos.x, pos.y);
	}
	
	public EndGameCell getGridCell(int x, int y)
	{
		return gridCells[x][y];
	}
	
	public boolean isCellEmpty(Vector2 pos)
	{
		return isCellEmpty(pos.x,pos.y);
	}
	
	public boolean isCellEmpty(int x, int y)
	{
		return (gridCells[x][y] == EndGameCell.EMPTY);
	}
	
	public boolean doesCellContain(Vector2 pos, EndGameCell object)
	{
		return doesCellContain(pos.x,pos.y, object);
	}
	
	public boolean doesCellContain(int x, int y, EndGameCell object)
	{
		return (gridCells[x][y] == object);
	}
		
	
	public ArrayList<Vector2> getRemaingWarriorsPos() {
		return remaingWarriorsPos;
	}

	public ArrayList<Vector2> getRemaingStonesPos() {
		return remaingStonesPos;
	}

	public Vector2 getCurrentIronManPos() {
		return currentIronManPos;
	}

	public Vector2 getThanosPos() {
		return thanosPos;
	}

	public static void main(String [] args)
	{
		EndGameGrid grid = new EndGameGrid("5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2,3,4,4,3");
		grid.PrintGrid();
		
		System.out.println(grid.isCellEmpty(0,2));	
		System.out.println(grid.doesCellContain(0,2, EndGameCell.STONE));	
		System.out.println(grid.getGridCell(0,2));	
		
		System.out.println(grid.remaingWarriorsPos);
		System.out.println(grid.remaingStonesPos);
		grid.getRemaingStonesPos().get(0).x = 5;
		System.out.println(grid.remaingStonesPos);
		System.out.println(grid.currentIronManPos);
		System.out.println(grid.thanosPos);
	}
}
