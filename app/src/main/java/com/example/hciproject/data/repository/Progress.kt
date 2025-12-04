package com.example.emotionrecognition.data.repository

import com.example.hciproject.data.MongoDB // Import ông trùm
import org.bson.Document

object ProgressRepository {
    // Lấy đúng collection "progress" từ ông trùm
    private val collection = MongoDB.getDatabase().getCollection("progress")

    fun saveDailyProgress(correct: Int, wrong: Int) {
        // Code lưu tiến độ (như cũ) dùng biến `collection` ở trên
        // collection.insertOne(...)
    }

    fun getTodayStats(): Pair<Int, Int> {
        // Code lấy thống kê
        return Pair(0, 0)
    }
}