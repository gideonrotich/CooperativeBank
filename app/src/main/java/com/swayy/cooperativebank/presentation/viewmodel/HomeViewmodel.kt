package com.swayy.cooperativebank.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.swayy.cooperativebank.domain.model.Login
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(

) : ViewModel() {

    private val _user = MutableStateFlow<Login?>(null)
    val user = _user.asStateFlow()

    fun getTestUser(test: Login) {
        _user.update {
            test
        }
    }

}