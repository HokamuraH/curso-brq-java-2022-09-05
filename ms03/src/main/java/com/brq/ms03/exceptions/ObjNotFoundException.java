package com.brq.ms03.exceptions;


public class ObjNotFoundException extends RuntimeException{

    public ObjNotFoundException(String mensagem){
        super(mensagem);
    }

    public ObjNotFoundException(String mensagem, Throwable causa){
        super(mensagem,causa);
    }
}