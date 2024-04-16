package com.swayy.cooperativebank.presentation.screens

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.swayy.cooperativebank.R
import com.swayy.cooperativebank.domain.model.Login
import com.swayy.cooperativebank.presentation.state.LoginState
import com.swayy.cooperativebank.presentation.state.PasswordTextFieldState
import com.swayy.cooperativebank.presentation.viewmodel.LoginViewmodel
import com.swayy.cooperativebank.util.UiEvents
import com.swayy.qhala.presentation.state.TextFieldState
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    navigateToWelcomeScreen: (Login) -> Unit,
    viewModel: LoginViewmodel = hiltViewModel()
) {
    val usernameState = viewModel.usernameState.value
    val passwordState = viewModel.passwordState.value
    val keyboardController = LocalSoftwareKeyboardController.current
    val loginState = viewModel.login.value
    val snackbarHostState = remember { SnackbarHostState() }


    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvents.SnackbarEvent -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

    SignInScreenContent(
        snackbarHostState = snackbarHostState,
        emailState = usernameState,
        passwordState = passwordState,
        loginState = loginState,
        onCurrentEmailTextChange = {
            viewModel.setUsername(it)
        },
        onCurrentPasswordTextChange = {
            viewModel.setPassword(it)
        },
        onClickSignIn = {
            keyboardController?.hide()
            viewModel.loginUser {
                navigateToWelcomeScreen(it)
            }
        },
        onConfirmPasswordToggle = {
            viewModel.togglePasswordVisibility()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun SignInScreenContent(
    snackbarHostState: SnackbarHostState,
    emailState: TextFieldState,
    passwordState: PasswordTextFieldState,
    loginState: LoginState,
    onCurrentEmailTextChange: (String) -> Unit,
    onCurrentPasswordTextChange: (String) -> Unit,
    onClickSignIn: () -> Unit,
    onConfirmPasswordToggle: (Boolean) -> Unit,
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) {
        Box {
            Image(
                painter = painterResource(id = R.drawable.backgroundimage),
                contentDescription = "",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
            Column (modifier = Modifier.fillMaxSize()){

                Column (modifier = Modifier.fillMaxWidth().padding(top = 70.dp)){

                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "",
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Welcome to Co-op Bank",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        fontSize = 18.sp,
                        color = colorResource(id = R.color.coop_light),
                    )
                }


                LazyColumn(contentPadding = PaddingValues(16.dp)) {

                    item {
                        Spacer(modifier = Modifier.height(64.dp))

                        Text(
                            text = "Use your credentials to login",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White,
                            modifier = Modifier.padding(start = 20.dp, end = 20.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Column {
                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp),
                                value = emailState.text,
                                onValueChange = {
                                    onCurrentEmailTextChange(it)
                                },
                                label = {
                                    Text(text = "Username")
                                },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Text
                                ),
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = "",
                                        tint = Color.White
                                    )

                                },
                                isError = emailState.error != null,
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color.White,
                                    unfocusedBorderColor = Color.White,
                                    cursorColor = Color.White,
                                    focusedLabelColor = Color.White,
                                    unfocusedLabelColor = Color.White,
                                ),

                                )
                            if (emailState.error != "") {
                                Text(
                                    text = emailState.error ?: "",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.error,
                                    textAlign = TextAlign.End,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(8.dp))

                        Column {
                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp),
                                value = passwordState.text,
                                onValueChange = {
                                    onCurrentPasswordTextChange(it)
                                },
                                label = {
                                    Text(text = "Password")
                                },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Password
                                ),
                                isError = passwordState.error != null,
                                visualTransformation = if (passwordState.isPasswordVisible) {
                                    PasswordVisualTransformation()
                                } else {
                                    VisualTransformation.None
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = "",
                                        tint = Color.White
                                    )

                                },
                                trailingIcon = {
                                    IconButton(
                                        onClick = {
                                            onConfirmPasswordToggle(!passwordState.isPasswordVisible)
                                        },
                                        modifier = Modifier.semantics {
                                            testTag = "PasswordToggle"
                                        }
                                    ) {
                                        Icon(
                                            imageVector = if (passwordState.isPasswordVisible) {
                                                Icons.Filled.VisibilityOff
                                            } else {
                                                Icons.Filled.Visibility
                                            },
                                            contentDescription = if (passwordState.isPasswordVisible) {
                                                "Hide Password"
                                            } else {
                                                "Show Password"
                                            }
                                        )
                                    }
                                },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Color.White,
                                    unfocusedBorderColor = Color.White,
                                    cursorColor = Color.White,
                                    focusedLabelColor = Color.White,
                                    unfocusedLabelColor = Color.White,
                                ),
                            )
                            if (passwordState.error != "") {
                                Text(
                                    text = passwordState.error ?: "",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.error,
                                    textAlign = TextAlign.End,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = onClickSignIn,
                            shape = RoundedCornerShape(8),
                            colors = ButtonDefaults.buttonColors(Color.Transparent),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(50.dp)
                                    .background(
                                        color = colorResource(id = R.color.coop_light),
                                    ),
                                contentAlignment = Alignment.Center
                            ){

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .heightIn(50.dp)
                                        .background(
                                            color = colorResource(id = R.color.coop_light),
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Login",
                                        fontSize = 18.sp,
                                        color = colorResource(id = R.color.coop_green_dark)
                                    )
                                }
                            }

                        }
                    }
                    item {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            Spacer(modifier = Modifier.height(16.dp))
                            if (loginState.isLoading) {
//                                CircularProgressIndicator()
                            }
                        }
                    }

                }
            }


        }

    }
}

//how to create custom modifier
//fun Modifier.customModifier(): Modifier = composed {
//    val shape = RoundedCornerShape(10.dp)
//
//    this then Modifier.clip(shape)
//}