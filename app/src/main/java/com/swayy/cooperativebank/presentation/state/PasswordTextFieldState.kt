package com.swayy.cooperativebank.presentation.state

data class PasswordTextFieldState(
    val text: String = "",
    val error: String? = null,
    val isPasswordVisible: Boolean = true
)
