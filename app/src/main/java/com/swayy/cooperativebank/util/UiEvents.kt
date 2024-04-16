package com.swayy.cooperativebank.util

sealed class UiEvents {
    data class SnackbarEvent(val message: String) : UiEvents()
}
