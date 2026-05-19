package org.serratec.concessionaria.exception;

import io.swagger.v3.oas.annotations.Hidden;
import org.serratec.concessionaria.model.ErrorMessage;
import org.springframework.cglib.core.Local;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
@Hidden
public class ExceptionHandleController extends ResponseEntityExceptionHandler {

    @Override
    @Hidden
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request)

    {
        String mensagem = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(i -> i.getField() + " " + i.getDefaultMessage())
                .collect(Collectors.joining(","));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(mensagem, LocalDateTime.now()));
    }

    @Hidden
    @ExceptionHandler(ClienteNaoEncontradoException.class)
    public ResponseEntity<ErrorMessage> handleClienteNaoEncontradoException(ClienteNaoEncontradoException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(ex.getMessage(), LocalDateTime.now()));
    }

    @Hidden
    @ExceptionHandler(DescontoInvalidoException.class)
    public ResponseEntity<ErrorMessage> handleDescontoInvalidoException(DescontoInvalidoException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(ex.getMessage(), LocalDateTime.now()));
    }

    @Hidden
    @ExceptionHandler(PlacaJaCadastradaException.class)
    public ResponseEntity<ErrorMessage> handlePlacaJaCadastradaException(PlacaJaCadastradaException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMessage(ex.getMessage(), LocalDateTime.now()));
    }

    @Hidden
    @ExceptionHandler(ValorVendaObrigatorioException.class)
    public ResponseEntity<ErrorMessage> handleValorVendaObrigatorioException(ValorVendaObrigatorioException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(ex.getMessage(), LocalDateTime.now()));
    }

    @Hidden
    @ExceptionHandler(VeiculoNaoEncontradoException.class)
    public ResponseEntity<ErrorMessage> handleVeiculoNaoEncontradoException(VeiculoNaoEncontradoException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(ex.getMessage(), LocalDateTime.now()));
    }

    @Hidden
    @ExceptionHandler(CpfJaCadastradoException.class)
    public ResponseEntity<ErrorMessage> handleCpfJaCadastradoException(CpfJaCadastradoException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMessage(ex.getMessage(), LocalDateTime.now()));
    }

    @Hidden
    @ExceptionHandler(CadastrarClienteSemTerVendidoException.class)
    public ResponseEntity<ErrorMessage> handleCadastrarClienteSemTerVendidoException(CadastrarClienteSemTerVendidoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(ex.getMessage(), LocalDateTime.now()));
    }

    @Hidden
    @ExceptionHandler(CadastrarValorSemTerVendidoException.class)
    public ResponseEntity<ErrorMessage> handleCadastrarValorSemTerVendidoException(CadastrarValorSemTerVendidoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(ex.getMessage(), LocalDateTime.now()));
    }

    @Hidden
    @ExceptionHandler(ClienteObrigatorioException.class)
    public ResponseEntity<ErrorMessage> handleClienteObrigatorio(ClienteObrigatorioException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(ex.getMessage(), LocalDateTime.now()));
    }

    @Hidden
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessage> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String mensagem = "Não é possível apagar o cliente, pois ele está vinculado a um carro.";
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMessage(mensagem, LocalDateTime.now()));
    }

}
