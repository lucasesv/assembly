package br.com.lucasv.southsystem.assembly.infra.http.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.lucasv.southsystem.assembly.core.exception.CannotVoteInClosedSessionException;
import br.com.lucasv.southsystem.assembly.core.exception.MemberAlreadyVotedException;

/**
 * <p>Handler to handle the exceptions of the thrown in the
 * http layer (the REST Controllers).
 * 
 * <p>This is a central place to handle main exceptions raised
 * by the http layer. It provides a friendly response to the
 * user fired the request so that he can easily know about
 * the errors. 
 * 
 * @author Lucas Vasconcelos
 *
 */
@ControllerAdvice
@RestController
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  /**
   * Handle generic errors raised by the application.
   * 
   * @param ex The exception raised by the application.
   * @param request The original request.
   * @return The Response Entity encapsulated with the {@link ExceptionResponse}.
   * @throws Exception The generic exception.
   */
  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ExceptionResponse>handleAllException(
      Exception ex,
      WebRequest request) throws Exception {
    
    ExceptionResponse response = new ExceptionResponse(
        LocalDateTime.now(),
        ex.getMessage(),
        request.getDescription(false));
    
    return new ResponseEntity<ExceptionResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }
  
  /**
   * Handle error regarding wrong arguments sent in the request,
   * but raised in the validation of the DTO's.
   * 
   * @param ex The exception raised by the application.
   * @param headers The headers of the response.
   * @param HttpStatus The HTTP Status of the response.
   * @param request The original request.
   * @return The Response Entity encapsulated with the {@link ExceptionResponse}.
   */
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    
      Map<String, String> errors = new HashMap<>();
      ex.getBindingResult().getAllErrors().forEach((error) -> {
          String fieldName = ((FieldError) error).getField();
          String errorMessage = error.getDefaultMessage();
          errors.put(fieldName, errorMessage);
      });
      
      return createExceptionResponse(errors, request, status);
  }
  
  /**
   * Handle error regarding wrong arguments sent in the request,
   * but raised in the application core.
   * 
   * @param ex The exception raised by the application.
   * @param request The original request.
   * @return The Response Entity encapsulated with the {@link ExceptionResponse}.
   * @throws Exception The generic exception.
   */
  @ExceptionHandler({
    MemberAlreadyVotedException.class,
    CannotVoteInClosedSessionException.class,
    IllegalArgumentException.class})
  public final ResponseEntity<Object> handleBadRequestExceptions(
      Exception ex,
      WebRequest request) throws Exception {
    
    return createExceptionResponse(ex.getMessage(), request, HttpStatus.BAD_REQUEST);
  }
  
  /**
   * Handle error when the resource specified in the request is not found.
   * 
   * @param ex The exception raised by the application.
   * @param request The original request.
   * @return The Response Entity encapsulated with the {@link ExceptionResponse}.
   * @throws Exception The generic exception.
   */
  @ExceptionHandler(NoSuchElementException.class)
  public final ResponseEntity<Object> handleNotFoundExceptions(
      Exception ex,
      WebRequest request) throws Exception {
    
    return createExceptionResponse(ex.getMessage(), request, HttpStatus.NOT_FOUND);
  }
  
  /**
   * Create a {@link ExceptionResponse} with 1 error only.
   * 
   * @param errorMsg The error message that should be in the response.
   * @param request The original request.
   * @param httpStatus The HTTP Status of the response.
   * @return The Response Entity encapsulated with the {@link ExceptionResponse}
   */
  private ResponseEntity<Object> createExceptionResponse(
      String errorMsg,
      WebRequest request,
      HttpStatus httpStatus) {
    
    ExceptionResponse response = new ExceptionResponse(
        LocalDateTime.now(),
        errorMsg,
        request.getDescription(false));
    
    return new ResponseEntity<Object>(response, httpStatus);
  }
  
  /**
   * Create a {@link ExceptionResponse} with more than 1 errors.
   * 
   * @param errors A map with all errors that should be in the response.
   * @param request The original request.
   * @param httpStatus The HTTP Status of the response.
   * @return The Response Entity encapsulated with the {@link ExceptionResponse}
   */
  private ResponseEntity<Object> createExceptionResponse(
      Map<String, String> errors,
      WebRequest request,
      HttpStatus httpStatus) {
    
    ExceptionResponse response = new ExceptionResponse(
        LocalDateTime.now(),
        errors,
        request.getDescription(false));
    
    return new ResponseEntity<Object>(response, httpStatus);
  }
  
}
