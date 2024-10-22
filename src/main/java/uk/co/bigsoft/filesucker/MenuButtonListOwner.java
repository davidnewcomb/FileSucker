package uk.co.bigsoft.filesucker;

import uk.co.bigsoft.filesucker.view.HistoryDropDown;

public interface MenuButtonListOwner {
	public HistoryDropDown getList();

	public void setList(HistoryDropDown list);
}
