package com.swayy.cooperativebank.data.mapper

import com.swayy.cooperativebank.data.remote.dto.LoginResponseDto
import com.swayy.cooperativebank.domain.model.Login

internal fun LoginResponseDto.toDomain(): Login {
    return Login(
        email, firstName, gender, id, image, lastName, token, username
    )
}