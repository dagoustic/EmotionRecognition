package com.example.hciproject.Dang.data.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun DashboardScreen() {
    // 1. Khai báo biến trạng thái (State)
    // Khi biến này thay đổi, giao diện sẽ tự cập nhật
    var correctCount by remember { mutableIntStateOf(0) }
    var wrongCount by remember { mutableIntStateOf(0) }
    var isLoading by remember { mutableStateOf(true) }

    // 2. Load dữ liệu (Chạy 1 lần khi màn hình hiện lên)
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            try {
                // --- PHẦN 1: KẾT NỐI MONGODB (Bỏ comment khi đã cấu hình xong Repository) ---
                /*
                val collection = MongoRepository.getCollection()
                // Tìm dữ liệu theo ngày hôm nay, ví dụ dùng Filters...
                // val doc = collection.find(...).first()
                // if (doc != null) {
                //     correctCount = doc.getInteger("correct_count", 0)
                //     wrongCount = doc.getInteger("wrong_count", 0)
                // }
                */

                // --- PHẦN 2: DỮ LIỆU GIẢ (DEMO) ---
                // Tạm thời dùng cái này để test giao diện trước
                delay(1000) // Giả vờ đợi load 1 giây
                correctCount = 12 // Số giả
                wrongCount = 4    // Số giả

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }

    Scaffold(
        containerColor = Color(0xFFF5F5F5) // Màu nền xám nhạt
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- Tiêu đề ---
            Text(
                text = "Tiến độ hôm nay",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333),
                modifier = Modifier.padding(top = 20.dp, bottom = 30.dp)
            )

            // --- Khu vực hiển thị thẻ thống kê ---
            if (isLoading) {
                // Hiện vòng quay loading nếu đang tải
                CircularProgressIndicator(color = Color(0xFF2196F3))
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Thẻ ĐÚNG
                    StatCard(
                        number = correctCount,
                        label = "Đúng",
                        backgroundColor = Color(0xFFE8F5E9), // Xanh lá nhạt
                        textColor = Color(0xFF2E7D32),       // Xanh lá đậm
                        modifier = Modifier.weight(1f)
                    )

                    // Thẻ SAI
                    StatCard(
                        number = wrongCount,
                        label = "Sai",
                        backgroundColor = Color(0xFFFFEBEE), // Đỏ nhạt
                        textColor = Color(0xFFC62828),       // Đỏ đậm
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f)) // Đẩy nút xuống dưới đáy

            // --- Nút Bắt đầu ---
            Button(
                onClick = {
                    // TODO: Code chuyển sang màn hình bài tập
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2196F3) // Màu xanh dương
                ),
                elevation = ButtonDefaults.buttonElevation(8.dp)
            ) {
                Text(
                    text = "Bắt đầu bài tập",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

// 4. Component con: Thẻ thống kê (Tái sử dụng)
@Composable
fun StatCard(
    number: Int,
    label: String,
    backgroundColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(150.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = number.toString(),
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
            Text(
                text = label,
                fontSize = 18.sp,
                color = Color.Gray
            )
        }
    }
}