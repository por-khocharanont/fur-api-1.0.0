package th.cu.thesis.fur.api.service;

import th.cu.thesis.fur.api.model.FlowConfig;
import th.cu.thesis.fur.api.rest.model.FlowconfigGridRequest;
import th.cu.thesis.fur.api.rest.model.FlowconfigGridResponse;
import th.cu.thesis.fur.api.service.exception.ServiceException;



public interface FlowConfigService {
	
	public FlowconfigGridResponse getSearchFlowconfig(FlowconfigGridRequest flowconfigGridRequest) throws ServiceException;
	
	public FlowConfig getApplicationById(Integer flowid) throws ServiceException;
	
	public void insertFlowConfig(FlowConfig insertForm) throws ServiceException;
}
