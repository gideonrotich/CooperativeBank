package com.swayy.cooperativebank.data.repository

import com.swayy.cooperativebank.data.mapper.toDomain
import com.swayy.cooperativebank.data.remote.CooperativeApi
import com.swayy.cooperativebank.domain.model.Login
import com.swayy.cooperativebank.domain.model.LoginRequest
import com.swayy.cooperativebank.domain.repository.LoginRepository
import com.swayy.cooperativebank.util.Resource
import com.swayy.cooperativebank.util.safeApiCall
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class LoginRepositoryImpl(
    private val api: CooperativeApi
) : LoginRepository {
    override suspend fun login(request: LoginRequest): Resource<Login> {
        return safeApiCall(Dispatchers.IO) {
            val response = api.login(request = request)
            val result = response.toDomain()
            result
        }
    }
}