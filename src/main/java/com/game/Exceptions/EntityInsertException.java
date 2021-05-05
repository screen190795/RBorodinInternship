package com.game.Exceptions;

public class EntityInsertException extends Exception {

    // Parameterless Constructor
    public EntityInsertException() {}

    // Constructor that accepts a message
    public EntityInsertException(String message)
    {
        super(message);
    }
}

