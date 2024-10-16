package uk.co.bigsoft.filesucker.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigModel implements Cloneable {

	private String baseDir = System.getenv("HOME") + "/FileSuker";
	private String helperWeb = "firefox %s";
	private String helperText = "textPad %s";
	private String openDirectory = "/usr/bin/nautilus %s";
	private int numberFrom = 1;
	private int numberTo = 15;
	private int numberPad = 1;
	private String textFrom = "a";
	private String textTo = "z";
	private String postPrefix = "_";
	private List<String> findExtn = Arrays.asList(".jpg");
	private int maxTasks = 10;
	private int maxSubTasks = 2;
	private int delaySockReadMs = 5000;
	private int delayFilesMs = 5000;
	private List<String> numberLooperHistory = new ArrayList<>();
	private List<String> textLooperHistory = new ArrayList<>();
	private List<String> launchProfiles = new ArrayList<>();

	public ConfigModel() {
		//
	}

	public ConfigModel clone() {
		ConfigModel cm = new ConfigModel();
		cm.setBaseDir(baseDir);
		cm.setHelperWeb(helperWeb);
		cm.setHelperText(helperText);
		cm.setOpenDirectory(openDirectory);
		cm.setNumberFrom(numberFrom);
		cm.setNumberTo(numberTo);
		cm.setNumberPad(numberPad);
		cm.setTextFrom(textFrom);
		cm.setTextTo(textTo);
		cm.setPostPrefix(postPrefix);
		cm.setFindExtn(findExtn);
		cm.setMaxTasks(maxTasks);
		cm.setMaxSubTasks(maxSubTasks);
		cm.setDelaySockReadMs(delaySockReadMs);
		cm.setDelayFilesMs(delayFilesMs);
		cm.setNumberLooperHistory(numberLooperHistory);
		cm.setTextLooperHistory(textLooperHistory);
		cm.setLaunchProfiles(launchProfiles);
		return cm;
	}

	public String getBaseDir() {
		return baseDir;
	}

	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
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

	public List<String> getFindExtn() {
		return findExtn;
	}

	public void setFindExtn(List<String> findExtn) {
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

	public String getHelperDirectory() {
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
