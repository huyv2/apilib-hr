package apilib;

import java.io.File;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import apilib.hr.util.ResourceUtil;

public class Application {
    public static void main(String[] args){

    	try {
            String webappDirLocation = "src/main/webapp/";
            Tomcat tomcat = new Tomcat();
	
	        //The port that we should run on can be set into an environment variable
	        //Look for that variable and default to 8080 if it isn't there.
	        String serverPort = ResourceUtil.getApplicationConfiguration("server.port");
	        if(serverPort == null || serverPort.isEmpty()) {
	        	serverPort = "8080";
	        }
	
	        tomcat.setPort(Integer.valueOf(serverPort));
	
	        StandardContext ctx = (StandardContext) tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
	        System.out.println("configuring app with basedir: " + new File("./" + webappDirLocation).getAbsolutePath());

	        // Declare an alternative location for your "WEB-INF/classes" dir
	        // Servlet 3.0 annotation will work
	        File additionWebInfClasses = new File("");
	        WebResourceRoot resources = new StandardRoot(ctx);
	        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
	                additionWebInfClasses.getAbsolutePath(), "/"));
	        ctx.setResources(resources);

	        tomcat.start();
	        tomcat.getServer().await();
	    	
	    	
	        /*ResourceBundle applicationResourceBundle = ResourceBundle.getBundle("application");
	        String serverPort = applicationResourceBundle.getString("server.port");
	        if(serverPort == null || serverPort.isEmpty()) {
	        	serverPort = "8080";
	        }
	        
	        String appBase = ".";
	        Tomcat tomcat = new Tomcat();
	        tomcat.setBaseDir(createTempDir(serverPort));
	        tomcat.setPort(Integer.valueOf(serverPort));
	        tomcat.getHost().setAppBase(appBase);
	        tomcat.addWebapp("", appBase);
	        tomcat.start();
	        tomcat.getServer().await();*/
    	} catch(Exception e) {
    		
    	}
    }
    
    // based on AbstractEmbeddedServletContainerFactory
    /*private static String createTempDir(String serverPort) throws IOException {
            File tempDir = File.createTempFile("tomcat.", "." + serverPort);
            tempDir.delete();
            tempDir.mkdir();
            tempDir.deleteOnExit();
            return tempDir.getAbsolutePath();
    }*/
}
