package com.gakki.love.exception;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: YuAn
 * \* Date: 2017/11/28
 * \* Time: 21:27
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
/*
默认异常
 */
public class FlightException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public FlightException(String message){
        super(message);
    }
    public FlightException(){

    }
}