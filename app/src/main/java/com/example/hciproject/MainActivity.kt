package com.example.hciproject // Giữ nguyên package của bạn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.hciproject.ui.dashboard.DashboardScreen

// Đừng quên import DashboardScreen nếu nó bị đỏ (Bấm vào nó rồi nhấn Alt + Enter)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // 👇 GỌI MÀN HÌNH CỦA BẠN Ở ĐÂY
            DashboardScreen()
        }
    }
}