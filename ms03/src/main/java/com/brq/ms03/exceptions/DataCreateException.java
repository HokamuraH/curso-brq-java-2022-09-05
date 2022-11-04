package com.brq.ms03.exceptions;

public class DataCreateException extends RuntimeException{

    public DataCreateException(String mensagem){
        super(mensagem);
    }

    public DataCreateException(String mensagem, Throwable causa){
        super(mensagem,causa);
    }
}