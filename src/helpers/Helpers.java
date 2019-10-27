package helpers;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class Helpers {

	public static void render(HttpServletRequest request, HttpServletResponse response, String path, Map<String, Object> root){
		Configuration conf = (Configuration)request.getServletContext().getAttribute("conf");
		try{
			Template temp = conf.getTemplate(path);
			try{
				temp.process(root, response.getWriter());
			}
			catch (TemplateException e){
				e.printStackTrace();
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}

}
