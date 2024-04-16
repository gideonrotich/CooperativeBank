package com.swayy.cooperativebank.presentation.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.swayy.cooperativebank.domain.model.Login
import com.swayy.cooperativebank.presentation.viewmodel.HomeViewmodel

@Composable
fun HomeScreen(
    userTest:Login?,
    viewModel: HomeViewmodel = hiltViewModel()
){
    val user by viewModel.user.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = userTest) {
        userTest?.let {
            viewModel.getTestUser(it)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {

        Box(modifier = Modifier.fillMaxSize()){
            Column (modifier = Modifier.fillMaxSize()){
                Text(
                    text ="Welcome",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                user?.let {
                    Text(
                        text ="Hi "+it.firstName,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

            }
        }
    }


}