package uk.co.bigsoft.filesucker.config;

import java.util.ArrayList;
import java.util.List;

public class ConfigModel implements Cloneable {

	private String screenBaseDir = "";
	private int numberFrom = 1;
	private int numberTo = 15;
	private int numberPad = 1;
	private String textFrom = "a";
	private String textTo = "z";
	private String postPrefix = "_";
	private int maxTasks = 10;
	private int maxSubTasks = 2;
	private String findExtn = ".jpg";
	private int delaySockReadMs = 5000;
	private int delayFilesMs = 5000;
	private String helperWeb = "";
	private String helperText = "";
	private String openDirectory = "";
	private List<String> numberLooperHistory = new ArrayList<>();
	private List<String> textLooperHistory = new ArrayList<>();
	private List<String> launchProfiles = new ArrayList<>();

	public ConfigModel() {
		//
	}
	
	public ConfigModel clone() {
		ConfigModel cm = new ConfigModel();
		cm.setScreenBaseDir(screenBaseDir);
		cm.setNumberFrom(numberFrom);
		cm.setNumberTo(numberTo);
		cm.setNumberPad(numberPad);
		cm.setTextFrom(textFrom);
		cm.setTextTo(textTo);
		cm.setPostPrefix(postPrefix);
		cm.setMaxTasks(maxTasks);
		cm.setMaxSubTasks(maxSubTasks);
		cm.setFindExtn(findExtn);
		cm.setDelaySockReadMs(delaySockReadMs);
		cm.setDelayFilesMs(delayFilesMs);
		cm.setHelperWeb(helperWeb);
		cm.setHelperText(helperText);
		cm.setOpenDirectory(openDirectory);
		cm.setNumberLooperHistory(numberLooperHistory);
		cm.setTextLooperHistory(textLooperHistory);
		cm.setLaunchProfiles(launchProfiles);
		return cm;
	}
	
	public String getScreenBaseDir() {
		return screenBaseDir;
	}

	public void setScreenBaseDir(String screenBaseDir) {
		this.screenBaseDir = screenBaseDir;
	}

	public int getNumberFrom() {
		return numberFrom;
	}

	public void setNumberFrom(int numberFrom) {
		this.numberFrom = numberFrom;
	}

	public int getNumberTo() {
		return numberTo;
	}

	public void setNumberTo(int numberTo) {
		this.numberTo = numberTo;
	}

	public int getNumberPad() {
		return numberPad;
	}

	public void setNumberPad(int numberPad) {
		this.numberPad = numberPad;
	}

	public String getTextFrom() {
		return textFrom;
	}

	public void setTextFrom(String textFrom) {
		this.textFrom = textFrom;
	}

	public String getTextTo() {
		return textTo;
	}

	public void setTextTo(String textTo) {
		this.textTo = textTo;
	}

	public String getPostPrefix() {
		return postPrefix;
	}

	public void setPostPrefix(String postPrefix) {
		this.postPrefix = postPrefix;
	}

	public int getMaxTasks() {
		return maxTasks;
	}

	public void setMaxTasks(int maxTasks) {
		this.maxTasks = maxTasks;
	}

	public int getMaxSubTasks() {
		return maxSubTasks;
	}

	public void setMaxSubTasks(int maxSubTasks) {
		this.maxSubTasks = maxSubTasks;
	}

	public String getFindExtn() {
		return findExtn;
	}

	public void setFindExtn(String findExtn) {
		this.findExtn = findExtn;
	}

	public int getDelaySockReadMs() {
		return delaySockReadMs;
	}

	public void setDelaySockReadMs(int delaySockReadMs) {
		this.delaySockReadMs = delaySockReadMs;
	}

	public int getDelayFilesMs() {
		return delayFilesMs;
	}

	public void setDelayFilesMs(int delayFilesMs) {
		this.delayFilesMs = delayFilesMs;
	}

	public String getHelperWeb() {
		return helperWeb;
	}

	public void setHelperWeb(String helperWeb) {
		this.helperWeb = helperWeb;
	}

	public String getHelperText() {
		return helperText;
	}

	public void setHelperText(String helperText) {
		this.helperText = helperText;
	}

	public String getOpenDirectory() {
		return openDirectory;
	}

	public void setOpenDirectory(String openDirectory) {
		this.openDirectory = openDirectory;
	}

	public List<String> getNumberLooperHistory() {
		return numberLooperHistory;
	}
	
	public void setNumberLooperHistory(List<String> numberLooperHistory) {
		this.numberLooperHistory = numberLooperHistory;
	}
	
	public List<String> getTextLooperHistory() {
		return textLooperHistory;
	}
	
	public void setTextLooperHistory(List<String> textLooperHistory) {
		this.textLooperHistory = textLooperHistory;
	}

	public List<String> getLaunchProfiles() {
		return launchProfiles;
	}
	
	public void setLaunchProfiles(List<String> launchProfiles) {
		this.launchProfiles = launchProfiles;
	}

}
