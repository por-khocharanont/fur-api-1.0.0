package th.cu.thesis.fur.api.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import th.cu.thesis.fur.api.model.AuthUser;
import th.cu.thesis.fur.api.model.RolePermission;
import th.cu.thesis.fur.api.model.User;
import th.cu.thesis.fur.api.model.UserMenu;
import th.cu.thesis.fur.api.rest.model.UserApplicationInfoGridRequest;
import th.cu.thesis.fur.api.rest.model.UserApplicationInfoGridResponse;
import th.cu.thesis.fur.api.rest.model.UserInfo;
import th.cu.thesis.fur.api.rest.model.UserTokenInfoGridResponse;
import th.cu.thesis.fur.api.service.CommonService;
import th.cu.thesis.fur.api.service.UserService;
import th.cu.thesis.fur.api.service.exception.ServiceException;

@RestController
@RequestMapping("/users")
public class UserRest extends AbstractRestController{
	private static final Logger logger = LoggerFactory.getLogger(UserRest.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommonService commonService;
	
	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public @ResponseBody UserInfo authenticationUser(@RequestBody AuthUser authUser) throws ServiceException {
		return userService.authUser(authUser);
	}
	
	@RequestMapping(value = "/userprofile/{username}", method = RequestMethod.GET)
	public @ResponseBody User getUserProfile(@PathVariable("username") String username) throws ServiceException {
		return userService.getUserProfileByUsername(username);
	}
	
	@RequestMapping(value = "/userprofile/userId/{userId}", method = RequestMethod.GET)
	public @ResponseBody User getUserProfileByUserId(@PathVariable("userId") String userId) throws ServiceException {
		return userService.getUserProfileByUserId(userId);
	}
	
	@RequestMapping(value = "/applications/{type}", method = RequestMethod.POST)
	public @ResponseBody UserApplicationInfoGridResponse getApplicationByType(@PathVariable("type") String type,@RequestBody UserApplicationInfoGridRequest request) throws ServiceException {
		return userService.getApplicationByType(type,request);
	}
	
	@RequestMapping(value = "/tokens", method = RequestMethod.POST)
	public @ResponseBody UserTokenInfoGridResponse getApplicationTokenByUser(@RequestBody UserApplicationInfoGridRequest request) throws ServiceException {
		return userService.getApplicationByUserToken(request);
	}
	
	@RequestMapping(value = "/userlists/{username}", method = RequestMethod.GET)
	public @ResponseBody List<User> getUserProfileList(@PathVariable String username) throws ServiceException {
		return userService.getUserProfileList(username);
	}
	
	@RequestMapping(value = "/menu/{userId}", method = RequestMethod.GET)
	public @ResponseBody UserMenu getUserMenu(@PathVariable String userId) throws ServiceException {
		return commonService.getUserMenuByUserId(userId);
	}
	
	@RequestMapping(value = "/role/{userId}", method = RequestMethod.GET)
	public @ResponseBody List<RolePermission> getRole(@PathVariable String userId) throws ServiceException {
		return commonService.getRoleByUserId(userId);
	}
}
