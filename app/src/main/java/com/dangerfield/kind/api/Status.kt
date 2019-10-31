package com.dangerfield.kind.api

sealed class Status {
    object LOADING : Status()
    object SUCCESS : Status()
    class FAILURE(val message: String) : Status()
}