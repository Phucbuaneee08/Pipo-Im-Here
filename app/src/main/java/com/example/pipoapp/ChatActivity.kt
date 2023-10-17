package com.example.pipoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pipoapp.databinding.ActivityChatBinding

private const val TAG = "ChatActivity"
class ChatActivity : AppCompatActivity() {
    private val binding by lazy { ActivityChatBinding.inflate(layoutInflater) }
    private val adapter by lazy { ChatAdapter() }
    private val listAsk = listOf<String>(
            "Tôi bị bỏng",
        "Tôi bị bỏng nước",
        "Tôi có thể dùng đá lạnh không",
        "Khá nhẹ, khoảng cấp độ 1",
        "Ở tay",
        "khá nặng, khoảng cấp độ 2",
        "Tôi rất đau, Vết bỏng có màu đen đã ăn vào xương và cơ"
    )
    private val listReply = listOf<String>(
        "Bỏng có thể rất đau đớn và khó chịu. Hãy bình tĩnh và nói cho tôi nguyên nhân khiến bạn bị bỏng",
        "Bỏng nước khá phổ biến, bây giờ bạn hãy xối nước mát và sạch vào vết bỏng nhẹ nhàng khoảng 10-15p",
        "Không dùng đá để làm mát vết bỏng, điều này khiết vết bỏng trở nên trầm trọng hơn, vùng da vừa bị bỏng thì bị lạnh đột ngột tế bào co lại khiến vết bỏng lâu khỏi và dễ bị loét hơn\n Trong lúc xối nước, cho tôi biết bạn bỏng khoảng cấp độ mấy - Bỏng nhẹ: Bỏng nhẹ chỉ ảnh hưởng đến lớp da ngoài cùng. Chúng thường đỏ và đau, nhưng không gây phồng rộp.\n - Bỏng vừa: Bỏng vừa ảnh hưởng đến lớp da ngoài cùng và lớp da bên dưới. Chúng có thể gây đau đớn, phồng rộp và chảy máu. \n - Bỏng nặng: Bỏng nặng ảnh hưởng đến tất cả các lớp da và có thể ảnh hưởng đến cơ bắp, xương và dây thần kinh.",
        "Bạn bị bỏng ở vị trí nào ?",
        "Tôi hiểu rồi, Sau khi rửa sạch, hãy che vết bỏng bằng băng gạc vô trùng hoặc băng dính. Điều này sẽ giúp bảo vệ vết bỏng khỏi nhiễm trùng.\nLưu ý: Tránh chọc hoặc gãi vết bỏng, Uống nhiều nước để giúp cơ thể bạn chữa lành vết bỏng",
        "Bây giờ bạn hãy làm theo các bước sau:\n" +
                "B1: vùng bỏng lớn thì không nên cởi bỏ quần áo khiến bị lột da vùng bỏng, nhanh chóng dùng kéo cắt áo quần ra tách khỏi vết bỏng tránh việc áo quần dính chặt vào vết bỏng khiến vết bỏng bị đau rát, dễ viêm nhiễm.\n" +
                "B2: Nhẹ nhàng tháo bỏ các tư trang cá nhân, vòng lắc hoặc đồng hồ, giày dép … trước khi vết bỏng bị sưng nề.:\n" +
                "B3: Sau đó hãy che vết bỏng bằng băng gạc vô trùng hoặc băng dính. Điều này sẽ giúp bảo vệ vết bỏng khỏi nhiễm trùng\n" +
                "B4: Đến cơ sở y tế gần nhất\n- Lưu ý: Tránh chọc hoặc gãi vết bỏng, Uống nhiều nước để giúp cơ thể bạn chữa lành vết bỏng",
        "-Hiện tại bạn đang bị bỏng cấp độ 3, hãy bình tĩnh và chọn chế độ gọi 115 trên giao diện App.\n" +
                "- Trong lúc đợi xe cấp cứu, bình tĩnh làm theo các thao tác sau:\n" +
                "B1: Bây giờ bạn hãy làm theo các bước sau: vùng bỏng lớn thì không nên cởi bỏ quần áo khiến bị lột da vùng bỏng, nhanh chóng dùng kéo cắt áo quần ra tách khỏi vết bỏng tránh việc áo quần dính chặt vào vết bỏng khiến vết bỏng bị đau rát, dễ viêm nhiễm.\n" +
                "B2: Nhẹ nhàng tháo bỏ các tư trang cá nhân, vòng lắc hoặc đồng hồ, giày dép … trước khi vết bỏng bị sưng nề.\n" +
                "B3: Sau đó hãy che vết bỏng bằng băng gạc vô trùng hoặc băng dính. Điều này sẽ giúp bảo vệ vết bỏng khỏi nhiễm trùng\n" +
                "- Bình tĩnh đợi 115 đến\n"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.recyclerView.layoutManager = LinearLayoutManager(this).apply {
            stackFromEnd = true
            reverseLayout = false
        }
        binding.recyclerView.adapter = adapter
        binding.imvSend.setOnClickListener {
            if (!binding.edtMessage.text.toString().isEmpty()) {
                val message = binding.edtMessage.text.toString()
                binding.edtMessage.setText("")
                Log.e(TAG, "onCreate: $message", )
                adapter.setAdapter(message)
                check(message)
            }
        }
    }
    private fun check(message:String){

        var index = -1;
        for (i in 0..listAsk.size-1) {
            if (listAsk.get(i) == message) {
                index = i
                break
            }
        }
        if (index == -1)
            adapter.setAdapter("Lỗi")
        else
            adapter.setAdapter(listReply[index])
    }
}