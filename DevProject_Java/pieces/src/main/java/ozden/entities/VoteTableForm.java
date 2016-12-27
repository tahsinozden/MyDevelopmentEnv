package ozden.entities;

import java.util.List;

public class VoteTableForm {
	private VoteTable table;
	private List<TableItem> tableItems;
	
	public VoteTableForm(VoteTable table, List<TableItem> tableItems) {
		super();
		this.table = table;
		this.tableItems = tableItems;
	}

	public VoteTable getTable() {
		return table;
	}

	public void setTable(VoteTable table) {
		this.table = table;
	}

	public List<TableItem> getTableItems() {
		return tableItems;
	}

	public void setTableItems(List<TableItem> tableItems) {
		this.tableItems = tableItems;
	}
	
	
	
}
