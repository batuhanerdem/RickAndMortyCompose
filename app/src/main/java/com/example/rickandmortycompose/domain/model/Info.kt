package com.example.rickandmortycompose.domain.model

data class Info(
    val count: Int,
    val next: String,
    val pages: Int,
    val prev: String?
)