package RESTfulAPITesting.Utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {
	
	public static Properties prop;

	public ConfigurationReader() throws IOException{
		prop=new Properties();
		FileInputStream ip=new FileInputStream("C:\\Users\\Qkan\\Desktop\\JAVA\\Java_Codes\\RESTfulAPITesting\\config.properties");
		prop.load(ip);
	}

}
