package com.dangerfield.kind.api;

public class Success<T, U> extends Result<T, U> {
    public T value;

    public Success(T value) {
        this.value = value;
    }
}
