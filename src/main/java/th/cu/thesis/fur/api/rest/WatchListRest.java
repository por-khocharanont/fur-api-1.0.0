package th.cu.thesis.fur.api.rest;

import static th.cu.thesis.fur.api.util.CommonUtil.HEADER_X_ACIM_USER;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import th.cu.thesis.fur.api.model.AuthUser;
import th.cu.thesis.fur.api.rest.model.UserInfo;
import th.cu.thesis.fur.api.rest.model.WatchListGridRequest;
import th.cu.thesis.fur.api.rest.model.WatchListGridResponse;
import th.cu.thesis.fur.api.service.UserService;
import th.cu.thesis.fur.api.service.WatchListService;
import th.cu.thesis.fur.api.service.exception.ServiceException;

@RestController
@RequestMapping("/watchlist")
public class WatchListRest extends AbstractRestController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private WatchListService watchlistService;
	
	@RequestMapping(value = "/{username}", method = RequestMethod.POST)
	@ResponseBody
	public UserInfo authenticationUser(@RequestBody AuthUser authUser) throws ServiceException {
		return userService.authUser(authUser);
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
	@ResponseBody
	public WatchListGridResponse getWatchList(@RequestBody WatchListGridRequest request, @RequestHeader(HEADER_X_ACIM_USER) String userLogin) throws ServiceException{
		
		return watchlistService.getWatchList(request, userLogin);
		
	}
	
	@RequestMapping(value = "/ur/close", method = RequestMethod.POST)
	@ResponseBody
	public void closeUr(@RequestBody List<String> urIdList, @RequestHeader(HEADER_X_ACIM_USER) String userLogin) throws ServiceException {
				
		watchlistService.closeUr(urIdList, userLogin);
	}
	
}
