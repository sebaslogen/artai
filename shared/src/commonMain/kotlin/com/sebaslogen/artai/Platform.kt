package com.sebaslogen.artai

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform