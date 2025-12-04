package com.example.emotionrecognition.data.repository

import com.example.hciproject.Dang.data.MongoDB

object ProgressRepository {
    private val collection = MongoDB.getDatabase().getCollection("progress")

    fun saveDailyProgress(correct: Int, wrong: Int, ratio: Double, emotionPracticed: Int) {
        // Code lưu tiến độ (như cũ) dùng biến `collection` ở trên
        // collection.insertOne(...)
    }

    fun getTodayStats(): Pair<Int, Int> {

        return Pair(0, 0)
    }
}