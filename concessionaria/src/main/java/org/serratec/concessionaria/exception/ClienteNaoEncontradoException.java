package org.serratec.concessionaria.exception;

public class ClienteNaoEncontradoException extends RuntimeException{
    public ClienteNaoEncontradoException(String mensagem){
        super(mensagem);
    }
}
