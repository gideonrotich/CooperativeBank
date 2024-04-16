package com.swayy.cooperativebank.presentation.state

import com.swayy.cooperativebank.domain.model.Login

data class LoginState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val login: Login? = null,
    val isPasswordVisible: Boolean = false
)
