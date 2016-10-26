package kr.co.netbro.common.exception;

public class RequiredDataException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	public RequiredDataException(String message) {
		super(message);
	}
	
	public RequiredDataException(char errorCode, Throwable cause) {
		super(cause);
		setErrorCode(errorCode);
	}
	
	public RequiredDataException(Throwable cause) {
		super(cause);
	}
	
	public RequiredDataException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public RequiredDataException(char errorCode, String message, Throwable cause) {
		super(message, cause);
		setErrorCode(errorCode);
	}
	
}
