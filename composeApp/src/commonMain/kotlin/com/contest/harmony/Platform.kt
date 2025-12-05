package com.contest.harmony

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform