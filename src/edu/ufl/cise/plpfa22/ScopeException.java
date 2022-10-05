package edu.ufl.cise.plpfa22;

@SuppressWarnings("serial")
public class ScopeException extends PLPException {

	public ScopeException() {
	}

	public ScopeException(String error_message, int line, int column) {
		super(error_message, line, column);
	}

//	public ScopeException(String error_message, SourceLocation loc) {
//		super(error_message, loc);
//	}

	public ScopeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ScopeException(String message, Throwable cause) {
		super(message, cause);
	}

	public ScopeException(String message) {
		super(message);
	}

	public ScopeException(Throwable cause) {
		super(cause);
	}

}
