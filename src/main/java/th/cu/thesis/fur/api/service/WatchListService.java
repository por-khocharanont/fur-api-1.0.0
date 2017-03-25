package th.cu.thesis.fur.api.service;

import java.util.List;

import th.cu.thesis.fur.api.rest.model.WatchListGridRequest;
import th.cu.thesis.fur.api.rest.model.WatchListGridResponse;
import th.cu.thesis.fur.api.service.exception.ServiceException;

public interface WatchListService {

	public WatchListGridResponse getWatchList(WatchListGridRequest request, String userLogin) throws ServiceException;
	
	public void closeUr(List<String> urIdList, String userLogin) throws ServiceException;
	
}
