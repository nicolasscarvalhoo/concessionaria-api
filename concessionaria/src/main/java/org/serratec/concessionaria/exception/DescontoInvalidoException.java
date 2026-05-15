package org.serratec.concessionaria.exception;

public class DescontoInvalidoException extends RuntimeException{
    public DescontoInvalidoException(String mensagem) {
        super(mensagem);
    }
}
