package uk.co.bigsoft.filesucker.transfer.download.si;

import uk.co.bigsoft.filesucker.transfer.view.SuckerItemModel;

public class SuckerItem {
	private String url;
	private String local;
	//private SuckerItemModel model;

	public SuckerItem(String url, String local) {
		this.url = url;
		this.local = local;
	}

	public String getUrl() {
		return url;
	}

	public String getLocal() {
		return local;
	}

//	public SuckerItemModel getModel() {
//		return model;
//	}
//
//	public void setModel(SuckerItemModel model) {
//		this.model = model;
//	}

}
