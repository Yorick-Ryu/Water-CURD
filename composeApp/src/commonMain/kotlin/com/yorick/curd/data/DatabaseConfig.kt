package com.yorick.curd.data

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object DatabaseConfig {
    private const val URL = "jdbc:mysql://localhost:3306/water"
    private const val USER = "root"
    private const val PASSWORD = "964538"

    fun getConnection(): Connection {
        return DriverManager.getConnection(URL, USER, PASSWORD)
    }
}
