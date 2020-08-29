package com.targa.labs.dev.cqrses.exceptions;

import java.util.UUID;

public class AccountNotFoundException extends Throwable {

    private static final long serialVersionUID = 1L;

    public AccountNotFoundException (UUID id) {
        super("Cannot find account number  [" + id + "]");
    }
    
}