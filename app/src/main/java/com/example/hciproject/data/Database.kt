package com.example.hciproject.data

import android.os.Build
import androidx.annotation.RequiresApi
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import org.litote.kmongo.* // Import hết mấy cái eq, and, inc
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import com.mongodb.client.model.UpdateOptions
import org.litote.kmongo.coroutine.CoroutineCollection
import java.time.LocalDate

// --- 1. KHU VỰC MODEL ---

// Model cho bảng thống kê (Cái mày đang cần)
data class UserDailyStat(
    @BsonId val id: ObjectId? = null,
    val userId: String,
    val dateKey: String, // Ví dụ: "2025-11-27"
    val correctCount: Int,
    val incorrectCount: Int,
    val accuracy: Double
)

// Model com.example.hciproject.data.User thường (Để code cũ không lỗi)
data class User(
    val _id: ObjectId? = null,
    val name: String,
    val age: Int,
    val email: String
)

// --- 2. KHU VỰC XỬ LÝ LOGIC ---

// Tao sửa lại hàm này: Phải nhận vào cái 'col' (collection) thì mới bắn lệnh được
@RequiresApi(Build.VERSION_CODES.O)
suspend fun addScoreToDailyStat(col: CoroutineCollection<UserDailyStat>, userId: String) {

    // Lấy ngày hiện tại
    val todayKey = LocalDate.now().toString()

    println(">> Đang cộng điểm cho user $userId vào ngày $todayKey...")

    // Điều kiện: Đúng com.example.hciproject.data.User VÀ Đúng Ngày
    val filter = and(
        UserDailyStat::userId eq userId,
        UserDailyStat::dateKey eq todayKey
    )

    // Lệnh: Cộng field correctCount lên 1 đơn vị
    val update = inc(UserDailyStat::correctCount, 1)

    // Option: Upsert = True (Chưa có thì tạo mới, có rồi thì update)
    val options = UpdateOptions().upsert(true)

    // Bắn!
    col.updateOne(filter, update, options)
}

// --- 3. CHƯƠNG TRÌNH CHÍNH (MAIN) ---

@RequiresApi(Build.VERSION_CODES.O)
suspend fun main() {
    // Kết nối Database
    val connectionString = "mongodb://localhost:27017"
    val client = KMongo.createClient(connectionString).coroutine
    val database = client.getDatabase("my_kotlin_db")

    // --- TEST PHẦN THỐNG KÊ ---

    // 1. Lấy collection com.example.hciproject.data.UserDailyStat
    val statCol = database.getCollection<UserDailyStat>("daily_stats")

    // 2. Giả vờ thằng user "U123" trả lời đúng câu hỏi
    val userId = "user_vip_123"

    // Gọi lần 1: Database chưa có gì -> Nó sẽ TỰ TẠO dòng mới với count = 1
    addScoreToDailyStat(statCol, userId)

    // Gọi lần 2: Database có rồi -> Nó sẽ CỘNG LÊN thành 2
    addScoreToDailyStat(statCol, userId)

    // 3. Kiểm tra lại xem ngon chưa
    val result = statCol.findOne(
        UserDailyStat::userId eq userId,
        UserDailyStat::dateKey eq LocalDate.now().toString()
    )

    println("------------------------------------------------")
    println("KẾT QUẢ CHECK HÀNG:")
    println("com.example.hciproject.data.User ID: ${result?.userId}")
    println("Ngày: ${result?.dateKey}")
    println("Số câu đúng: ${result?.correctCount}") // Chỗ này in ra 2 là chuẩn bài!
    println("------------------------------------------------")
}