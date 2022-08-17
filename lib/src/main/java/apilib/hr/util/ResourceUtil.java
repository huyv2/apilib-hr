package apilib.hr.util;

import java.util.ResourceBundle;

public class ResourceUtil {
	public static String getApplicationConfiguration(String key) {
		String result = "";
		
		try {
	        ResourceBundle applicationResourceBundle = ResourceBundle.getBundle("application");
	        result = applicationResourceBundle.getString(key);
		} catch(Exception e) {
			
		}
		
		return result;
	}
}
