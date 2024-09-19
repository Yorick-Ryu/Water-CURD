package com.yorick.curd.data

import com.yorick.curd.data.model.User
import java.sql.Connection

class UserDAO(private val connection: Connection) {

    // 创建用户
    fun createUser(user: User): Int {
        val sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)"
        val statement = connection.prepareStatement(sql)
        statement.setString(1, user.username)
        statement.setString(2, user.email)
        statement.setString(3, user.password)
        return statement.executeUpdate()
    }

    // 通过邮箱获取用户
    fun getUserByEmail(email: String): User? {
        val sql = "SELECT * FROM users WHERE email = ?"
        val statement = connection.prepareStatement(sql)
        statement.setString(1, email)
        val resultSet = statement.executeQuery()

        return if (resultSet.next()) {
            User(
                userId = resultSet.getInt("user_id"),
                username = resultSet.getString("username"),
                email = resultSet.getString("email"),
                password = resultSet.getString("password"),
                createdAt = resultSet.getString("created_at")
            )
        } else null
    }

    // 登录验证
    fun login(email: String, password: String): User? {
        val sql = "SELECT * FROM users WHERE email = ? AND password = ?"
        val statement = connection.prepareStatement(sql)
        statement.setString(1, email)
        statement.setString(2, password)
        val resultSet = statement.executeQuery()

        return if (resultSet.next()) {
            User(
                userId = resultSet.getInt("user_id"),
                username = resultSet.getString("username"),
                email = resultSet.getString("email"),
                password = resultSet.getString("password"),
                createdAt = resultSet.getString("created_at")
            )
        } else null
    }
}

