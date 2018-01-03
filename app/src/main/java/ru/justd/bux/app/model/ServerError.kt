package ru.justd.bux.app.model

data class ServerError (
    override val message : String?,
    val developerMessage : String,
    val errorCode : String
) : Throwable(message)