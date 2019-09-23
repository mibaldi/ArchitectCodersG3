package com.mibaldi.domain.entity

data class Beer(
    val id: String,
    val title: String,
    val image: String,
    val observation: List<String>,
    val rating: Float = 0f,
    var votes: Int = 0
) {
    constructor() : this("", "", "", emptyList(), 0f, 0)
}