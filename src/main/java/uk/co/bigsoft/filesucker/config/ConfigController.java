package uk.co.bigsoft.filesucker.config;


public class ConfigController {

	private ConfigModel model;
	private ConfigModel editingModel;
	private ConfigView view;
	private ConfigSaver saver = new ConfigSaver();

	public ConfigController(ConfigModel m, ConfigView v) {
		model = m;
		editingModel = m.clone();
		view = v;
		initView();
	}

	public void initView() {
		view.getBaseTextField().setText(model.getBaseDir());

		view.getHelperWebTextField().setText(model.getHelperWeb());
		view.getHelperTextTextField().setText(model.getHelperText());
		view.getHelperDirectoryTextField().setText(model.getHelperDirectory());

		view.getNumberToTextField().setText(String.valueOf(model.getNumberTo()));
		view.getNumberFromTextField().setText(String.valueOf(model.getNumberFrom()));
		view.getNumberPadTextField().setText(String.valueOf(model.getNumberPad()));

		view.getTextFromTextField().setText(model.getTextFrom());
		view.getTextToTextField().setText(model.getTextTo());

		view.getTextPostPrefixTextField().setText(model.getPostPrefix());
		view.getFindFilesTextField().setText(model.getFindExtn());

		view.getMaxTasksTextField().setText(String.valueOf(model.getMaxTasks()));
		view.getMaxSubTaskTextField().setText(String.valueOf(model.getMaxSubTasks()));

		view.getDelaySockReadMsTextField().setText(String.valueOf(model.getDelaySockReadMs()));
		view.getDelayFilesMsTextField().setText(String.valueOf(model.getDelayFilesMs()));
	}


	public void initController() {
		view.getBaseTextField().addActionListener(e -> updateBaseDir());
		view.getBaseTextField().addKeyListener((KeyReleasedListener) e -> updateBaseDir());

		view.getHelperWebTextField().addActionListener(e -> updateHelperWeb());
		view.getHelperWebTextField().addKeyListener((KeyReleasedListener) e -> updateHelperWeb());
		view.getHelperTextTextField().addActionListener(e -> updateHelperText());
		view.getHelperTextTextField().addKeyListener((KeyReleasedListener) e -> updateHelperText());
		view.getHelperDirectoryTextField().addActionListener(e -> updateOpenDirectory());
		view.getHelperDirectoryTextField().addKeyListener((KeyReleasedListener) e -> updateOpenDirectory());

		view.getNumberToTextField().addActionListener(e -> updateNumberTo());
		view.getNumberToTextField().addKeyListener((KeyReleasedListener) e -> updateNumberTo());
		view.getNumberFromTextField().addActionListener(e -> updateNumberFrom());
		view.getNumberFromTextField().addKeyListener((KeyReleasedListener) e -> updateNumberFrom());
		view.getNumberPadTextField().addActionListener(e -> updateNumberPad());
		view.getNumberPadTextField().addKeyListener((KeyReleasedListener) e -> updateNumberPad());

		view.getTextToTextField().addActionListener(e -> updateTextTo());
		view.getTextToTextField().addKeyListener((KeyReleasedListener) e -> updateTextTo());
		view.getTextFromTextField().addActionListener(e -> updateTextFrom());
		view.getTextFromTextField().addKeyListener((KeyReleasedListener) e -> updateTextFrom());

		view.getTextPostPrefixTextField().addActionListener(e -> updatePostPrefix());
		view.getTextPostPrefixTextField().addKeyListener((KeyReleasedListener) e -> updatePostPrefix());
		view.getFindFilesTextField().addActionListener(e -> updateFindFiles());
		view.getFindFilesTextField().addKeyListener((KeyReleasedListener) e -> updateFindFiles());

		view.getMaxTasksTextField().addActionListener(e -> updateMaxTasks());
		view.getMaxTasksTextField().addKeyListener((KeyReleasedListener) e -> updateMaxTasks());
		view.getMaxSubTaskTextField().addActionListener(e -> updateMaxSubTask());
		view.getMaxSubTaskTextField().addKeyListener((KeyReleasedListener) e -> updateMaxSubTask());

		view.getDelaySockReadMsTextField().addActionListener(e -> updateDelaySockReadMs());
		view.getDelaySockReadMsTextField().addKeyListener((KeyReleasedListener) e -> updateDelaySockReadMs());
		
		view.getDelayFilesMsTextField().addActionListener(e -> updateDelayFilesMs());
		view.getDelayFilesMsTextField().addKeyListener((KeyReleasedListener) e -> updateDelayFilesMs());

		view.getSaveButton().addActionListener(e -> saveConfig());
	}

	private int toInt(String num, int def) {
		try {
			return Integer.parseInt(num);
		} catch (NumberFormatException e) {
			return def;
		}
	}

	private void updateBaseDir() {
		editingModel.setBaseDir(view.getBaseTextField().getText());
	}

	private void updateHelperWeb() {
		editingModel.setHelperWeb(view.getHelperWebTextField().getText());
	}

	private void updateHelperText() {
		editingModel.setHelperText(view.getHelperTextTextField().getText());
	}

	private void updateOpenDirectory() {
		editingModel.setOpenDirectory(view.getHelperDirectoryTextField().getText());
	}

	private void updateNumberTo() {
		editingModel.setNumberTo(toInt(view.getNumberToTextField().getText(), 1));
	}

	private void updateNumberFrom() {
		editingModel.setNumberFrom(toInt(view.getNumberFromTextField().getText(), 15));
	}

	private void updateNumberPad() {
		editingModel.setNumberPad(toInt(view.getNumberPadTextField().getText(), 2));
	}

	private void updateTextTo() {
		editingModel.setTextTo(view.getTextToTextField().getText());
	}

	private void updateTextFrom() {
		editingModel.setTextFrom(view.getTextFromTextField().getText());
	}

	private void updatePostPrefix() {
		editingModel.setPostPrefix(view.getTextPostPrefixTextField().getText());
	}

	private void updateFindFiles() {
		editingModel.setFindExtn(view.getFindFilesTextField().getText());
	}

	private void updateMaxTasks() {
		editingModel.setMaxTasks(toInt(view.getMaxTasksTextField().getText(), 2));
	}

	private void updateMaxSubTask() {
		editingModel.setMaxSubTasks(toInt(view.getMaxSubTaskTextField().getText(), 2));
	}

	private void updateDelaySockReadMs() {
		editingModel.setDelaySockReadMs(toInt(view.getDelaySockReadMsTextField().getText(), 5000));
	}

	private void updateDelayFilesMs() {
		editingModel.setDelayFilesMs(toInt(view.getDelayFilesMsTextField().getText(), 5000));
	}

	private void saveConfig() {
		saver.save(editingModel);
		model = editingModel.clone();
	}
}
