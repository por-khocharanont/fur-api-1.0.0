package th.cu.thesis.fur.api.rest;

import static th.cu.thesis.fur.api.util.CommonUtil.HEADER_X_ACIM_USER;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import th.cu.thesis.fur.api.rest.model.TodoListInfoGridRequest;
import th.cu.thesis.fur.api.rest.model.TodoListInfoGridResponse;
import th.cu.thesis.fur.api.service.TodoListService;
import th.cu.thesis.fur.api.service.exception.ServiceException;

@RestController
@RequestMapping("/todolist")
public class TodoListRest {
	private static final Logger logger = LoggerFactory.getLogger(UserRest.class);
	
	@Autowired
	TodoListService todoListService;
	
	@RequestMapping(value = "/ur", method = RequestMethod.POST)
	public @ResponseBody TodoListInfoGridResponse getTodoList(@RequestBody TodoListInfoGridRequest request,@RequestHeader(HEADER_X_ACIM_USER) String userLogin) throws ServiceException {
		request.setUsername(userLogin);
		return todoListService.getTodoList(request);
	}
	
	
}
