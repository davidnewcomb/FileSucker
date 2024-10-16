package uk.co.bigsoft.filesucker.task.looper;

public class LooperId {

	private static int currentLooperId = 1;

	public static int getNext() {
		return currentLooperId++;
	}
}
