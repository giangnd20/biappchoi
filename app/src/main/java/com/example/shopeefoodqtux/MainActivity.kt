package com.example.shopeefoodqtux

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShopeeFoodQTUXTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    QTUXHomeScreen()
                }
            }
        }
    }
}

@Composable
fun ShopeeFoodQTUXTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFFEE4D2D), // Màu cam đặc trưng ShopeeFood
            secondary = Color(0xFF2673DD),
            background = Color(0xFFF5F5F5)
        ),
        content = content
    )
}

data class RuleItem(
    val title: String,
    val description: String,
    val penalty: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QTUXHomeScreen() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Quy định cốt lõi", "Tra cứu lỗi & Điểm")

    val rules = listOf(
        RuleItem(
            title = "Cập nhật trạng thái đơn hàng",
            description = "Tuyệt đối không bấm trước trạng thái (Đã lấy/Đã giao) khi chưa hoàn thành thực tế để gian lận cước phí.",
            penalty = "Cộng điểm vi phạm, truy thu phí, khóa tài khoản."
        ),
        RuleItem(
            title = "Tác phong & Đồng phục",
            description = "Bắt buộc mặc đúng đồng phục ShopeeFood, giữ thái độ chuẩn mực trong suốt quá trình giao nhận.",
            penalty = "Ghi nhận vi phạm, nhắc nhở hoặc khóa tài khoản tạm thời."
        ),
        RuleItem(
            title = "Vận chuyển an toàn",
            description = "Không chở theo hành khách không liên quan hoặc hàng hóa ngoài đơn hàng khi đang làm nhiệm vụ.",
            penalty = "Xử lý theo quy định nội bộ và quy chế an toàn."
        )
    )

    val policies = listOf(
        RuleItem(
            title = "Hiệu lực điểm vi phạm",
            description = "Điểm vi phạm tự động cộng dồn và có hiệu lực lưu trữ trong vòng 60 ngày kể từ ngày ghi nhận.",
            penalty = "60 ngày"
        ),
        RuleItem(
            title = "Tạm ngừng nhận đơn",
            description = "Áp dụng khi tài xế không phát sinh giao hàng liên tục trong 120 ngày.",
            penalty = "120 ngày không hoạt động"
        ),
        RuleItem(
            title = "Cấn trừ tiền cọc & Công nợ",
            description = "Tiến hành cấn trừ tiền cọc nếu không hoạt động trên 1 năm; xử lý công nợ nếu quá 3 năm.",
            penalty = "1 năm / 3 năm"
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ShopeeFood QTUX 2026", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFEE4D2D))
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF5F5F5))
        ) {
            TabRow(selectedTabIndex = selectedTab, containerColor = Color.White) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title, fontWeight = FontWeight.Bold) },
                        selectedContentColor = Color(0xFFEE4D2D),
                        unselectedContentColor = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val currentList = if (selectedTab == 0) rules else policies
                items(currentList) { item ->
                    Card(
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = item.title,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF333333)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = item.description,
                                fontSize = 14.sp,
                                color = Color(0xFF666666)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = Color(0xFFFFEEEE)
                            ) {
                                Text(
                                    text = "Xử lý/Thời hạn: ${item.penalty}",
                                    fontSize = 12.sp,
                                    color = Color(0xFFEE4D2D),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
