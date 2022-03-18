package ravi.core.workflows;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;

import ravi.core.services.TestService;

@Component(service= {WorkflowProcess.class},
			property= {"description=test",
					"lable=RaviTestProceesStep"})
public class test implements WorkflowProcess {

	@Reference
	TestService testService;
	
	@Override
	public void execute(WorkItem arg0, WorkflowSession arg1, MetaDataMap arg2) throws WorkflowException {
		Logger log = LoggerFactory.getLogger(test.class);
		try {
			Map<String, String> prop = new HashMap<>();
			prop.put("Name", "RAVI");
			testService.senMail("usernmail", "subject", "template path", prop);
		}catch (Exception e) {
			log.info("EXCEPTION :",e);
		}
		
	}

}
