package uk.co.bigsoft.filesucker.transfer;

import java.util.LinkedList;

import uk.co.bigsoft.filesucker.transfer.download.SuckerThread;

public class TransferModel {

	private LinkedList<SuckerThread> activeFileSuckerThreads = new LinkedList<>();

	public TransferModel() {

	}

}
