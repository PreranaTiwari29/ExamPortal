package exam.portal.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import exam.portal.dtos.Response;

@ControllerAdvice
public class RestControllerExceptionHandler {
	@ExceptionHandler({RuntimeException.class})
	public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
		return Response.error(ex.getMessage());
	}
	
	@ExceptionHandler({AccessDeniedException.class})
	public ResponseEntity<?> handleUnauthorisedException(Exception ex) {
		Map<String, Object> map = new HashMap<>();
		map.put("status", 401);
		map.put("error", ex.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);
	}
	
	@ExceptionHandler
	public ResponseEntity<?> handleGeneralException(Exception ex) {
		return Response.error(ex.getMessage());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    List<ObjectError> list= ex.getBindingResult().getAllErrors();
	    for(ObjectError error: list) {
	    	String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    }
	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
}
