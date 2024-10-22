package uk.co.bigsoft.filesucker.task.view.loopers;

import java.util.List;

public interface ILooperPanel {
	public void fill(List<String> parameters);

	public String toStringBraces();
}
