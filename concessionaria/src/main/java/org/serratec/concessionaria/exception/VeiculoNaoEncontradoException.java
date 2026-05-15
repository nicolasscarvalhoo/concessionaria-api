package org.serratec.concessionaria.exception;

public class VeiculoNaoEncontradoException extends RuntimeException{
    public VeiculoNaoEncontradoException (String mensagem) {
        super(mensagem);
    }
}
