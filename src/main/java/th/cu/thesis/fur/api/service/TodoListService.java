package th.cu.thesis.fur.api.service;

import th.cu.thesis.fur.api.rest.model.TodoListInfoGridRequest;
import th.cu.thesis.fur.api.rest.model.TodoListInfoGridResponse;
import th.cu.thesis.fur.api.service.exception.ServiceException;

public interface TodoListService {
	public TodoListInfoGridResponse getTodoList(TodoListInfoGridRequest request) throws ServiceException;
	
}
