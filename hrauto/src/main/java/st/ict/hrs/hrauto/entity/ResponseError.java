package st.ict.hrs.hrauto.entity;

public class ResponseError {

	
	private int errorCode;
	private String errorMessage;
	private String errorStatus;
	public ResponseError() 
	{
		this.errorCode = 200;
		this.errorMessage = "SUCCESS";
		this.errorStatus = "OK";
	}
	
	
	public ResponseError(int errorCode, String errorMessage, String errorStatus) {		
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.errorStatus = errorStatus;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getErrorStatus() {
		return errorStatus;
	}
	public void setErrorStatus(String errorStatus) {
		this.errorStatus = errorStatus;
	}
	
	public void updateResponseError(int errorCode,String errorMessage,String errorStatus) 
	{
		setErrorMessage(errorMessage);
		setErrorStatus(errorStatus);
		setErrorCode(errorCode);
	}
	
}
