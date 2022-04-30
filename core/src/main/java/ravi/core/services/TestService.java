package ravi.core.services;

import java.io.InputStream;
import java.util.Map;

import javax.jcr.Node;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.HtmlEmail;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;

import ravi.core.workflows.test;

@Component(service=TestService.class)
public class TestService {
	Logger log = LoggerFactory.getLogger(test.class);
	
	public void senMail(String userMail, String subject, String templatePath, Map<String, String> prop) {
		if(StringUtils.isNotEmpty(templatePath)) {
			HtmlEmail email = new HtmlEmail();
			String htmlString = constructHtmlFromTemplate(prop, templatePath);
			sendMailFinal(userMail, subject, htmlString, e
		}
	}
	
	
	public void sendMailFinal(Object userMail, String subject, String htmlBody, HtmlEmail email) {
		try {
			Personalization per = new Personalization();
			per.addTo(new Email(userMail.toString()));
			Mail mail = new Mail();
			Content content = new Content("text/html",htmlBody);
			mail.addPersonalization(per);
			mail.setSubject(subject);
			mail.addContent(content);
			
			SendGrid sg = new SendGrid("PLACE SEND GRID API KEY HERE");
			
			Request request = new Request();
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
			log.info("STATUS CODE :{}", response.getStatusCode());
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public String constructHtmlFromTemplate(Map<String, String> prop, String emailTemplate) {
		ResourceResolver resolver = null;//GET RESOLVER FROM SYSTEM USER
		StringBuilder emailHtml = new StringBuilder();
		String test = "";
		if(!emailTemplate.isEmpty()) {
			String emailTemplateText = "";
			try {
				Resource templatePath = resolver.getResource("ADD TEMPLATE PATH HERE");
				Node templateNode = templatePath.adaptTo(Node.class);
				ValueMap templateNodeData = templatePath.adaptTo(ValueMap.class);
				if(templateNodeData.containsKey("jcr:data")) {
					test = IOUtils.toString(templateNodeData.get("jcr:data", InputStream.class), "UTF-8");
				}
				for(Map.Entry<String, String> entry : prop.entrySet()) {
					test = test.replace("", "");//Replace dynamic content in template
				}
				emailHtml.append(test);
			}catch (Exception e) {
				// TODO: handle exception
			}
		}
		return null;
	}
	
}
