package com.yorick.curd

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.yorick.curd.data.DatabaseConfig
import com.yorick.curd.data.UserDAO
import com.yorick.curd.data.WaterIntakeDAO
import com.yorick.curd.data.model.User
import com.yorick.curd.data.model.WaterIntake
import com.yorick.curd.ui.screen.LoginScreen
import com.yorick.curd.ui.screen.RegisterScreen
import com.yorick.curd.ui.screen.WaterIntakeScreen
import com.yorick.curd.ui.theme.AppTheme


fun main() = application {
    var currentScreen by remember { mutableStateOf("login") }
    var loggedInUser by remember { mutableStateOf<User?>(null) }
    var waterIntakes by remember { mutableStateOf(listOf<WaterIntake>()) }

    Window(onCloseRequest = ::exitApplication, title = "Water Intake Tracker") {
        AppTheme {
            when (currentScreen) {
                "login" -> LoginScreen(
                    onLogin = { email, password ->
                        // 调用 UserDAO 登录方法
                        val user = UserDAO(DatabaseConfig.getConnection()).login(email, password)
                        if (user != null) {
                            loggedInUser = user
                            // 登录成功后，立即获取所有饮水记录
                            waterIntakes =
                                WaterIntakeDAO(DatabaseConfig.getConnection()).getUserWaterIntake(
                                    user.userId
                                )
                            currentScreen = "waterIntake"
                        }
                    },
                    onRegister = { currentScreen = "register" }
                )

                "register" -> RegisterScreen(
                    onRegister = { username, email, password ->
                        // 调用 UserDAO 注册方法
                        UserDAO(DatabaseConfig.getConnection()).createUser(
                            User(
                                0,
                                username,
                                email,
                                password,
                                ""
                            )
                        )
                        currentScreen = "login"
                    }
                )

                "waterIntake" -> WaterIntakeScreen(
                    waterIntakes = waterIntakes,
                    onAdd = { amount ->
                        loggedInUser?.let { user ->
                            // 调用 WaterIntakeDAO 添加饮水记录
                            WaterIntakeDAO(DatabaseConfig.getConnection()).addWaterIntake(
                                WaterIntake(0, user.userId, amount, "")
                            )
                            waterIntakes =
                                WaterIntakeDAO(DatabaseConfig.getConnection()).getUserWaterIntake(
                                    user.userId
                                )
                        }
                    },
                    onUpdate = { intakeId, newAmount ->
                        // 调用 WaterIntakeDAO 更新饮水记录
                        WaterIntakeDAO(DatabaseConfig.getConnection()).updateWaterIntake(
                            intakeId,
                            newAmount
                        )
                        loggedInUser?.let { user ->
                            waterIntakes =
                                WaterIntakeDAO(DatabaseConfig.getConnection()).getUserWaterIntake(
                                    user.userId
                                )
                        }
                    },
                    onDelete = { intakeId ->
                        // 调用 WaterIntakeDAO 删除饮水记录
                        WaterIntakeDAO(DatabaseConfig.getConnection()).deleteWaterIntake(intakeId)
                        loggedInUser?.let { user ->
                            waterIntakes =
                                WaterIntakeDAO(DatabaseConfig.getConnection()).getUserWaterIntake(
                                    user.userId
                                )
                        }
                    }
                )
            }
        }
    }
}
