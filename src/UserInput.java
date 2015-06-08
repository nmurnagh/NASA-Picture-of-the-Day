import javax.swing.JOptionPane;

public class UserInput {

	public static String getPath() {

		String filePath = JOptionPane
				.showInputDialog("Please input filepath for download location");
		return filePath;

	}

	public static String getApi() {

		String apiKey = JOptionPane
				.showInputDialog("Please input NASA API key");
		return "api_key=" + apiKey;

	}
}
