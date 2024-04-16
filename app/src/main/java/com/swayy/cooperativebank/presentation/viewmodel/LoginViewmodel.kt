package com.swayy.cooperativebank.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swayy.cooperativebank.domain.model.Login
import com.swayy.cooperativebank.domain.model.LoginRequest
import com.swayy.cooperativebank.domain.repository.LoginRepository
import com.swayy.cooperativebank.presentation.state.LoginState
import com.swayy.cooperativebank.presentation.state.PasswordTextFieldState
import com.swayy.cooperativebank.util.Resource
import com.swayy.cooperativebank.util.UiEvents
import com.swayy.qhala.presentation.state.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewmodel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {

    //used mutableState and not sharedflow or stateflow so as not to introduce asynchronous delays
    private val _state = mutableStateOf(LoginState(isLoading = false))
    val login: State<LoginState> = _state

    private val _usernameState = mutableStateOf(TextFieldState())
    val usernameState: State<TextFieldState> = _usernameState
    fun setUsername(value: String) {
        _usernameState.value = _usernameState.value.copy(text = value)
    }

    private val _passwordState = mutableStateOf(PasswordTextFieldState())
    val passwordState: State<PasswordTextFieldState> = _passwordState
    fun setPassword(value: String) {
        _passwordState.value = _passwordState.value.copy(text = value)
    }

    private val _eventFlow = MutableSharedFlow<UiEvents>()
    val eventFlow = _eventFlow.asSharedFlow()


    //login user function to call the repository
    fun loginUser(onSuccessLogin: (Login) -> Unit) {
        viewModelScope.launch {
            if (usernameState.value.text.isEmpty()) {
                _eventFlow.emit(
                    UiEvents.SnackbarEvent(message = "Please input your email")
                )
                return@launch
            }

            if (passwordState.value.text.isEmpty()) {
                _eventFlow.emit(
                    UiEvents.SnackbarEvent(message = "Please input your password")
                )
                return@launch
            }

            _state.value = login.value.copy(isLoading = true)

            when (
                val result = repository.login(
                    LoginRequest(
                        username = usernameState.value.text.trim(),
                        password = passwordState.value.text.trim()
                    )
                )
            ) {
                is Resource.Error -> {
                    _state.value = login.value.copy(
                        isLoading = false,
                        error = result.message ?: "Unknown Error Occurred"
                    )

                    _eventFlow.emit(
                        UiEvents.SnackbarEvent(message = result.message ?: "Unknown Error Occurred")
                    )
                }

                is Resource.Success -> {

                    _state.value = login.value.copy(
                        isLoading = false,
                        login = result.data
                    )
                    _eventFlow.emit(
                        UiEvents.SnackbarEvent(message = "Login Successful")
                    )

                    result.data?.let { onSuccessLogin(it) }
//                    onSuccessLogin(result.data)

                }

                else -> {
                    login
                }
            }
        }
    }

    //function to toggle password visibility
    fun togglePasswordVisibility() {
        _passwordState.value = _passwordState.value.copy(
            isPasswordVisible = !passwordState.value.isPasswordVisible
        )
    }

}
