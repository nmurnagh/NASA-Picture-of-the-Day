import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class MainApplication {

	public static void main(String[] args) {

		String downloadPath = new String();
		String apikey = new String();
		String url = "https://api.nasa.gov/planetary/apod?";
		Image image = null;

		try {
			
			//Reads in saved download path and API key
			FileInputStream fs = new FileInputStream("data.ser");
			ObjectInputStream os = new ObjectInputStream(fs);
			downloadPath = os.readUTF();
			apikey = os.readUTF();
			os.close();

		} catch (FileNotFoundException e1) {

			//If file is not found asks user for the path and API key
			downloadPath = UserInput.getPath();
			apikey = UserInput.getApi();

			try {
				
				//Saves the users inputs to a file
				FileOutputStream fo = new FileOutputStream("data.ser");
				ObjectOutputStream oo = new ObjectOutputStream(fo);
				oo.writeUTF(downloadPath);
				oo.writeUTF(apikey);
				oo.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Contacts NASAs API and gets the URL of the photo of the day
		//Then downloads and saves the photo to the specified folder
		try {
			
			URL nasaurl = new URL(url + apikey);

			BufferedReader br = new BufferedReader(new InputStreamReader(
					nasaurl.openStream()));

			JSONObject obj = (JSONObject) JSONValue.parse(br.readLine());

			image = ImageIO.read(new URL((String) obj.get("url")));

			ImageIO.write((RenderedImage) image, "png", new File(downloadPath
					+ (String) obj.get("title") + ".png"));

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
