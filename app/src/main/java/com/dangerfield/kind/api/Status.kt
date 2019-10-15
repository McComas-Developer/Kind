package com.dangerfield.kind.api

sealed class Status {
    class LOADING : Status()
    class SUCCESS : Status()
    class FAILURE(val message: String) : Status()
}