
/**
 * WS_OM_OMServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package th.cu.thesis.fur.api.service.ws.client;

    /**
     *  WS_OM_OMServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class WS_OM_OMServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public WS_OM_OMServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public WS_OM_OMServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for oM_WS_ListPinDuplicate method
            * override this method for handling normal response from oM_WS_ListPinDuplicate operation
            */
           public void receiveResultoM_WS_ListPinDuplicate(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListPinDuplicateResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListPinDuplicate operation
           */
            public void receiveErroroM_WS_ListPinDuplicate(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmployeeProfileName method
            * override this method for handling normal response from oM_WS_ListEmployeeProfileName operation
            */
           public void receiveResultoM_WS_ListEmployeeProfileName(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmployeeProfileNameResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmployeeProfileName operation
           */
            public void receiveErroroM_WS_ListEmployeeProfileName(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetEmployeeProfile method
            * override this method for handling normal response from oM_WS_GetEmployeeProfile operation
            */
           public void receiveResultoM_WS_GetEmployeeProfile(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetEmployeeProfileResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetEmployeeProfile operation
           */
            public void receiveErroroM_WS_GetEmployeeProfile(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListCompany method
            * override this method for handling normal response from oM_WS_ListCompany operation
            */
           public void receiveResultoM_WS_ListCompany(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListCompanyResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListCompany operation
           */
            public void receiveErroroM_WS_ListCompany(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListAllEmpProfileAndApprover method
            * override this method for handling normal response from oM_WS_ListAllEmpProfileAndApprover operation
            */
           public void receiveResultoM_WS_ListAllEmpProfileAndApprover(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListAllEmpProfileAndApproverResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListAllEmpProfileAndApprover operation
           */
            public void receiveErroroM_WS_ListAllEmpProfileAndApprover(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmpNewStatusByDate method
            * override this method for handling normal response from oM_WS_ListEmpNewStatusByDate operation
            */
           public void receiveResultoM_WS_ListEmpNewStatusByDate(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmpNewStatusByDateResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmpNewStatusByDate operation
           */
            public void receiveErroroM_WS_ListEmpNewStatusByDate(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListGradeDesc method
            * override this method for handling normal response from oM_WS_ListGradeDesc operation
            */
           public void receiveResultoM_WS_ListGradeDesc(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListGradeDescResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListGradeDesc operation
           */
            public void receiveErroroM_WS_ListGradeDesc(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmployeeProfileByPrefixEngName method
            * override this method for handling normal response from oM_WS_ListEmployeeProfileByPrefixEngName operation
            */
           public void receiveResultoM_WS_ListEmployeeProfileByPrefixEngName(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmployeeProfileByPrefixEngNameResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmployeeProfileByPrefixEngName operation
           */
            public void receiveErroroM_WS_ListEmployeeProfileByPrefixEngName(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmpForFingerScanResigned method
            * override this method for handling normal response from oM_WS_ListEmpForFingerScanResigned operation
            */
           public void receiveResultoM_WS_ListEmpForFingerScanResigned(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmpForFingerScanResignedResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmpForFingerScanResigned operation
           */
            public void receiveErroroM_WS_ListEmpForFingerScanResigned(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListOrganizeLowerByListOrg method
            * override this method for handling normal response from oM_WS_ListOrganizeLowerByListOrg operation
            */
           public void receiveResultoM_WS_ListOrganizeLowerByListOrg(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListOrganizeLowerByListOrgResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListOrganizeLowerByListOrg operation
           */
            public void receiveErroroM_WS_ListOrganizeLowerByListOrg(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListDailySyncEmployeeData method
            * override this method for handling normal response from oM_WS_ListDailySyncEmployeeData operation
            */
           public void receiveResultoM_WS_ListDailySyncEmployeeData(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListDailySyncEmployeeDataResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListDailySyncEmployeeData operation
           */
            public void receiveErroroM_WS_ListDailySyncEmployeeData(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmployeeProfileByPrefixThaiName method
            * override this method for handling normal response from oM_WS_ListEmployeeProfileByPrefixThaiName operation
            */
           public void receiveResultoM_WS_ListEmployeeProfileByPrefixThaiName(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmployeeProfileByPrefixThaiNameResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmployeeProfileByPrefixThaiName operation
           */
            public void receiveErroroM_WS_ListEmployeeProfileByPrefixThaiName(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetEmpOrgLine method
            * override this method for handling normal response from oM_WS_GetEmpOrgLine operation
            */
           public void receiveResultoM_WS_GetEmpOrgLine(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetEmpOrgLineResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetEmpOrgLine operation
           */
            public void receiveErroroM_WS_GetEmpOrgLine(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_SearchEmpDisplayProfile method
            * override this method for handling normal response from oM_WS_SearchEmpDisplayProfile operation
            */
           public void receiveResultoM_WS_SearchEmpDisplayProfile(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_SearchEmpDisplayProfileResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_SearchEmpDisplayProfile operation
           */
            public void receiveErroroM_WS_SearchEmpDisplayProfile(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetEmpProfileByUserstamp method
            * override this method for handling normal response from oM_WS_GetEmpProfileByUserstamp operation
            */
           public void receiveResultoM_WS_GetEmpProfileByUserstamp(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetEmpProfileByUserstampResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetEmpProfileByUserstamp operation
           */
            public void receiveErroroM_WS_GetEmpProfileByUserstamp(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListUpdatedEmployee method
            * override this method for handling normal response from oM_WS_ListUpdatedEmployee operation
            */
           public void receiveResultoM_WS_ListUpdatedEmployee(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListUpdatedEmployeeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListUpdatedEmployee operation
           */
            public void receiveErroroM_WS_ListUpdatedEmployee(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListAllEmpByPositionID method
            * override this method for handling normal response from oM_WS_ListAllEmpByPositionID operation
            */
           public void receiveResultoM_WS_ListAllEmpByPositionID(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListAllEmpByPositionIDResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListAllEmpByPositionID operation
           */
            public void receiveErroroM_WS_ListAllEmpByPositionID(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListOrganize method
            * override this method for handling normal response from oM_WS_ListOrganize operation
            */
           public void receiveResultoM_WS_ListOrganize(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListOrganizeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListOrganize operation
           */
            public void receiveErroroM_WS_ListOrganize(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetEmpProfileByPIN method
            * override this method for handling normal response from oM_WS_GetEmpProfileByPIN operation
            */
           public void receiveResultoM_WS_GetEmpProfileByPIN(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetEmpProfileByPINResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetEmpProfileByPIN operation
           */
            public void receiveErroroM_WS_GetEmpProfileByPIN(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListAllEmpByEmpType method
            * override this method for handling normal response from oM_WS_ListAllEmpByEmpType operation
            */
           public void receiveResultoM_WS_ListAllEmpByEmpType(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListAllEmpByEmpTypeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListAllEmpByEmpType operation
           */
            public void receiveErroroM_WS_ListAllEmpByEmpType(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetStaffCountByOrgCode method
            * override this method for handling normal response from oM_WS_GetStaffCountByOrgCode operation
            */
           public void receiveResultoM_WS_GetStaffCountByOrgCode(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetStaffCountByOrgCodeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetStaffCountByOrgCode operation
           */
            public void receiveErroroM_WS_GetStaffCountByOrgCode(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmpProfileByPinAndUserstamp method
            * override this method for handling normal response from oM_WS_ListEmpProfileByPinAndUserstamp operation
            */
           public void receiveResultoM_WS_ListEmpProfileByPinAndUserstamp(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmpProfileByPinAndUserstampResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmpProfileByPinAndUserstamp operation
           */
            public void receiveErroroM_WS_ListEmpProfileByPinAndUserstamp(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmployeeWfForFlaxiplan method
            * override this method for handling normal response from oM_WS_ListEmployeeWfForFlaxiplan operation
            */
           public void receiveResultoM_WS_ListEmployeeWfForFlaxiplan(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmployeeWfForFlaxiplanResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmployeeWfForFlaxiplan operation
           */
            public void receiveErroroM_WS_ListEmployeeWfForFlaxiplan(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmployeeProfilePowerSearch method
            * override this method for handling normal response from oM_WS_ListEmployeeProfilePowerSearch operation
            */
           public void receiveResultoM_WS_ListEmployeeProfilePowerSearch(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmployeeProfilePowerSearchResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmployeeProfilePowerSearch operation
           */
            public void receiveErroroM_WS_ListEmployeeProfilePowerSearch(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmpProfileAllOrgLevel method
            * override this method for handling normal response from oM_WS_ListEmpProfileAllOrgLevel operation
            */
           public void receiveResultoM_WS_ListEmpProfileAllOrgLevel(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmpProfileAllOrgLevelResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmpProfileAllOrgLevel operation
           */
            public void receiveErroroM_WS_ListEmpProfileAllOrgLevel(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmpResigned method
            * override this method for handling normal response from oM_WS_ListEmpResigned operation
            */
           public void receiveResultoM_WS_ListEmpResigned(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmpResignedResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmpResigned operation
           */
            public void receiveErroroM_WS_ListEmpResigned(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetCompanyByOrgCode method
            * override this method for handling normal response from oM_WS_GetCompanyByOrgCode operation
            */
           public void receiveResultoM_WS_GetCompanyByOrgCode(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetCompanyByOrgCodeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetCompanyByOrgCode operation
           */
            public void receiveErroroM_WS_GetCompanyByOrgCode(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmpDetail method
            * override this method for handling normal response from oM_WS_ListEmpDetail operation
            */
           public void receiveResultoM_WS_ListEmpDetail(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmpDetailResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmpDetail operation
           */
            public void receiveErroroM_WS_ListEmpDetail(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListAllApproverByPin method
            * override this method for handling normal response from oM_WS_ListAllApproverByPin operation
            */
           public void receiveResultoM_WS_ListAllApproverByPin(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListAllApproverByPinResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListAllApproverByPin operation
           */
            public void receiveErroroM_WS_ListAllApproverByPin(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetAllEmpCompanyByUser method
            * override this method for handling normal response from oM_WS_GetAllEmpCompanyByUser operation
            */
           public void receiveResultoM_WS_GetAllEmpCompanyByUser(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetAllEmpCompanyByUserResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetAllEmpCompanyByUser operation
           */
            public void receiveErroroM_WS_GetAllEmpCompanyByUser(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_UpdateADTelephone method
            * override this method for handling normal response from oM_WS_UpdateADTelephone operation
            */
           public void receiveResultoM_WS_UpdateADTelephone(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_UpdateADTelephoneResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_UpdateADTelephone operation
           */
            public void receiveErroroM_WS_UpdateADTelephone(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmpForFingerScan method
            * override this method for handling normal response from oM_WS_ListEmpForFingerScan operation
            */
           public void receiveResultoM_WS_ListEmpForFingerScan(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmpForFingerScanResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmpForFingerScan operation
           */
            public void receiveErroroM_WS_ListEmpForFingerScan(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetStaffCountByPositionID method
            * override this method for handling normal response from oM_WS_GetStaffCountByPositionID operation
            */
           public void receiveResultoM_WS_GetStaffCountByPositionID(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetStaffCountByPositionIDResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetStaffCountByPositionID operation
           */
            public void receiveErroroM_WS_GetStaffCountByPositionID(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_SearchEmployeeDetailAcc method
            * override this method for handling normal response from oM_WS_SearchEmployeeDetailAcc operation
            */
           public void receiveResultoM_WS_SearchEmployeeDetailAcc(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_SearchEmployeeDetailAccResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_SearchEmployeeDetailAcc operation
           */
            public void receiveErroroM_WS_SearchEmployeeDetailAcc(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListTemporary method
            * override this method for handling normal response from oM_WS_ListTemporary operation
            */
           public void receiveResultoM_WS_ListTemporary(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListTemporaryResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListTemporary operation
           */
            public void receiveErroroM_WS_ListTemporary(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListAllPinsByOrgRange method
            * override this method for handling normal response from oM_WS_ListAllPinsByOrgRange operation
            */
           public void receiveResultoM_WS_ListAllPinsByOrgRange(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListAllPinsByOrgRangeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListAllPinsByOrgRange operation
           */
            public void receiveErroroM_WS_ListAllPinsByOrgRange(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListOrgPersInfo method
            * override this method for handling normal response from oM_WS_ListOrgPersInfo operation
            */
           public void receiveResultoM_WS_ListOrgPersInfo(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListOrgPersInfoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListOrgPersInfo operation
           */
            public void receiveErroroM_WS_ListOrgPersInfo(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetApproverByUserName method
            * override this method for handling normal response from oM_WS_GetApproverByUserName operation
            */
           public void receiveResultoM_WS_GetApproverByUserName(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetApproverByUserNameResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetApproverByUserName operation
           */
            public void receiveErroroM_WS_GetApproverByUserName(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListADTelephone method
            * override this method for handling normal response from oM_WS_ListADTelephone operation
            */
           public void receiveResultoM_WS_ListADTelephone(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListADTelephoneResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListADTelephone operation
           */
            public void receiveErroroM_WS_ListADTelephone(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetJobKey method
            * override this method for handling normal response from oM_WS_GetJobKey operation
            */
           public void receiveResultoM_WS_GetJobKey(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetJobKeyResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetJobKey operation
           */
            public void receiveErroroM_WS_GetJobKey(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListAllPinsUnderOrgCode method
            * override this method for handling normal response from oM_WS_ListAllPinsUnderOrgCode operation
            */
           public void receiveResultoM_WS_ListAllPinsUnderOrgCode(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListAllPinsUnderOrgCodeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListAllPinsUnderOrgCode operation
           */
            public void receiveErroroM_WS_ListAllPinsUnderOrgCode(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListPin method
            * override this method for handling normal response from oM_WS_ListPin operation
            */
           public void receiveResultoM_WS_ListPin(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListPinResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListPin operation
           */
            public void receiveErroroM_WS_ListPin(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetLineOrganize method
            * override this method for handling normal response from oM_WS_GetLineOrganize operation
            */
           public void receiveResultoM_WS_GetLineOrganize(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetLineOrganizeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetLineOrganize operation
           */
            public void receiveErroroM_WS_GetLineOrganize(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmployeeProfileByPrefixOrgcode method
            * override this method for handling normal response from oM_WS_ListEmployeeProfileByPrefixOrgcode operation
            */
           public void receiveResultoM_WS_ListEmployeeProfileByPrefixOrgcode(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmployeeProfileByPrefixOrgcodeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmployeeProfileByPrefixOrgcode operation
           */
            public void receiveErroroM_WS_ListEmployeeProfileByPrefixOrgcode(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_SearchEmployeeBaseColumn method
            * override this method for handling normal response from oM_WS_SearchEmployeeBaseColumn operation
            */
           public void receiveResultoM_WS_SearchEmployeeBaseColumn(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_SearchEmployeeBaseColumnResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_SearchEmployeeBaseColumn operation
           */
            public void receiveErroroM_WS_SearchEmployeeBaseColumn(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListPositionIdDuplicate method
            * override this method for handling normal response from oM_WS_ListPositionIdDuplicate operation
            */
           public void receiveResultoM_WS_ListPositionIdDuplicate(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListPositionIdDuplicateResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListPositionIdDuplicate operation
           */
            public void receiveErroroM_WS_ListPositionIdDuplicate(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmployeeProfileByPrefixEmail method
            * override this method for handling normal response from oM_WS_ListEmployeeProfileByPrefixEmail operation
            */
           public void receiveResultoM_WS_ListEmployeeProfileByPrefixEmail(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmployeeProfileByPrefixEmailResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmployeeProfileByPrefixEmail operation
           */
            public void receiveErroroM_WS_ListEmployeeProfileByPrefixEmail(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmployeeByUserStamp method
            * override this method for handling normal response from oM_WS_ListEmployeeByUserStamp operation
            */
           public void receiveResultoM_WS_ListEmployeeByUserStamp(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmployeeByUserStampResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmployeeByUserStamp operation
           */
            public void receiveErroroM_WS_ListEmployeeByUserStamp(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListAllEmpByPosition method
            * override this method for handling normal response from oM_WS_ListAllEmpByPosition operation
            */
           public void receiveResultoM_WS_ListAllEmpByPosition(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListAllEmpByPositionResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListAllEmpByPosition operation
           */
            public void receiveErroroM_WS_ListAllEmpByPosition(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetEmployeeAndMgrByUser method
            * override this method for handling normal response from oM_WS_GetEmployeeAndMgrByUser operation
            */
           public void receiveResultoM_WS_GetEmployeeAndMgrByUser(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetEmployeeAndMgrByUserResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetEmployeeAndMgrByUser operation
           */
            public void receiveErroroM_WS_GetEmployeeAndMgrByUser(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListOrganizeLowerDataSet method
            * override this method for handling normal response from oM_WS_ListOrganizeLowerDataSet operation
            */
           public void receiveResultoM_WS_ListOrganizeLowerDataSet(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListOrganizeLowerDataSetResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListOrganizeLowerDataSet operation
           */
            public void receiveErroroM_WS_ListOrganizeLowerDataSet(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetEmpProfileByNewPin method
            * override this method for handling normal response from oM_WS_GetEmpProfileByNewPin operation
            */
           public void receiveResultoM_WS_GetEmpProfileByNewPin(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetEmpProfileByNewPinResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetEmpProfileByNewPin operation
           */
            public void receiveErroroM_WS_GetEmpProfileByNewPin(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmployeeName method
            * override this method for handling normal response from oM_WS_ListEmployeeName operation
            */
           public void receiveResultoM_WS_ListEmployeeName(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmployeeNameResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmployeeName operation
           */
            public void receiveErroroM_WS_ListEmployeeName(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListOrganizeLevel method
            * override this method for handling normal response from oM_WS_ListOrganizeLevel operation
            */
           public void receiveResultoM_WS_ListOrganizeLevel(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListOrganizeLevelResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListOrganizeLevel operation
           */
            public void receiveErroroM_WS_ListOrganizeLevel(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_SearchAllEmployeeForTime method
            * override this method for handling normal response from oM_WS_SearchAllEmployeeForTime operation
            */
           public void receiveResultoM_WS_SearchAllEmployeeForTime(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_SearchAllEmployeeForTimeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_SearchAllEmployeeForTime operation
           */
            public void receiveErroroM_WS_SearchAllEmployeeForTime(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListAllPositionInfo method
            * override this method for handling normal response from oM_WS_ListAllPositionInfo operation
            */
           public void receiveResultoM_WS_ListAllPositionInfo(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListAllPositionInfoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListAllPositionInfo operation
           */
            public void receiveErroroM_WS_ListAllPositionInfo(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListMovementError method
            * override this method for handling normal response from oM_WS_ListMovementError operation
            */
           public void receiveResultoM_WS_ListMovementError(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListMovementErrorResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListMovementError operation
           */
            public void receiveErroroM_WS_ListMovementError(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ADJUST_DATA method
            * override this method for handling normal response from oM_WS_ADJUST_DATA operation
            */
           public void receiveResultoM_WS_ADJUST_DATA(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ADJUST_DATAResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ADJUST_DATA operation
           */
            public void receiveErroroM_WS_ADJUST_DATA(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetProvidentFund method
            * override this method for handling normal response from oM_WS_GetProvidentFund operation
            */
           public void receiveResultoM_WS_GetProvidentFund(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetProvidentFundResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetProvidentFund operation
           */
            public void receiveErroroM_WS_GetProvidentFund(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListLineApprover method
            * override this method for handling normal response from oM_WS_ListLineApprover operation
            */
           public void receiveResultoM_WS_ListLineApprover(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListLineApproverResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListLineApprover operation
           */
            public void receiveErroroM_WS_ListLineApprover(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListMovementBYDate method
            * override this method for handling normal response from oM_WS_ListMovementBYDate operation
            */
           public void receiveResultoM_WS_ListMovementBYDate(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListMovementBYDateResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListMovementBYDate operation
           */
            public void receiveErroroM_WS_ListMovementBYDate(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_LIstEmpForOM1 method
            * override this method for handling normal response from oM_WS_LIstEmpForOM1 operation
            */
           public void receiveResultoM_WS_LIstEmpForOM1(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_LIstEmpForOM1Response result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_LIstEmpForOM1 operation
           */
            public void receiveErroroM_WS_LIstEmpForOM1(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetChangeApproverK2Summary method
            * override this method for handling normal response from oM_WS_GetChangeApproverK2Summary operation
            */
           public void receiveResultoM_WS_GetChangeApproverK2Summary(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetChangeApproverK2SummaryResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetChangeApproverK2Summary operation
           */
            public void receiveErroroM_WS_GetChangeApproverK2Summary(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_SearchEmployeeInfoForOSM method
            * override this method for handling normal response from oM_WS_SearchEmployeeInfoForOSM operation
            */
           public void receiveResultoM_WS_SearchEmployeeInfoForOSM(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_SearchEmployeeInfoForOSMResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_SearchEmployeeInfoForOSM operation
           */
            public void receiveErroroM_WS_SearchEmployeeInfoForOSM(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmpMovementByDate method
            * override this method for handling normal response from oM_WS_ListEmpMovementByDate operation
            */
           public void receiveResultoM_WS_ListEmpMovementByDate(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmpMovementByDateResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmpMovementByDate operation
           */
            public void receiveErroroM_WS_ListEmpMovementByDate(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListOrganizeUpper method
            * override this method for handling normal response from oM_WS_ListOrganizeUpper operation
            */
           public void receiveResultoM_WS_ListOrganizeUpper(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListOrganizeUpperResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListOrganizeUpper operation
           */
            public void receiveErroroM_WS_ListOrganizeUpper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmployeeProfileMultiPosition method
            * override this method for handling normal response from oM_WS_ListEmployeeProfileMultiPosition operation
            */
           public void receiveResultoM_WS_ListEmployeeProfileMultiPosition(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmployeeProfileMultiPositionResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmployeeProfileMultiPosition operation
           */
            public void receiveErroroM_WS_ListEmployeeProfileMultiPosition(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmployeeProfileForITAM method
            * override this method for handling normal response from oM_WS_ListEmployeeProfileForITAM operation
            */
           public void receiveResultoM_WS_ListEmployeeProfileForITAM(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmployeeProfileForITAMResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmployeeProfileForITAM operation
           */
            public void receiveErroroM_WS_ListEmployeeProfileForITAM(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListAllPinsByPrefixThaiSurname method
            * override this method for handling normal response from oM_WS_ListAllPinsByPrefixThaiSurname operation
            */
           public void receiveResultoM_WS_ListAllPinsByPrefixThaiSurname(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListAllPinsByPrefixThaiSurnameResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListAllPinsByPrefixThaiSurname operation
           */
            public void receiveErroroM_WS_ListAllPinsByPrefixThaiSurname(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListMovementByDtSubArea method
            * override this method for handling normal response from oM_WS_ListMovementByDtSubArea operation
            */
           public void receiveResultoM_WS_ListMovementByDtSubArea(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListMovementByDtSubAreaResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListMovementByDtSubArea operation
           */
            public void receiveErroroM_WS_ListMovementByDtSubArea(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetEmployeeForStaffByUserStamp method
            * override this method for handling normal response from oM_WS_GetEmployeeForStaffByUserStamp operation
            */
           public void receiveResultoM_WS_GetEmployeeForStaffByUserStamp(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetEmployeeForStaffByUserStampResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetEmployeeForStaffByUserStamp operation
           */
            public void receiveErroroM_WS_GetEmployeeForStaffByUserStamp(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmpProfileTimeChangeApprover method
            * override this method for handling normal response from oM_WS_ListEmpProfileTimeChangeApprover operation
            */
           public void receiveResultoM_WS_ListEmpProfileTimeChangeApprover(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmpProfileTimeChangeApproverResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmpProfileTimeChangeApprover operation
           */
            public void receiveErroroM_WS_ListEmpProfileTimeChangeApprover(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmpByOEU method
            * override this method for handling normal response from oM_WS_ListEmpByOEU operation
            */
           public void receiveResultoM_WS_ListEmpByOEU(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmpByOEUResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmpByOEU operation
           */
            public void receiveErroroM_WS_ListEmpByOEU(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmpResignedByDate method
            * override this method for handling normal response from oM_WS_ListEmpResignedByDate operation
            */
           public void receiveResultoM_WS_ListEmpResignedByDate(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmpResignedByDateResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmpResignedByDate operation
           */
            public void receiveErroroM_WS_ListEmpResignedByDate(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmployeeProfileByPrefixPin method
            * override this method for handling normal response from oM_WS_ListEmployeeProfileByPrefixPin operation
            */
           public void receiveResultoM_WS_ListEmployeeProfileByPrefixPin(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmployeeProfileByPrefixPinResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmployeeProfileByPrefixPin operation
           */
            public void receiveErroroM_WS_ListEmployeeProfileByPrefixPin(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_SearchEmployeeDetail method
            * override this method for handling normal response from oM_WS_SearchEmployeeDetail operation
            */
           public void receiveResultoM_WS_SearchEmployeeDetail(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_SearchEmployeeDetailResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_SearchEmployeeDetail operation
           */
            public void receiveErroroM_WS_SearchEmployeeDetail(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmployeeProfileByPrefixOrgname method
            * override this method for handling normal response from oM_WS_ListEmployeeProfileByPrefixOrgname operation
            */
           public void receiveResultoM_WS_ListEmployeeProfileByPrefixOrgname(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmployeeProfileByPrefixOrgnameResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmployeeProfileByPrefixOrgname operation
           */
            public void receiveErroroM_WS_ListEmployeeProfileByPrefixOrgname(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_SearchEmployeeInfo method
            * override this method for handling normal response from oM_WS_SearchEmployeeInfo operation
            */
           public void receiveResultoM_WS_SearchEmployeeInfo(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_SearchEmployeeInfoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_SearchEmployeeInfo operation
           */
            public void receiveErroroM_WS_SearchEmployeeInfo(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetEmployeeProfileByUserstamp method
            * override this method for handling normal response from oM_WS_GetEmployeeProfileByUserstamp operation
            */
           public void receiveResultoM_WS_GetEmployeeProfileByUserstamp(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetEmployeeProfileByUserstampResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetEmployeeProfileByUserstamp operation
           */
            public void receiveErroroM_WS_GetEmployeeProfileByUserstamp(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListOrganizeByOrganizeLevel method
            * override this method for handling normal response from oM_WS_ListOrganizeByOrganizeLevel operation
            */
           public void receiveResultoM_WS_ListOrganizeByOrganizeLevel(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListOrganizeByOrganizeLevelResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListOrganizeByOrganizeLevel operation
           */
            public void receiveErroroM_WS_ListOrganizeByOrganizeLevel(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetOrgInfo method
            * override this method for handling normal response from oM_WS_GetOrgInfo operation
            */
           public void receiveResultoM_WS_GetOrgInfo(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetOrgInfoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetOrgInfo operation
           */
            public void receiveErroroM_WS_GetOrgInfo(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListOrganizeLowerReport method
            * override this method for handling normal response from oM_WS_ListOrganizeLowerReport operation
            */
           public void receiveResultoM_WS_ListOrganizeLowerReport(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListOrganizeLowerReportResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListOrganizeLowerReport operation
           */
            public void receiveErroroM_WS_ListOrganizeLowerReport(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetEmployeePicture method
            * override this method for handling normal response from oM_WS_GetEmployeePicture operation
            */
           public void receiveResultoM_WS_GetEmployeePicture(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetEmployeePictureResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetEmployeePicture operation
           */
            public void receiveErroroM_WS_GetEmployeePicture(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_UpdateMobileNo method
            * override this method for handling normal response from oM_WS_UpdateMobileNo operation
            */
           public void receiveResultoM_WS_UpdateMobileNo(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_UpdateMobileNoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_UpdateMobileNo operation
           */
            public void receiveErroroM_WS_UpdateMobileNo(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmpReOrgStatusByDate method
            * override this method for handling normal response from oM_WS_ListEmpReOrgStatusByDate operation
            */
           public void receiveResultoM_WS_ListEmpReOrgStatusByDate(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmpReOrgStatusByDateResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmpReOrgStatusByDate operation
           */
            public void receiveErroroM_WS_ListEmpReOrgStatusByDate(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetEmployeeInfo method
            * override this method for handling normal response from oM_WS_GetEmployeeInfo operation
            */
           public void receiveResultoM_WS_GetEmployeeInfo(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetEmployeeInfoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetEmployeeInfo operation
           */
            public void receiveErroroM_WS_GetEmployeeInfo(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListAllOrganizeByOrgType method
            * override this method for handling normal response from oM_WS_ListAllOrganizeByOrgType operation
            */
           public void receiveResultoM_WS_ListAllOrganizeByOrgType(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListAllOrganizeByOrgTypeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListAllOrganizeByOrgType operation
           */
            public void receiveErroroM_WS_ListAllOrganizeByOrgType(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListCompanycodeMapping method
            * override this method for handling normal response from oM_WS_ListCompanycodeMapping operation
            */
           public void receiveResultoM_WS_ListCompanycodeMapping(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListCompanycodeMappingResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListCompanycodeMapping operation
           */
            public void receiveErroroM_WS_ListCompanycodeMapping(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmployeeAllPosition method
            * override this method for handling normal response from oM_WS_ListEmployeeAllPosition operation
            */
           public void receiveResultoM_WS_ListEmployeeAllPosition(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmployeeAllPositionResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmployeeAllPosition operation
           */
            public void receiveErroroM_WS_ListEmployeeAllPosition(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmployeeProfileByPrefixEngLastName method
            * override this method for handling normal response from oM_WS_ListEmployeeProfileByPrefixEngLastName operation
            */
           public void receiveResultoM_WS_ListEmployeeProfileByPrefixEngLastName(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmployeeProfileByPrefixEngLastNameResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmployeeProfileByPrefixEngLastName operation
           */
            public void receiveErroroM_WS_ListEmployeeProfileByPrefixEngLastName(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListManagerPrefixThName method
            * override this method for handling normal response from oM_WS_ListManagerPrefixThName operation
            */
           public void receiveResultoM_WS_ListManagerPrefixThName(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListManagerPrefixThNameResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListManagerPrefixThName operation
           */
            public void receiveErroroM_WS_ListManagerPrefixThName(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListPinsUnderOrgCodeRenew method
            * override this method for handling normal response from oM_WS_ListPinsUnderOrgCodeRenew operation
            */
           public void receiveResultoM_WS_ListPinsUnderOrgCodeRenew(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListPinsUnderOrgCodeRenewResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListPinsUnderOrgCodeRenew operation
           */
            public void receiveErroroM_WS_ListPinsUnderOrgCodeRenew(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListALLMovementBYDate method
            * override this method for handling normal response from oM_WS_ListALLMovementBYDate operation
            */
           public void receiveResultoM_WS_ListALLMovementBYDate(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListALLMovementBYDateResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListALLMovementBYDate operation
           */
            public void receiveErroroM_WS_ListALLMovementBYDate(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListManagerUnderOrgCode method
            * override this method for handling normal response from oM_WS_ListManagerUnderOrgCode operation
            */
           public void receiveResultoM_WS_ListManagerUnderOrgCode(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListManagerUnderOrgCodeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListManagerUnderOrgCode operation
           */
            public void receiveErroroM_WS_ListManagerUnderOrgCode(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmployeeInfoForOSM method
            * override this method for handling normal response from oM_WS_ListEmployeeInfoForOSM operation
            */
           public void receiveResultoM_WS_ListEmployeeInfoForOSM(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmployeeInfoForOSMResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmployeeInfoForOSM operation
           */
            public void receiveErroroM_WS_ListEmployeeInfoForOSM(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetEmployeeDetail method
            * override this method for handling normal response from oM_WS_GetEmployeeDetail operation
            */
           public void receiveResultoM_WS_GetEmployeeDetail(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetEmployeeDetailResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetEmployeeDetail operation
           */
            public void receiveErroroM_WS_GetEmployeeDetail(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetImage method
            * override this method for handling normal response from oM_WS_GetImage operation
            */
           public void receiveResultoM_WS_GetImage(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetImageResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetImage operation
           */
            public void receiveErroroM_WS_GetImage(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetAllCompany method
            * override this method for handling normal response from oM_WS_GetAllCompany operation
            */
           public void receiveResultoM_WS_GetAllCompany(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetAllCompanyResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetAllCompany operation
           */
            public void receiveErroroM_WS_GetAllCompany(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmployeeProfileByPrefixThaiLastName method
            * override this method for handling normal response from oM_WS_ListEmployeeProfileByPrefixThaiLastName operation
            */
           public void receiveResultoM_WS_ListEmployeeProfileByPrefixThaiLastName(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmployeeProfileByPrefixThaiLastNameResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmployeeProfileByPrefixThaiLastName operation
           */
            public void receiveErroroM_WS_ListEmployeeProfileByPrefixThaiLastName(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListPosition method
            * override this method for handling normal response from oM_WS_ListPosition operation
            */
           public void receiveResultoM_WS_ListPosition(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListPositionResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListPosition operation
           */
            public void receiveErroroM_WS_ListPosition(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetCostCenter method
            * override this method for handling normal response from oM_WS_GetCostCenter operation
            */
           public void receiveResultoM_WS_GetCostCenter(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetCostCenterResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetCostCenter operation
           */
            public void receiveErroroM_WS_GetCostCenter(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetApprover method
            * override this method for handling normal response from oM_WS_GetApprover operation
            */
           public void receiveResultoM_WS_GetApprover(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetApproverResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetApprover operation
           */
            public void receiveErroroM_WS_GetApprover(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetEmployeeProfileByPIN method
            * override this method for handling normal response from oM_WS_GetEmployeeProfileByPIN operation
            */
           public void receiveResultoM_WS_GetEmployeeProfileByPIN(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetEmployeeProfileByPINResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetEmployeeProfileByPIN operation
           */
            public void receiveErroroM_WS_GetEmployeeProfileByPIN(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmployeeProfileByPrefixOrgdesc method
            * override this method for handling normal response from oM_WS_ListEmployeeProfileByPrefixOrgdesc operation
            */
           public void receiveResultoM_WS_ListEmployeeProfileByPrefixOrgdesc(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmployeeProfileByPrefixOrgdescResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmployeeProfileByPrefixOrgdesc operation
           */
            public void receiveErroroM_WS_ListEmployeeProfileByPrefixOrgdesc(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetEmployeeForStaffByPin method
            * override this method for handling normal response from oM_WS_GetEmployeeForStaffByPin operation
            */
           public void receiveResultoM_WS_GetEmployeeForStaffByPin(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetEmployeeForStaffByPinResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetEmployeeForStaffByPin operation
           */
            public void receiveErroroM_WS_GetEmployeeForStaffByPin(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListLineActingApprover method
            * override this method for handling normal response from oM_WS_ListLineActingApprover operation
            */
           public void receiveResultoM_WS_ListLineActingApprover(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListLineActingApproverResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListLineActingApprover operation
           */
            public void receiveErroroM_WS_ListLineActingApprover(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListAllEmpByJobKey method
            * override this method for handling normal response from oM_WS_ListAllEmpByJobKey operation
            */
           public void receiveResultoM_WS_ListAllEmpByJobKey(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListAllEmpByJobKeyResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListAllEmpByJobKey operation
           */
            public void receiveErroroM_WS_ListAllEmpByJobKey(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetPersDataByUserID method
            * override this method for handling normal response from oM_WS_GetPersDataByUserID operation
            */
           public void receiveResultoM_WS_GetPersDataByUserID(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetPersDataByUserIDResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetPersDataByUserID operation
           */
            public void receiveErroroM_WS_GetPersDataByUserID(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetManager method
            * override this method for handling normal response from oM_WS_GetManager operation
            */
           public void receiveResultoM_WS_GetManager(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetManagerResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetManager operation
           */
            public void receiveErroroM_WS_GetManager(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetAllStaffCountByOrgCode method
            * override this method for handling normal response from oM_WS_GetAllStaffCountByOrgCode operation
            */
           public void receiveResultoM_WS_GetAllStaffCountByOrgCode(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetAllStaffCountByOrgCodeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetAllStaffCountByOrgCode operation
           */
            public void receiveErroroM_WS_GetAllStaffCountByOrgCode(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetEmployeeProfileForOldJava method
            * override this method for handling normal response from oM_WS_GetEmployeeProfileForOldJava operation
            */
           public void receiveResultoM_WS_GetEmployeeProfileForOldJava(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetEmployeeProfileForOldJavaResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetEmployeeProfileForOldJava operation
           */
            public void receiveErroroM_WS_GetEmployeeProfileForOldJava(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetOrganizationName method
            * override this method for handling normal response from oM_WS_GetOrganizationName operation
            */
           public void receiveResultoM_WS_GetOrganizationName(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetOrganizationNameResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetOrganizationName operation
           */
            public void receiveErroroM_WS_GetOrganizationName(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmployeeUnderApprover method
            * override this method for handling normal response from oM_WS_ListEmployeeUnderApprover operation
            */
           public void receiveResultoM_WS_ListEmployeeUnderApprover(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmployeeUnderApproverResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmployeeUnderApprover operation
           */
            public void receiveErroroM_WS_ListEmployeeUnderApprover(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_S_UPD_EMPLOYEE method
            * override this method for handling normal response from oM_S_UPD_EMPLOYEE operation
            */
           public void receiveResultoM_S_UPD_EMPLOYEE(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_S_UPD_EMPLOYEEResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_S_UPD_EMPLOYEE operation
           */
            public void receiveErroroM_S_UPD_EMPLOYEE(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetEmployeeProfileByEmail method
            * override this method for handling normal response from oM_WS_GetEmployeeProfileByEmail operation
            */
           public void receiveResultoM_WS_GetEmployeeProfileByEmail(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetEmployeeProfileByEmailResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetEmployeeProfileByEmail operation
           */
            public void receiveErroroM_WS_GetEmployeeProfileByEmail(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListOrgInfo method
            * override this method for handling normal response from oM_WS_ListOrgInfo operation
            */
           public void receiveResultoM_WS_ListOrgInfo(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListOrgInfoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListOrgInfo operation
           */
            public void receiveErroroM_WS_ListOrgInfo(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_CheckEmployee method
            * override this method for handling normal response from oM_WS_CheckEmployee operation
            */
           public void receiveResultoM_WS_CheckEmployee(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_CheckEmployeeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_CheckEmployee operation
           */
            public void receiveErroroM_WS_CheckEmployee(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListLogK2ChangeApprover method
            * override this method for handling normal response from oM_WS_ListLogK2ChangeApprover operation
            */
           public void receiveResultoM_WS_ListLogK2ChangeApprover(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListLogK2ChangeApproverResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListLogK2ChangeApprover operation
           */
            public void receiveErroroM_WS_ListLogK2ChangeApprover(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_LIstEmployeeID method
            * override this method for handling normal response from oM_WS_LIstEmployeeID operation
            */
           public void receiveResultoM_WS_LIstEmployeeID(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_LIstEmployeeIDResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_LIstEmployeeID operation
           */
            public void receiveErroroM_WS_LIstEmployeeID(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmpChangedStatusByDate method
            * override this method for handling normal response from oM_WS_ListEmpChangedStatusByDate operation
            */
           public void receiveResultoM_WS_ListEmpChangedStatusByDate(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmpChangedStatusByDateResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmpChangedStatusByDate operation
           */
            public void receiveErroroM_WS_ListEmpChangedStatusByDate(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListAllApprover method
            * override this method for handling normal response from oM_WS_ListAllApprover operation
            */
           public void receiveResultoM_WS_ListAllApprover(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListAllApproverResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListAllApprover operation
           */
            public void receiveErroroM_WS_ListAllApprover(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmployeeEmpty method
            * override this method for handling normal response from oM_WS_ListEmployeeEmpty operation
            */
           public void receiveResultoM_WS_ListEmployeeEmpty(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmployeeEmptyResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmployeeEmpty operation
           */
            public void receiveErroroM_WS_ListEmployeeEmpty(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListOldPinFalse method
            * override this method for handling normal response from oM_WS_ListOldPinFalse operation
            */
           public void receiveResultoM_WS_ListOldPinFalse(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListOldPinFalseResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListOldPinFalse operation
           */
            public void receiveErroroM_WS_ListOldPinFalse(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmpProfileByPin method
            * override this method for handling normal response from oM_WS_ListEmpProfileByPin operation
            */
           public void receiveResultoM_WS_ListEmpProfileByPin(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmpProfileByPinResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmpProfileByPin operation
           */
            public void receiveErroroM_WS_ListEmpProfileByPin(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmployeProfileByUsername2 method
            * override this method for handling normal response from oM_WS_ListEmployeProfileByUsername2 operation
            */
           public void receiveResultoM_WS_ListEmployeProfileByUsername2(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmployeProfileByUsername2Response result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmployeProfileByUsername2 operation
           */
            public void receiveErroroM_WS_ListEmployeProfileByUsername2(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetEmployeeFamilyInfo method
            * override this method for handling normal response from oM_WS_GetEmployeeFamilyInfo operation
            */
           public void receiveResultoM_WS_GetEmployeeFamilyInfo(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetEmployeeFamilyInfoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetEmployeeFamilyInfo operation
           */
            public void receiveErroroM_WS_GetEmployeeFamilyInfo(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListOrganizeLower method
            * override this method for handling normal response from oM_WS_ListOrganizeLower operation
            */
           public void receiveResultoM_WS_ListOrganizeLower(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListOrganizeLowerResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListOrganizeLower operation
           */
            public void receiveErroroM_WS_ListOrganizeLower(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_CheckEmpResigned method
            * override this method for handling normal response from oM_WS_CheckEmpResigned operation
            */
           public void receiveResultoM_WS_CheckEmpResigned(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_CheckEmpResignedResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_CheckEmpResigned operation
           */
            public void receiveErroroM_WS_CheckEmpResigned(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListAllPinsByPrefixPin method
            * override this method for handling normal response from oM_WS_ListAllPinsByPrefixPin operation
            */
           public void receiveResultoM_WS_ListAllPinsByPrefixPin(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListAllPinsByPrefixPinResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListAllPinsByPrefixPin operation
           */
            public void receiveErroroM_WS_ListAllPinsByPrefixPin(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_SearchEmployeeUpper method
            * override this method for handling normal response from oM_WS_SearchEmployeeUpper operation
            */
           public void receiveResultoM_WS_SearchEmployeeUpper(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_SearchEmployeeUpperResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_SearchEmployeeUpper operation
           */
            public void receiveErroroM_WS_SearchEmployeeUpper(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_CheckManager method
            * override this method for handling normal response from oM_WS_CheckManager operation
            */
           public void receiveResultoM_WS_CheckManager(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_CheckManagerResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_CheckManager operation
           */
            public void receiveErroroM_WS_CheckManager(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListAllPinsByPrefixThaiName method
            * override this method for handling normal response from oM_WS_ListAllPinsByPrefixThaiName operation
            */
           public void receiveResultoM_WS_ListAllPinsByPrefixThaiName(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListAllPinsByPrefixThaiNameResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListAllPinsByPrefixThaiName operation
           */
            public void receiveErroroM_WS_ListAllPinsByPrefixThaiName(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetEmployeeAddIdToTableId method
            * override this method for handling normal response from oM_WS_GetEmployeeAddIdToTableId operation
            */
           public void receiveResultoM_WS_GetEmployeeAddIdToTableId(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetEmployeeAddIdToTableIdResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetEmployeeAddIdToTableId operation
           */
            public void receiveErroroM_WS_GetEmployeeAddIdToTableId(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetApproverProfile method
            * override this method for handling normal response from oM_WS_GetApproverProfile operation
            */
           public void receiveResultoM_WS_GetApproverProfile(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetApproverProfileResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetApproverProfile operation
           */
            public void receiveErroroM_WS_GetApproverProfile(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetOrganizationData method
            * override this method for handling normal response from oM_WS_GetOrganizationData operation
            */
           public void receiveResultoM_WS_GetOrganizationData(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetOrganizationDataResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetOrganizationData operation
           */
            public void receiveErroroM_WS_GetOrganizationData(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListLineApproverPositionAvpUp method
            * override this method for handling normal response from oM_WS_ListLineApproverPositionAvpUp operation
            */
           public void receiveResultoM_WS_ListLineApproverPositionAvpUp(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListLineApproverPositionAvpUpResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListLineApproverPositionAvpUp operation
           */
            public void receiveErroroM_WS_ListLineApproverPositionAvpUp(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListAllPG method
            * override this method for handling normal response from oM_WS_ListAllPG operation
            */
           public void receiveResultoM_WS_ListAllPG(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListAllPGResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListAllPG operation
           */
            public void receiveErroroM_WS_ListAllPG(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetPinRelationOrg method
            * override this method for handling normal response from oM_WS_GetPinRelationOrg operation
            */
           public void receiveResultoM_WS_GetPinRelationOrg(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetPinRelationOrgResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetPinRelationOrg operation
           */
            public void receiveErroroM_WS_GetPinRelationOrg(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListLogOmDataRelation method
            * override this method for handling normal response from oM_WS_ListLogOmDataRelation operation
            */
           public void receiveResultoM_WS_ListLogOmDataRelation(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListLogOmDataRelationResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListLogOmDataRelation operation
           */
            public void receiveErroroM_WS_ListLogOmDataRelation(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmpType method
            * override this method for handling normal response from oM_WS_ListEmpType operation
            */
           public void receiveResultoM_WS_ListEmpType(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmpTypeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmpType operation
           */
            public void receiveErroroM_WS_ListEmpType(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetEmployeeFromVwEmployeeDetail method
            * override this method for handling normal response from oM_WS_GetEmployeeFromVwEmployeeDetail operation
            */
           public void receiveResultoM_WS_GetEmployeeFromVwEmployeeDetail(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetEmployeeFromVwEmployeeDetailResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetEmployeeFromVwEmployeeDetail operation
           */
            public void receiveErroroM_WS_GetEmployeeFromVwEmployeeDetail(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListAllPinByOrgCodeLevel method
            * override this method for handling normal response from oM_WS_ListAllPinByOrgCodeLevel operation
            */
           public void receiveResultoM_WS_ListAllPinByOrgCodeLevel(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListAllPinByOrgCodeLevelResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListAllPinByOrgCodeLevel operation
           */
            public void receiveErroroM_WS_ListAllPinByOrgCodeLevel(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetEmpByUserForOldJava method
            * override this method for handling normal response from oM_WS_GetEmpByUserForOldJava operation
            */
           public void receiveResultoM_WS_GetEmpByUserForOldJava(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetEmpByUserForOldJavaResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetEmpByUserForOldJava operation
           */
            public void receiveErroroM_WS_GetEmpByUserForOldJava(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListOrgPersInfoHRM method
            * override this method for handling normal response from oM_WS_ListOrgPersInfoHRM operation
            */
           public void receiveResultoM_WS_ListOrgPersInfoHRM(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListOrgPersInfoHRMResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListOrgPersInfoHRM operation
           */
            public void receiveErroroM_WS_ListOrgPersInfoHRM(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListAllPinsByOrgCode method
            * override this method for handling normal response from oM_WS_ListAllPinsByOrgCode operation
            */
           public void receiveResultoM_WS_ListAllPinsByOrgCode(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListAllPinsByOrgCodeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListAllPinsByOrgCode operation
           */
            public void receiveErroroM_WS_ListAllPinsByOrgCode(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmployeeType method
            * override this method for handling normal response from oM_WS_ListEmployeeType operation
            */
           public void receiveResultoM_WS_ListEmployeeType(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmployeeTypeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmployeeType operation
           */
            public void receiveErroroM_WS_ListEmployeeType(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetApproverProfileForEHR method
            * override this method for handling normal response from oM_WS_GetApproverProfileForEHR operation
            */
           public void receiveResultoM_WS_GetApproverProfileForEHR(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetApproverProfileForEHRResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetApproverProfileForEHR operation
           */
            public void receiveErroroM_WS_GetApproverProfileForEHR(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListAllEmpByCondition method
            * override this method for handling normal response from oM_WS_ListAllEmpByCondition operation
            */
           public void receiveResultoM_WS_ListAllEmpByCondition(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListAllEmpByConditionResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListAllEmpByCondition operation
           */
            public void receiveErroroM_WS_ListAllEmpByCondition(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetEmployeeIdByEmail method
            * override this method for handling normal response from oM_WS_GetEmployeeIdByEmail operation
            */
           public void receiveResultoM_WS_GetEmployeeIdByEmail(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetEmployeeIdByEmailResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetEmployeeIdByEmail operation
           */
            public void receiveErroroM_WS_GetEmployeeIdByEmail(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetEmployeeFromEmMstEmployee method
            * override this method for handling normal response from oM_WS_GetEmployeeFromEmMstEmployee operation
            */
           public void receiveResultoM_WS_GetEmployeeFromEmMstEmployee(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetEmployeeFromEmMstEmployeeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetEmployeeFromEmMstEmployee operation
           */
            public void receiveErroroM_WS_GetEmployeeFromEmMstEmployee(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_S_GET_EMPLOYEE method
            * override this method for handling normal response from oM_S_GET_EMPLOYEE operation
            */
           public void receiveResultoM_S_GET_EMPLOYEE(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_S_GET_EMPLOYEEResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_S_GET_EMPLOYEE operation
           */
            public void receiveErroroM_S_GET_EMPLOYEE(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetEmpProfileByEmail method
            * override this method for handling normal response from oM_WS_GetEmpProfileByEmail operation
            */
           public void receiveResultoM_WS_GetEmpProfileByEmail(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetEmpProfileByEmailResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetEmpProfileByEmail operation
           */
            public void receiveErroroM_WS_GetEmpProfileByEmail(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetEmployeeDetailByPin method
            * override this method for handling normal response from oM_WS_GetEmployeeDetailByPin operation
            */
           public void receiveResultoM_WS_GetEmployeeDetailByPin(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetEmployeeDetailByPinResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetEmployeeDetailByPin operation
           */
            public void receiveErroroM_WS_GetEmployeeDetailByPin(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListAllEmployeeUnderApprover method
            * override this method for handling normal response from oM_WS_ListAllEmployeeUnderApprover operation
            */
           public void receiveResultoM_WS_ListAllEmployeeUnderApprover(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListAllEmployeeUnderApproverResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListAllEmployeeUnderApprover operation
           */
            public void receiveErroroM_WS_ListAllEmployeeUnderApprover(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListAllApproverForEHR method
            * override this method for handling normal response from oM_WS_ListAllApproverForEHR operation
            */
           public void receiveResultoM_WS_ListAllApproverForEHR(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListAllApproverForEHRResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListAllApproverForEHR operation
           */
            public void receiveErroroM_WS_ListAllApproverForEHR(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetEmpProfileforValidate method
            * override this method for handling normal response from oM_WS_GetEmpProfileforValidate operation
            */
           public void receiveResultoM_WS_GetEmpProfileforValidate(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetEmpProfileforValidateResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetEmpProfileforValidate operation
           */
            public void receiveErroroM_WS_GetEmpProfileforValidate(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListJobKey method
            * override this method for handling normal response from oM_WS_ListJobKey operation
            */
           public void receiveResultoM_WS_ListJobKey(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListJobKeyResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListJobKey operation
           */
            public void receiveErroroM_WS_ListJobKey(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_SearchEmployeeOrg method
            * override this method for handling normal response from oM_WS_SearchEmployeeOrg operation
            */
           public void receiveResultoM_WS_SearchEmployeeOrg(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_SearchEmployeeOrgResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_SearchEmployeeOrg operation
           */
            public void receiveErroroM_WS_SearchEmployeeOrg(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetEmpProfileByUserName method
            * override this method for handling normal response from oM_WS_GetEmpProfileByUserName operation
            */
           public void receiveResultoM_WS_GetEmpProfileByUserName(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetEmpProfileByUserNameResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetEmpProfileByUserName operation
           */
            public void receiveErroroM_WS_GetEmpProfileByUserName(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmployeeProfileByPrefixThaiNameForTime method
            * override this method for handling normal response from oM_WS_ListEmployeeProfileByPrefixThaiNameForTime operation
            */
           public void receiveResultoM_WS_ListEmployeeProfileByPrefixThaiNameForTime(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmployeeProfileByPrefixThaiNameForTimeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmployeeProfileByPrefixThaiNameForTime operation
           */
            public void receiveErroroM_WS_ListEmployeeProfileByPrefixThaiNameForTime(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmployeeProfileByPinForTime method
            * override this method for handling normal response from oM_WS_ListEmployeeProfileByPinForTime operation
            */
           public void receiveResultoM_WS_ListEmployeeProfileByPinForTime(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmployeeProfileByPinForTimeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmployeeProfileByPinForTime operation
           */
            public void receiveErroroM_WS_ListEmployeeProfileByPinForTime(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListAllOrganize method
            * override this method for handling normal response from oM_WS_ListAllOrganize operation
            */
           public void receiveResultoM_WS_ListAllOrganize(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListAllOrganizeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListAllOrganize operation
           */
            public void receiveErroroM_WS_ListAllOrganize(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_SearchOrgInfo method
            * override this method for handling normal response from oM_WS_SearchOrgInfo operation
            */
           public void receiveResultoM_WS_SearchOrgInfo(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_SearchOrgInfoResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_SearchOrgInfo operation
           */
            public void receiveErroroM_WS_SearchOrgInfo(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListOrgPersInfoBossPostID method
            * override this method for handling normal response from oM_WS_ListOrgPersInfoBossPostID operation
            */
           public void receiveResultoM_WS_ListOrgPersInfoBossPostID(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListOrgPersInfoBossPostIDResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListOrgPersInfoBossPostID operation
           */
            public void receiveErroroM_WS_ListOrgPersInfoBossPostID(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetEmployeeProfileByUsername2 method
            * override this method for handling normal response from oM_WS_GetEmployeeProfileByUsername2 operation
            */
           public void receiveResultoM_WS_GetEmployeeProfileByUsername2(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetEmployeeProfileByUsername2Response result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetEmployeeProfileByUsername2 operation
           */
            public void receiveErroroM_WS_GetEmployeeProfileByUsername2(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListOrgDesc method
            * override this method for handling normal response from oM_WS_ListOrgDesc operation
            */
           public void receiveResultoM_WS_ListOrgDesc(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListOrgDescResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListOrgDesc operation
           */
            public void receiveErroroM_WS_ListOrgDesc(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_GetPersDataByPIN method
            * override this method for handling normal response from oM_WS_GetPersDataByPIN operation
            */
           public void receiveResultoM_WS_GetPersDataByPIN(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_GetPersDataByPINResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_GetPersDataByPIN operation
           */
            public void receiveErroroM_WS_GetPersDataByPIN(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListOrganizeByOrgList method
            * override this method for handling normal response from oM_WS_ListOrganizeByOrgList operation
            */
           public void receiveResultoM_WS_ListOrganizeByOrgList(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListOrganizeByOrgListResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListOrganizeByOrgList operation
           */
            public void receiveErroroM_WS_ListOrganizeByOrgList(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListAllPinsByPrefixEmail method
            * override this method for handling normal response from oM_WS_ListAllPinsByPrefixEmail operation
            */
           public void receiveResultoM_WS_ListAllPinsByPrefixEmail(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListAllPinsByPrefixEmailResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListAllPinsByPrefixEmail operation
           */
            public void receiveErroroM_WS_ListAllPinsByPrefixEmail(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListAllEmpProfileAndMgr method
            * override this method for handling normal response from oM_WS_ListAllEmpProfileAndMgr operation
            */
           public void receiveResultoM_WS_ListAllEmpProfileAndMgr(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListAllEmpProfileAndMgrResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListAllEmpProfileAndMgr operation
           */
            public void receiveErroroM_WS_ListAllEmpProfileAndMgr(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListEmployeeByCompanyCodeNamePin method
            * override this method for handling normal response from oM_WS_ListEmployeeByCompanyCodeNamePin operation
            */
           public void receiveResultoM_WS_ListEmployeeByCompanyCodeNamePin(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListEmployeeByCompanyCodeNamePinResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListEmployeeByCompanyCodeNamePin operation
           */
            public void receiveErroroM_WS_ListEmployeeByCompanyCodeNamePin(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListActingApprover method
            * override this method for handling normal response from oM_WS_ListActingApprover operation
            */
           public void receiveResultoM_WS_ListActingApprover(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListActingApproverResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListActingApprover operation
           */
            public void receiveErroroM_WS_ListActingApprover(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListCompanyInWireless method
            * override this method for handling normal response from oM_WS_ListCompanyInWireless operation
            */
           public void receiveResultoM_WS_ListCompanyInWireless(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListCompanyInWirelessResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListCompanyInWireless operation
           */
            public void receiveErroroM_WS_ListCompanyInWireless(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ExpSearchEmployee method
            * override this method for handling normal response from oM_WS_ExpSearchEmployee operation
            */
           public void receiveResultoM_WS_ExpSearchEmployee(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ExpSearchEmployeeResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ExpSearchEmployee operation
           */
            public void receiveErroroM_WS_ExpSearchEmployee(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for oM_WS_ListLogK2ChangeApproverDetail method
            * override this method for handling normal response from oM_WS_ListLogK2ChangeApproverDetail operation
            */
           public void receiveResultoM_WS_ListLogK2ChangeApproverDetail(
                    th.cu.thesis.fur.api.service.ws.client.WS_OM_OMServiceStub.OM_WS_ListLogK2ChangeApproverDetailResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from oM_WS_ListLogK2ChangeApproverDetail operation
           */
            public void receiveErroroM_WS_ListLogK2ChangeApproverDetail(java.lang.Exception e) {
            }
                


    }
    