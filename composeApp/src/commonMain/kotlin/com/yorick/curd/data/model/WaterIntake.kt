package com.yorick.curd.data.model

data class WaterIntake(
    val intakeId: Int,
    val userId: Int,
    val intakeAmountMl: Int,
    val intakeTime: String
)