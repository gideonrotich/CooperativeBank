package com.swayy.cooperativebank.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swayy.cooperativebank.R
import com.swayy.cooperativebank.domain.model.Login

@Composable
fun HomeScreen(
    userTest: Login?,
) {

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                userTest?.let {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Image(
                            painter = painterResource(id = R.drawable.backgroundimage),
                            contentDescription = "",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.FillBounds
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = 20.dp),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(
                                    onClick = {

                                    },
                                    modifier = Modifier.padding(top = 10.dp, start = 3.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.Logout,
                                        contentDescription = "Log Out",
                                        tint = Color.White
                                    )
                                }

                                val annotatedString = buildAnnotatedString {
                                    withStyle(
                                        SpanStyle(
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Normal,
                                            color = Color.White
                                        )
                                    ) {
                                        append("Log Out")
                                    }

                                }

                                Text(
                                    text = annotatedString,
                                    modifier = Modifier.padding(top = 10.dp, start = 14.dp),
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxHeight(0.25f)
                                    .fillMaxWidth()
                                    .padding(),
                                contentAlignment = Alignment.Center
                            ) {

                                Image(
                                    painter = painterResource(id = R.drawable.logo),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(100.dp)
                                )

                            }
                            Box(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .fillMaxWidth(),

                                    ) {

                                    Text(
                                        text = buildAnnotatedString {
                                            append("Welcome ")
                                            withStyle(
                                                style = SpanStyle(
                                                    color = colorResource(id = R.color.coop_light),
                                                    fontWeight = FontWeight.Bold
                                                )
                                            ) {
                                                append(it.username)
                                            }
                                            append(" to the new Co-op Bank App!")
                                        },
                                        modifier = Modifier
                                            .wrapContentSize()
                                            .padding(top = 14.dp),
                                        style = TextStyle(
                                            fontSize = 22.sp,
                                            fontWeight = FontWeight.Normal,
                                            color = Color.White
                                        ),
                                        textAlign = TextAlign.Center
                                    )

                                }
                            }
                        }
                    }
                }

            }
        }
    }


}