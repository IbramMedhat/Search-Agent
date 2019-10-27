
public class EndGameCell {

	private EndGameCellType content;
	private int contentIndex;
	
	public EndGameCell()
	{
		content = EndGameCellType.EMPTY;
		contentIndex = 0;
	}
	
	public void setCellContent(EndGameCellType object, int index)
	{
		this.content = object;
		this.contentIndex = index;
	}

	public int getContentIndex() {
		return contentIndex;
	}

	public void setContentIndex(int contentIndex) {
		this.contentIndex = contentIndex;
	}

	public EndGameCellType getContent() {
		return content;
	}
	
	
}
