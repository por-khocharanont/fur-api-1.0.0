package th.cu.thesis.fur.api.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import th.cu.thesis.fur.api.model.ApplicationAuthentication;
import th.cu.thesis.fur.api.model.UrStep;
import th.cu.thesis.fur.api.model.User;
import th.cu.thesis.fur.api.model.UserRequestInfo;
import th.cu.thesis.fur.api.repository.ApplicationAuthenticationRepository;
import th.cu.thesis.fur.api.repository.UserAppRoleRepository;
import th.cu.thesis.fur.api.repository.UserRepository;
import th.cu.thesis.fur.api.repository.UserRequestApproveHistoryRepository;
import th.cu.thesis.fur.api.repository.UserRequestForUserRepository;
import th.cu.thesis.fur.api.repository.UserRequestRepository;
import th.cu.thesis.fur.api.repository.UserRequestStepApproveRepository;
import th.cu.thesis.fur.api.rest.model.TodoListInfoGridRequest;
import th.cu.thesis.fur.api.rest.model.TodoListInfoGridResponse;
import th.cu.thesis.fur.api.service.CommonService;
import th.cu.thesis.fur.api.service.TodoListService;
import th.cu.thesis.fur.api.service.exception.ServiceException;
import th.cu.thesis.fur.api.util.CommonUtil;

@Service("todoListService")
public class TodoListServiceImpl implements TodoListService {

	@Autowired
	UserRequestRepository userRequestRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserRequestApproveHistoryRepository userRequestApproveHistoryRepository;

	@Autowired
	UserRequestStepApproveRepository userRequestStepApproveRepository;

	@Autowired
	UserRequestForUserRepository userRequestForUserRepository;

	@Autowired
	UserAppRoleRepository userAppRoleRepository;

	@Autowired
	ApplicationAuthenticationRepository applicationAuthenticationRepository;

	@Autowired
	private CommonService commonService;

	static final String APPROVE_TYPE_ALL = "1";
	static final String APPROVE_TYPE_MINIMUM = "2";
	static final String APPROVE_TYPE_SEQUENCE = "3";
	static final String APPROVE_TYPE_SEQUENCE_TEAM = "4";
	static final String APPROVE_TYPE_PARALLEL = "5";
	static final String UR_STEP_WAITING_FOR_CUSTODIAN = "5";
	static final String UR_STEP_WAITING_FOR_CLOSE = "6";

	static final String VALUE_0 = "0";
	static final String VALUE_1 = "1";

	static final String FLOW_TERMINATE_F017 = "F017";
	static final String FLOW_TERMINATE_F018 = "F018";
	static final String FLOW_TERMINATE_F019 = "F019";
	static final String FLOW_TERMINATE_F020 = "F020";
	static final String FLOW_TERMINATE_F021 = "F021";
	static final String FLOW_TERMINATE_F022 = "F022";
	static final String FLOW_TERMINATE_F023 = "F023";
	static final String FLOW_TERMINATE_F024 = "F024";
	static final String FLOW_TERMINATE_F025 = "F025";
	static final String FLOW_TERMINATE_F026 = "F026";
	static final String FLOW_TERMINATE_F027 = "F027";
	static final String FLOW_TERMINATE_F028 = "F028";

	static final String AUTHEN_TYPE_NAME_TOKEN = "1";

	static final String ROLE_DEFAULT = "Default";

	static final String APPROVE_APPROVE = "1";
	static final String APPROVE_REJECT = "2";

	static final String PROCESS_STATUS = "1";
	static final String COMPLETE_STATUS = "2";
	static final String REJECT_STATUS = "3";

	static final String REQUEST_TYPE_NEW = "1";
	static final String REQUEST_TYPE_TERMINATE = "2";
	static final String REQUEST_TYPE_CHANGE = "3";

	static final String CHANGE_ROLE = "CH1R1";

	static final String TODOLIST_PROCESS = "1";
	static final String TODOLIST_COMPLETE = "2";
	
	static final String COMPLETE = "Complete";
	static final String REJECT = "Reject";
	@Override
	public TodoListInfoGridResponse getTodoList(TodoListInfoGridRequest request) throws ServiceException {
		TodoListInfoGridResponse response = new TodoListInfoGridResponse();
		String username = request.getUsername();
		String urStepType = request.getUrStep();
		String urId = request.getUrId();
		String requestNo = request.getRequestNo();
		String type = request.getType();

		urId = CommonUtil.toSqlLikeFormat(urId);
		requestNo = CommonUtil.toSqlLikeFormat(requestNo);

		Date startDate = request.getStartDate();
		Date endDate = request.getEndDate();

		if (endDate != null) {
			endDate = CommonUtil.getDateForSearchAtEndDate(endDate);
		}

		String orderBy = request.getSidx() + " " + request.getSord();

		int page = request.getPage();
		int size = request.getRows();
		int startRow = ((page - 1) * size) + 1;
		int endRow = 0;

		response.setTotal(0);

		try {
			
			List<UserRequestInfo> userRequestInfoList = new ArrayList<UserRequestInfo>();

			int totalRecord = 0;
			
			if (TODOLIST_PROCESS.equals(type)) {
				List<UrStep> urStepList = userRequestRepository.getURStepIdProcess(username, urStepType, urId,
						requestNo, startDate, endDate);

				List<String> urStepIdListCanApprove = new ArrayList<String>();
				for (UrStep urStep : urStepList) {
					String approveType = urStep.getApproveType();
					String urStepId = urStep.getUrStepId();
					Integer urStepApproveList = userRequestRepository.getCountUrApproveByUsernameAndType(approveType,
							username, urStepId);

					if (urStepApproveList != 0) {
						urStepIdListCanApprove.add(urStepId);
					}
				}


				if (urStepIdListCanApprove.size() != 0) {
					StringBuilder stringBuilder = new StringBuilder();
					for (int i = 0; i < urStepIdListCanApprove.size(); i++) {
						if (i != 0) {
							stringBuilder.append(" ,");
						}
						stringBuilder.append("'" + urStepIdListCanApprove.get(i) + "'");
					}
					String urStepIdList = stringBuilder.toString();

					totalRecord = userRequestRepository.getCountURByUrStepIdList(urStepIdList, startRow, endRow);

					if (totalRecord == size) {
						endRow = totalRecord;
					} else {
						endRow = (startRow + size) - 1;
					}

					userRequestInfoList = userRequestRepository.getURByUrStepIdList(urStepIdList, startRow, endRow,
							orderBy);

					for (UserRequestInfo userReqInfo : userRequestInfoList) {

						User user = userRepository.getUserProfileByUsername(userReqInfo.getRequestBy());

						userReqInfo.setRequestByFullName(user.getEnfullname());

						userReqInfo.setUrApprove(VALUE_1);
						userReqInfo.setUrReject(VALUE_1);
						userReqInfo.setUrToken(VALUE_0);
						userReqInfo.setUrDefault(VALUE_0);

						if (UR_STEP_WAITING_FOR_CLOSE.equals(userReqInfo.getUrStep())) {
							userReqInfo.setUrReject(VALUE_0);
						}

						if (FLOW_TERMINATE_F017.equals(userReqInfo.getFlowId())
								|| FLOW_TERMINATE_F018.equals(userReqInfo.getFlowId())
								|| FLOW_TERMINATE_F019.equals(userReqInfo.getFlowId())
								|| FLOW_TERMINATE_F020.equals(userReqInfo.getFlowId())
								|| FLOW_TERMINATE_F021.equals(userReqInfo.getFlowId())
								|| FLOW_TERMINATE_F022.equals(userReqInfo.getFlowId())
								|| FLOW_TERMINATE_F023.equals(userReqInfo.getFlowId())
								|| FLOW_TERMINATE_F024.equals(userReqInfo.getFlowId())
								|| FLOW_TERMINATE_F025.equals(userReqInfo.getFlowId())
								|| FLOW_TERMINATE_F026.equals(userReqInfo.getFlowId())
								|| FLOW_TERMINATE_F027.equals(userReqInfo.getFlowId())
								|| FLOW_TERMINATE_F028.equals(userReqInfo.getFlowId())) {
							userReqInfo.setUrReject(VALUE_0);
						}

						// check role default
						if (UR_STEP_WAITING_FOR_CUSTODIAN.equals(userReqInfo.getUrStep())) {
							userReqInfo.setUrReject(VALUE_0);
							userReqInfo.setUrApprove(VALUE_0);
							userReqInfo.setUrToken(VALUE_1);
							userReqInfo.setUrDefault(VALUE_1);

						}
						
						// change urStep to text before send
						String urStepText = commonService.getMessage("ur.step." + userReqInfo.getUrStep());
						userReqInfo.setUrStep(urStepText);
					}

				}

			}

			if (TODOLIST_COMPLETE.equals(type)) {
				totalRecord = userRequestRepository.getCountURApproved(username, urStepType, urId, requestNo,
						startDate, endDate);

				if (totalRecord == size) {
					endRow = totalRecord;
				} else {
					endRow = (startRow + size) - 1;
				}

				userRequestInfoList = userRequestRepository.getURApproved(username, urStepType, urId, requestNo,
						startDate, endDate, startRow, endRow, orderBy);
				
				for(UserRequestInfo userReqInfo : userRequestInfoList){
					userReqInfo.setRequestByFullName(userReqInfo.getRequestBy());
					String urStepText = "";
					if(PROCESS_STATUS.equals(userReqInfo.getStatus())){
						
						UrStep urStep = userRequestRepository.getUrStepIdProcessByUrId(userReqInfo.getUrId());
						urStepText = commonService.getMessage("ur.step." + urStep.getUrStep());
					}


					if(COMPLETE_STATUS.equals(userReqInfo.getStatus())){
						urStepText = COMPLETE;
					}

					if(REJECT_STATUS.equals(userReqInfo.getStatus())){
						urStepText = REJECT;
					}
					
					userReqInfo.setUrStep(urStepText);
					
				}
				
			}
			

			int total = 0;
			if (totalRecord % size == 0) {
				total = totalRecord / size;
			} else {
				total = (totalRecord / size) + 1;
			}

			response.setTotal(total);

			response.setPage(request.getPage());
			response.setRecords(totalRecord);
			response.setRows(userRequestInfoList);

		} catch (RuntimeException e) {
			throw ServiceException.get500DBError(e);
		}
		return response;
	}

}
