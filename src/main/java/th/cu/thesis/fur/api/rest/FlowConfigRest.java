package th.cu.thesis.fur.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import th.cu.thesis.fur.api.rest.model.FlowconfigGridRequest;
import th.cu.thesis.fur.api.rest.model.FlowconfigGridResponse;
import th.cu.thesis.fur.api.service.FlowConfigService;
import th.cu.thesis.fur.api.service.exception.ServiceException;

@RestController
public class FlowConfigRest {
	
	@Autowired
	FlowConfigService flowConfigService;
	
	@RequestMapping(value = "/flowconfig/search", method = RequestMethod.POST)
	@ResponseBody
	public FlowconfigGridResponse getSearchApplications(@RequestBody FlowconfigGridRequest flowconfigGridRequest) throws ServiceException {
		FlowconfigGridResponse flowconfigGridResponse = flowConfigService.getSearchFlowconfig(flowconfigGridRequest);
		return flowconfigGridResponse;
	}
}
