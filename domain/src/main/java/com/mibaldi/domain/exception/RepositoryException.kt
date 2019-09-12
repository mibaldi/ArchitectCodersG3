package com.mibaldi.domain.exception

sealed class RepositoryException(message: String? = null) : Throwable(message) {
    object DataNotFoundException : RepositoryException()
    object NoConnectionException : RepositoryException()

}