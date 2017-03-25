package th.cu.thesis.fur.api.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import th.cu.thesis.fur.api.model.FlowConfig;
import th.cu.thesis.fur.api.repository.FlowConfigRepository;
import th.cu.thesis.fur.api.rest.model.FlowconfigGridRequest;
import th.cu.thesis.fur.api.rest.model.FlowconfigGridResponse;
import th.cu.thesis.fur.api.service.FlowConfigService;
import th.cu.thesis.fur.api.service.exception.ServiceException;
import th.cu.thesis.fur.api.util.CommonUtil;

@Service("flowconfigService")
public class FlowConfigServiceImpl implements FlowConfigService {

	@Autowired
	private FlowConfigRepository flowConfigRepository;
	
	@Override
	public FlowconfigGridResponse getSearchFlowconfig(FlowconfigGridRequest flowconfigGridRequest)
			throws ServiceException {
		FlowconfigGridResponse flowconfigGridResponse = new FlowconfigGridResponse();
		String flowname = CommonUtil.toSqlLikeFormat(flowconfigGridRequest.getColumnFlowname());
		String flowtype = flowconfigGridRequest.getColumnFlowtype();
		String usertype = flowconfigGridRequest.getColumnUsertype();
		int page = flowconfigGridRequest.getPage();
		int size = flowconfigGridRequest.getRows();
		int startRow = ((page - 1) * size) + 1;
		int endRow = 0;
		int totalRecord = flowConfigRepository.getCountSearchFlowConfig(flowname, flowtype, usertype);
			if(totalRecord==size)
			{
				endRow = totalRecord;
			}else{
				endRow = (startRow + size) - 1;
			}
		List<FlowConfig> listFlowconfig = flowConfigRepository.getSearchFlowConfig(flowname, flowtype, usertype, startRow, endRow);
		flowconfigGridResponse.setRecords(totalRecord);
		flowconfigGridResponse.setRows(listFlowconfig);
		flowconfigGridResponse.setPage(flowconfigGridRequest.getPage());
		
		int total = 0;
		if (totalRecord % size == 0) {
			total = totalRecord / size;
		} else {
			total = (totalRecord / size) + 1;
		}
		flowconfigGridResponse.setTotal(total);
		return flowconfigGridResponse;
	}

	@Override
	public FlowConfig getApplicationById(Integer flowid)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertFlowConfig(FlowConfig insertForm) throws ServiceException {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
