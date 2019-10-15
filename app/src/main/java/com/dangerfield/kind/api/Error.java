package com.dangerfield.kind.api;

public class Error<T, U> extends Result<T, U> {
    public U value;

    public Error(U value) {
        this.value = value;
    }
}
