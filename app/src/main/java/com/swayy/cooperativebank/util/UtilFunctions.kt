package com.swayy.cooperativebank.util

import android.util.Log
import com.google.gson.Gson

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): Resource<T> {
    return withContext(dispatcher) {
        try {
            Resource.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> {
                    Resource.Error(
                        message = "Please check your internet connection and try again later",
                        throwable = throwable
                    )
                }

                is HttpException -> {
                    val stringErrorBody = errorBodyAsString(throwable)
                    if (stringErrorBody != null) {
                        val errorResponse = convertStringErrorResponseToJsonObject(stringErrorBody)
                        Resource.Error(
                            message = errorResponse?.message,
                            throwable = throwable
                        )
                    } else {
                        Resource.Error(
                            message = "Unknown failure occurred, please try again later",
                            throwable = throwable
                        )
                    }
                }

                else -> {
                    Log.e("safeApiCall", "Unknown failure occurred", throwable)
                    Resource.Error(
                        message = "Unknown failure occurred, please try again later",
                        throwable = throwable
                    )
                }
            }
        }
    }
}

private fun convertStringErrorResponseToJsonObject(jsonString: String): ErrorResponse? {
    val gson = Gson()
    return gson.fromJson(jsonString, ErrorResponse::class.java)
}

fun errorBodyAsString(throwable: HttpException): String? {
    val reader = throwable.response()?.errorBody()?.charStream()
    return reader?.use { it.readText() }
}