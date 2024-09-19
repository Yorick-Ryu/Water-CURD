package com.yorick.curd.data

import com.yorick.curd.data.model.WaterIntake
import java.sql.Connection

class WaterIntakeDAO(private val connection: Connection) {

    // 添加饮水记录
    fun addWaterIntake(intake: WaterIntake): Int {
        val sql = "INSERT INTO water_intake (user_id, intake_amount_ml) VALUES (?, ?)"
        val statement = connection.prepareStatement(sql)
        statement.setInt(1, intake.userId)
        statement.setInt(2, intake.intakeAmountMl)
        return statement.executeUpdate()
    }

    // 获取用户所有的饮水记录
    fun getUserWaterIntake(userId: Int): List<WaterIntake> {
        val sql = "SELECT * FROM water_intake WHERE user_id = ?"
        val statement = connection.prepareStatement(sql)
        statement.setInt(1, userId)
        val resultSet = statement.executeQuery()

        val waterIntakes = mutableListOf<WaterIntake>()
        while (resultSet.next()) {
            waterIntakes.add(
                WaterIntake(
                    intakeId = resultSet.getInt("intake_id"),
                    userId = resultSet.getInt("user_id"),
                    intakeAmountMl = resultSet.getInt("intake_amount_ml"),
                    intakeTime = resultSet.getString("intake_time")
                )
            )
        }
        return waterIntakes
    }

    // 修改饮水记录
    fun updateWaterIntake(intakeId: Int, newAmount: Int): Int {
        val sql = "UPDATE water_intake SET intake_amount_ml = ? WHERE intake_id = ?"
        val statement = connection.prepareStatement(sql)
        statement.setInt(1, newAmount)
        statement.setInt(2, intakeId)
        return statement.executeUpdate()
    }

    // 删除饮水记录
    fun deleteWaterIntake(intakeId: Int): Int {
        val sql = "DELETE FROM water_intake WHERE intake_id = ?"
        val statement = connection.prepareStatement(sql)
        statement.setInt(1, intakeId)
        return statement.executeUpdate()
    }
}

