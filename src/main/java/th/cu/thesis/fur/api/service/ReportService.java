package th.cu.thesis.fur.api.service;

import java.util.List;
import java.util.Map;

import th.cu.thesis.fur.api.rest.model.ApplicationReportParams;
import th.cu.thesis.fur.api.rest.model.BaseGridResponse;
import th.cu.thesis.fur.api.rest.model.EligibleReportParams;
import th.cu.thesis.fur.api.rest.model.UrReportParams;
import th.cu.thesis.fur.api.service.exception.ServiceException;

public interface ReportService {
	public BaseGridResponse searchReport(Map<String, String> param) throws ServiceException;

	public BaseGridResponse searchUr(Map<String, String> param)throws ServiceException;

	public byte[] exportExcelApplicationDefault(ApplicationReportParams paramsApp)throws ServiceException;

	public byte[] exportExcelUrDefault(UrReportParams paramsUr)throws ServiceException;

	public BaseGridResponse searchEligible(Map<String, String> param)throws ServiceException;

	public byte[] exportExcelEligibleDefault(EligibleReportParams paramsUr)throws ServiceException;

	public List<Map<String, String>> getFlows()throws ServiceException;
}
