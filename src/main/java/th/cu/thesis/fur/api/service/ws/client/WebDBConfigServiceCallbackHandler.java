
/**
 * WebDBConfigServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package th.cu.thesis.fur.api.service.ws.client;

    /**
     *  WebDBConfigServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class WebDBConfigServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public WebDBConfigServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public WebDBConfigServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for wS_AUTHENDB_USERConfig method
            * override this method for handling normal response from wS_AUTHENDB_USERConfig operation
            */
           public void receiveResultwS_AUTHENDB_USERConfig(
        		   th.cu.thesis.fur.api.service.ws.client.WebDBConfigServiceStub.WS_AUTHENDB_USERConfigResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from wS_AUTHENDB_USERConfig operation
           */
            public void receiveErrorwS_AUTHENDB_USERConfig(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for wS_AUTHENDB_DBConfig method
            * override this method for handling normal response from wS_AUTHENDB_DBConfig operation
            */
           public void receiveResultwS_AUTHENDB_DBConfig(
        		   th.cu.thesis.fur.api.service.ws.client.WebDBConfigServiceStub.WS_AUTHENDB_DBConfigResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from wS_AUTHENDB_DBConfig operation
           */
            public void receiveErrorwS_AUTHENDB_DBConfig(java.lang.Exception e) {
            }
                


    }
    