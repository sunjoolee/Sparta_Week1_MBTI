package com.sunjoolee.sparta_week1_mbti

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import java.util.Locale

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val resultMBTITextView = findViewById<TextView>(R.id.tv_result_mbti)
        val resultImageImageView = findViewById<ImageView>(R.id.iv_result_image)
        val retryButton = findViewById<Button>(R.id.btn_retry)

        //(1) resultMBTITextView에 테스트 결과 반영하기
        val results = intent.getIntArrayExtra("results")?: intArrayOf(1,1,1,1)
        val result_types = listOf(
            listOf("E","I"),listOf("N","S"),listOf("T","F"),listOf("J","P")
        )
        var result_mbti = ""
        for(i in 0 until 4) {
            result_mbti += result_types[i][results[i] - 1]
        }
        resultMBTITextView.text = result_mbti

        //(2) resultImageView에 테스트 결과 반영하기
        val imgResource = resources.getIdentifier("ic_${result_mbti.toLowerCase()}", "drawable", packageName)
        resultImageImageView.setImageResource(imgResource)
        
        retryButton.setOnClickListener { 
            //시작 화면으로 돌아가기
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}