package com.brq.ms01.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/*
 * @ControllerAdvide permite manipular execeções de forma glogal.
 * Para cada tipo de execeção, podemos manipular desde o status
 * até a mensagem de retorno
 * */
@Slf4j
@ControllerAdvice
public class ResourceExpetionHandler {
    /* Gostaria que o método abaixo trate exceções
   do tipo de validação de dados
   Response Entity permite retornar o status, headers e dados do body da requisição para o cliente
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> methodValidationHandler(MethodArgumentNotValidException exception,
                                                                HttpServletRequest request){
 //       StandardError standardError = new StandardError();
 //       standardError.setStatus(400);
 //       standardError.setPath("");
 // @Builder
//        StandardError standardError = StandardError
//                .builder()
//                .timestamp(new Date(System.currentTimeMillis()))
//                .status(HttpStatus.BAD_REQUEST.value())
////                .status(HttpStatus.NOT_FOUND.value())
//                .error("Validation Error")
//                .message(exception.getMessage())
//                .path(request.getRequestURI())
//                .build();
        ValidationError validationError = new ValidationError();
                validationError.setTimestamp(new Date(System.currentTimeMillis()));
                validationError.setStatus(HttpStatus.BAD_REQUEST.value());
                validationError.setError("Validation Error");
                validationError.setMessage(exception.getMessage());
                validationError.setPath(request.getRequestURI());
                List<FieldError> listErrors = exception.getBindingResult().getFieldErrors();
                for (FieldError err : listErrors) {
                    FieldMessage fm = new FieldMessage();
                    fm.setField(err.getField());
                    fm.setMessage(err.getDefaultMessage());
                    validationError.getErrors().add(fm);
                }
        log.info(exception.getMessage());
        log.info("Fabrizio é legal") ;
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationError);
    }
    @ExceptionHandler(DataCreateException.class)
    public ResponseEntity<StandardError> dataCreateExceptionHandler(DataCreateException exception,
                                                                    HttpServletRequest request){

        StandardError standardError = new StandardError();
        standardError.setTimestamp(new Date());
        standardError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        standardError.setError("Erro ao inserir o registro");
        standardError.setMessage(exception.getMessage());
        standardError.setPath(request.getRequestURI());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(standardError);

    }
    @ExceptionHandler(ObjNotFoundException.class)
    public ResponseEntity<StandardError> ObjNotFoundExceptionHandler(ObjNotFoundException exception,
                                                                     HttpServletRequest request){
        StandardError standardError = new StandardError();
        standardError.setTimestamp((new Date()));
        standardError.setStatus(HttpStatus.NOT_FOUND.value());
        standardError.setMessage(exception.getMessage());
        standardError.setPath(request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body((standardError));
    }
}
