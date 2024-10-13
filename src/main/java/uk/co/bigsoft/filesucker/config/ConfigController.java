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
		view.getNumberToTextField().setText(String.valueOf(model.getNumberTo()));
		view.getNumberFromTextField().setText(String.valueOf(model.getNumberFrom()));
		view.getNumberPadTextField().setText(String.valueOf(model.getNumberPad()));

	}

	public void initController() {
		view.getNumberToTextField().addActionListener(e -> updateNumberTo());
		view.getNumberFromTextField().addActionListener(e -> updateNumberFrom());
		view.getNumberPadTextField().addActionListener(e -> updateNumberPad());
		
		view.getSaveButton().addActionListener(e -> saveConfig());
	}

	private int toInt(String num, int def) {
		try {
			return Integer.parseInt(num);
		} catch (NumberFormatException e) {
			return def;
		}
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

	private void saveConfig() {
		saver.save(editingModel);
		model = editingModel.clone();
	}
}
