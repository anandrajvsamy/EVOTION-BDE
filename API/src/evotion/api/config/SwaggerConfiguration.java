package evotion.config;

import io.swagger.jaxrs.config.BeanConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class SwaggerConfiguration extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		BeanConfig beanConfig = new BeanConfig();
		Properties prop = new Properties();
		InputStream input = null;
		ClassLoader classLoader = getClass().getClassLoader();
		try {
			
			input = classLoader.getResourceAsStream("evotion.properties");
			prop.load(input);
			beanConfig.setHost(prop.getProperty("db-url"));
			
		}catch (IOException ex) {
			ex.printStackTrace();
		}
	
		
		beanConfig.setTitle("Evotion API");
		beanConfig.setVersion("1.0");
		beanConfig.setSchemes(new String[]{"http"});
		
		beanConfig.setBasePath("/EvotionAPI/api");
		beanConfig.setResourcePackage("evotion.api,evotion.model");
		beanConfig.setScan(true);
		beanConfig.setDescription("");
		}
}