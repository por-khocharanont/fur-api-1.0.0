package th.cu.thesis.fur.api.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import th.cu.thesis.fur.api.rest.model.ApplicationReportParams;
import th.cu.thesis.fur.api.rest.model.BaseGridResponse;
import th.cu.thesis.fur.api.rest.model.EligibleReportParams;
import th.cu.thesis.fur.api.rest.model.UrReportParams;
import th.cu.thesis.fur.api.service.ReportService;
import th.cu.thesis.fur.api.service.exception.ServiceException;

@RestController
@RequestMapping(value="/report")
public class ReportRest {
	
	@Autowired
	ReportService reportService;
	
	@RequestMapping(value = "/serach/applist", method = RequestMethod.POST)
	public @ResponseBody BaseGridResponse searchReport(@RequestBody Map<String,String> param) throws ServiceException {
		try{
			return reportService.searchReport(param);
		}catch(Exception e){
			e.printStackTrace();
			throw ServiceException.get500SystemError(e);
		}
	}
	
	@RequestMapping(value = "/serach/urlist", method = RequestMethod.POST)
	public @ResponseBody BaseGridResponse searchUr(@RequestBody Map<String,String> param) throws ServiceException {
		try{
			return reportService.searchUr(param);
		}catch(Exception e){
			e.printStackTrace();
			throw ServiceException.get500SystemError(e);
		}
	}
	@RequestMapping(value = "/serach/eligiblelist", method = RequestMethod.POST)
	public @ResponseBody BaseGridResponse searchEligible(@RequestBody Map<String,String> param) throws ServiceException {
		try{
			return reportService.searchEligible(param);
		}catch(Exception e){
			e.printStackTrace();
			throw ServiceException.get500SystemError(e);
		}
	}
	@RequestMapping(value = "/export/applicationDefault", method = RequestMethod.POST)
	public @ResponseBody byte[] exportExcelApplicationDefault(@RequestBody ApplicationReportParams paramsApp) throws ServiceException {
		return reportService.exportExcelApplicationDefault(paramsApp);
	}
	
	@RequestMapping(value = "/export/UrDefault", method = RequestMethod.POST)
	public @ResponseBody byte[] exportExcelUrDefault(@RequestBody UrReportParams paramsUr) throws ServiceException {
		return reportService.exportExcelUrDefault(paramsUr);
	}
	
	@RequestMapping(value = "/export/eligibleDefault", method = RequestMethod.POST)
	public @ResponseBody byte[] exportExcelEligibleDefault(@RequestBody EligibleReportParams paramsUr) throws ServiceException {
		return reportService.exportExcelEligibleDefault(paramsUr);
	}
	@RequestMapping(value = "/combobox/flowsall", method = RequestMethod.POST)
	public @ResponseBody List<Map<String, String>> getFlows() throws ServiceException {
		try{
			return reportService.getFlows();
		}catch(Exception e){
			e.printStackTrace();
			throw ServiceException.get500SystemError(e);
		}
	}
}
