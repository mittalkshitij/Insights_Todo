package com.example.todokshitij.utils

data class State<out T>(
    val status: Status,
    val data: T?,
    val message: String?
) {

    companion object {

        fun <T> success(msg: String?, data: T?): State<T> = State(Status.SUCCESS, data, msg)
        fun <T> loading(data: T?): State<T> = State(Status.LOADING, data, null)
        fun <T> error(msg: String, data: T?): State<T> = State(Status.ERROR, data, msg)
    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}