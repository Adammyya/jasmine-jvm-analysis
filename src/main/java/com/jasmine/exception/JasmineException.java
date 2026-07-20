package com.jasmine.exception;

/**
 * Base exception class for all JASMINE-specific exceptions.
 *
 * <p>All custom exceptions in the JASMINE application should extend this class
 * to provide a consistent exception hierarchy. This enables:
 * <ul>
 *     <li>Catching all application-specific exceptions with a single catch block</li>
 *     <li>Typed error codes for programmatic error handling</li>
 *     <li>Clean separation from JDK and third-party exceptions</li>
 * </ul>
 *
 * <p><strong>Design Decision:</strong> Extends {@link RuntimeException} (unchecked)
 * rather than {@link Exception} (checked) because most JASMINE errors are
 * non-recoverable at the point of occurrence. The caller should not be forced
 * to declare or catch exceptions they cannot meaningfully handle. Error recovery
 * is centralized in the global exception handler configured at application startup.
 *
 * <p><strong>Error Codes:</strong> Each exception carries an error code string
 * (e.g., {@code "DB_CONNECTION_FAILED"}) that can be used for logging, metrics,
 * and user-facing error messages without exposing stack traces.
 *
 * @since 1.0
 */
public class JasmineException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /** Default error code when none is specified. */
    private static final String DEFAULT_ERROR_CODE = "JASMINE_ERROR";

    /** Machine-readable error code for programmatic error classification. */
    private final String errorCode;

    /**
     * Creates a new exception with a message and the default error code.
     *
     * @param message human-readable description of the error
     */
    public JasmineException(String message) {
        super(message);
        this.errorCode = DEFAULT_ERROR_CODE;
    }

    /**
     * Creates a new exception with a message, cause, and the default error code.
     *
     * @param message human-readable description of the error
     * @param cause   the underlying exception that triggered this error
     */
    public JasmineException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = DEFAULT_ERROR_CODE;
    }

    /**
     * Creates a new exception with a specific error code and message.
     *
     * @param errorCode machine-readable error classification code
     * @param message   human-readable description of the error
     */
    public JasmineException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Creates a new exception with a specific error code, message, and cause.
     *
     * @param errorCode machine-readable error classification code
     * @param message   human-readable description of the error
     * @param cause     the underlying exception that triggered this error
     */
    public JasmineException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * Returns the machine-readable error code associated with this exception.
     *
     * <p>Error codes follow the convention {@code COMPONENT_ERROR_TYPE},
     * e.g., {@code "DB_CONNECTION_FAILED"}, {@code "MONITOR_JMX_UNAVAILABLE"}.
     *
     * @return the error code string, never {@code null}
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Returns a formatted string combining the error code and message,
     * suitable for log output.
     *
     * @return formatted error string in the form {@code "[ERROR_CODE] message"}
     */
    @Override
    public String toString() {
        return String.format("[%s] %s", errorCode, getMessage());
    }
}
