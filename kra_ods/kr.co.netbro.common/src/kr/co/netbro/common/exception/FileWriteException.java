package kr.co.netbro.common.exception;

public class FileWriteException extends ApplicationException {

	private static final long serialVersionUID = 1L;

	public FileWriteException(String message) {
		super(message);
	}
	
	public FileWriteException(char errorCode, Throwable cause) {
		super(cause);
		setErrorCode(errorCode);
	}
	
	public FileWriteException(Throwable cause) {
		super(cause);
	}
	
	public FileWriteException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public FileWriteException(char errorCode, String message, Throwable cause) {
		super(message, cause);
		setErrorCode(errorCode);
	}
	
}
