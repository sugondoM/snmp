package process;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

public class SimpleReader {
	
	public String readJsonFile(String filename) throws IOException, URISyntaxException {
		String json = "";
		
		String path = "./"+filename;
		FileInputStream fis = new FileInputStream(path);
		BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
		try {
			
		    StringBuilder sb = new StringBuilder();
		    String line = reader.readLine();
	
		    while (line != null) {
		        sb.append(line);
		        sb.append("\n");
		        line = reader.readLine();
		    }
		    json = sb.toString();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		    reader.close();
		}
		//System.out.println(json);
		return json;
	}
}
