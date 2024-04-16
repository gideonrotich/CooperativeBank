package com.swayy.cooperativebank.domain.repository

import com.swayy.cooperativebank.domain.model.Login
import com.swayy.cooperativebank.domain.model.LoginRequest
import com.swayy.cooperativebank.util.Resource

interface LoginRepository {
    suspend fun login(request: LoginRequest): Resource<Login>
}