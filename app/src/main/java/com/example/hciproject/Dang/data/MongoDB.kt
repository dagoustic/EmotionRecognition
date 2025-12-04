package com.example.hciproject.Dang.data

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase

// Singleton: Giữ kết nối duy nhất cho toàn App
object MongoDB {
    // URL kết nối (Nhớ thay password)
    private const val CONNECTION_STRING = "mongodb+srv://dagoustic:Dang091205@cluster0.n36gfkd.mongodb.net/?appName=Cluster0"
    private const val DB_NAME = "emotion_recognition"

    // Biến lưu client để tái sử dụng
    private var client: MongoClient? = null

    // Hàm lấy Database (Các Repository con sẽ gọi hàm này)
    fun getDatabase(): MongoDatabase {
        if (client == null) {
            client = MongoClients.create(CONNECTION_STRING)
        }
        return client!!.getDatabase(DB_NAME)
    }
}