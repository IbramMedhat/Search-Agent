import java.util.ArrayList;

public class EndGameGrid {

	private EndGameCell [][] gridCells;
	private String gridString;
	private Vector2	initialIronManPos;
	private Vector2 thanosPos;

	
	public EndGameGrid(String GridString)
	{
		this.gridString = GridString;
		InitializeGrid();
	}
	
	private void InitializeGrid()
	{
		String[] gridSplitted = gridString.split(";");
		int gridWidth = Integer.parseInt((((gridSplitted[0]).split(","))[0]));
		int gridLength = Integer.parseInt((((gridSplitted[0]).split(","))[1]));
		gridCells = new EndGameCell[gridLength][gridWidth];

		for (int i = 0; i < gridCells.length; i++)
		{
			for (int j = 0; j < gridCells[i].length; j++)
			{
				gridCells[i][j] = new EndGameCell();
			}
		}
		insertObjects(gridSplitted[4], EndGameCellType.WARRIOR);
		insertObjects(gridSplitted[3], EndGameCellType.STONE);
		insertObjects(gridSplitted[2], EndGameCellType.THANOS);
		this.initialIronManPos = new Vector2(Integer.parseInt(gridSplitted[1].split(",")[0]),Integer.parseInt(gridSplitted[1].split(",")[1]));
		
	}
	
	//Helper function to insert the objects in their places in the grid, according to the string input
	private void insertObjects(String posString, EndGameCellType object)
	{
		// int stringLength = posString.length();
		ArrayList<Vector2> positions = new ArrayList<Vector2>();
		int index = 0;
		
		String[]  splitedString = posString.split(",");
		for (int i = 0; i < splitedString.length - 1; i+=2)
		{
			int xCord = Integer.parseInt(splitedString[i]);
			int yCord = Integer.parseInt(splitedString[i+1]);
			
			gridCells[xCord][yCord].setCellContent(object, index);
			
			positions.add(new Vector2(xCord, yCord));
			index++;
		}
		
		switch(object)
		{
			case THANOS:
				thanosPos = positions.get(0);
//				break;
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
				System.out.print(gridCells[i][j].getContent().getCellChar()+" | ");
			}
			System.out.println("");			
		}
	}
	
	public Vector2 getInitialIronManPos()
	{
		return initialIronManPos;
	}
	
	public EndGameCellType getGridCellContent(Vector2 pos, EndGameState currentState)
	{
		return getGridCellContent(pos.x, pos.y, currentState);
	}
	
	public EndGameCellType getGridCellContent(int x, int y, EndGameState currentState)
	{
		try {
			EndGameCellType obj = null;
			switch(gridCells[x][y].getContent())
			{
				default:
					obj = gridCells[x][y].getContent();
					break;	
				case WARRIOR:
					obj = currentState.isWarriorKilled(gridCells[x][y].getContentIndex())? EndGameCellType.EMPTY : EndGameCellType.WARRIOR;
					break;
				case STONE:
					obj =  currentState.isStoneCollected(gridCells[x][y].getContentIndex())? EndGameCellType.EMPTY : EndGameCellType.STONE;
					break;				
			}
			return obj;
		} catch (IndexOutOfBoundsException e) {
			return EndGameCellType.OUTOFBOUND;
		}
	}
	public EndGameCell getEndGameCell(Vector2 pos) {
		return gridCells[pos.x][pos.y];
	}
	
	public boolean isCellEmpty(Vector2 pos, EndGameState currentState)
	{
		return isCellEmpty(pos.x,pos.y, currentState);
	}
	
	public boolean isCellEmpty(int x, int y, EndGameState currentState)
	{
		try 
		{
			if((gridCells[x][y].getContent() == EndGameCellType.EMPTY))
			{
				return true;
			}
			else
			{
				boolean empty = false;
				switch(gridCells[x][y].getContent())
				{
					case WARRIOR:
						empty = currentState.isWarriorKilled(gridCells[x][y].getContentIndex());
						break;
					case STONE:
						empty =  true;
						break;
					case THANOS:
						empty = currentState.areAllStoneCollected();
					default:
						break;		
				} 
				return empty;
			}
		} catch (IndexOutOfBoundsException e) {
			return false;
		}

	}
	
	public boolean doesCellContain(Vector2 pos, EndGameCellType object, EndGameState currentState)
	{
		return doesCellContain(pos.x,pos.y, object, currentState);
	}
	
	public boolean doesCellContain(int x, int y, EndGameCellType object, EndGameState currentState)
	{
		try {
			if(gridCells[x][y].getContent() != object)
			{
				return false;
			}
			else
			{
				boolean decision = true;
				switch(gridCells[x][y].getContent())
				{
					case WARRIOR:
						decision = !currentState.isWarriorKilled(gridCells[x][y].getContentIndex());
						break;
					case STONE:
						decision =  !currentState.isStoneCollected(gridCells[x][y].getContentIndex());
						break;
					default:
						break;		
				}
				return decision;
			}
		} catch (IndexOutOfBoundsException e) {
			return false;
		}

	}
			
	


	public Vector2 getThanosPos() {
		return thanosPos;
	}

	public static void main(String [] args)
	{
		//TestGridFunctionality();
	}	
	
	@SuppressWarnings("unused")
	private static void TestGridFunctionality()
	{
		EndGameGrid grid = new EndGameGrid("5,5;1,2;3,1;0,2,1,1,2,1,2,2,4,0,4,1;0,3,3,0,3,2,3,4,4,3");
		grid.PrintGrid();
		System.out.println("");
		
		EndGameState state = new EndGameState();
		System.out.println(grid.getGridCellContent(new Vector2(0, 2), state) == EndGameCellType.STONE);
		System.out.println(!grid.isCellEmpty(new Vector2(0, 2), state));
		System.out.println(grid.doesCellContain(new Vector2(0, 2),EndGameCellType.STONE, state));
		state.setStoneCollected(0);
		System.out.println(grid.getGridCellContent(new Vector2(0, 2), state) == EndGameCellType.EMPTY);
		System.out.println(grid.isCellEmpty(new Vector2(0, 2), state));
		System.out.println(!grid.doesCellContain(new Vector2(0, 2),EndGameCellType.STONE, state));
		
		System.out.println("");
		
		System.out.println(grid.getGridCellContent(new Vector2(0, 3), state) == EndGameCellType.WARRIOR);
		System.out.println(!grid.isCellEmpty(new Vector2(0, 3), state));
		System.out.println(grid.doesCellContain(new Vector2(0, 3),EndGameCellType.WARRIOR, state));
		state.setWarriorKilled(0);
		System.out.println(grid.getGridCellContent(new Vector2(0, 3), state) == EndGameCellType.EMPTY);
		System.out.println(grid.isCellEmpty(new Vector2(0, 3), state));
		System.out.println(!grid.doesCellContain(new Vector2(0, 3),EndGameCellType.WARRIOR, state));
		

		state = new EndGameState();
		System.out.println(grid.getGridCellContent(new Vector2(3,1 ), state).getCellDamage());
		
		System.out.println(grid.doesCellContain(new Vector2(-1, 3),EndGameCellType.WARRIOR, state));
		System.out.println(grid.isCellEmpty(new Vector2(-1, 3), state));

	}
	
	//Returns a string visualizing the Grid according to the given state 
	public String Visualize(State currentState) {
		String out = "";
		EndGameState currentEndGameState = (EndGameState)currentState;
		
		Vector2 ironManpos = currentEndGameState.getIronManPos();
		EndGameCell ironManCell = new EndGameCell();
		ironManCell.setCellContent(gridCells[ironManpos.x][ironManpos.y].getContent(), gridCells[ironManpos.x][ironManpos.y].getContentIndex());
		this.gridCells[ironManpos.x][ironManpos.y].setCellContent(EndGameCellType.IRONMAN, 0);
		
		for (int i = 0; i < gridCells.length; i++)
		{
			for (int j = 0; j < gridCells[i].length; j++)
			{
				if(j == 0)
					out += ("| ");
				EndGameCellType cellContent = getGridCellContent(i,j ,currentEndGameState );
				if(isCellEmpty(i, j, currentEndGameState) || doesCellContain(i, j, cellContent, currentEndGameState)) {					
					out += (cellContent.getCellChar()+" | ");
				}
			}
			out += "\n";		
		}
		this.gridCells[ironManpos.x][ironManpos.y] = ironManCell;
		return out;
	}
	
	

}
