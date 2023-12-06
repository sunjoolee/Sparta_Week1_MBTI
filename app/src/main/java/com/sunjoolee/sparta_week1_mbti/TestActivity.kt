package com.sunjoolee.sparta_week1_mbti

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2

class TestActivity : AppCompatActivity() {
    private val TAG = "TestActivity"

    private lateinit var viewPager: ViewPager2
    val questionResult = QuestionResult()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        viewPager = findViewById(R.id.viewpager)
        viewPager.adapter = ViewPagerAdapter(this)
        //사용자가 화면을 스와이프 했을 때 페이지 넘어가기 허용 X
        viewPager.isUserInputEnabled = false
    }

    fun moveToNextPage(){
        Log.d(TAG, "moveToNextPage) currentItem: ${viewPager.currentItem}")
        //마지막 페이지인 경우: 결과 페이지로 이동
        val currentItem = viewPager.currentItem
        if(currentItem == 3){
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("results", questionResult.results.toIntArray())
            startActivity(intent)
        }
        else viewPager.currentItem = currentItem+1
    }
}

class QuestionResult{
    private val TAG = "QuestionResult"

    //results[i]: i번째 질문지에서 가장 많이 나온 응답의 번호
    val results = mutableListOf<Int>()
    fun getPageResult(responses: List<Int>){
        //responses: 질문지에서 사용자가 응답한 결과
        //mostFrequentResponse: responses에서 가장 많이 나온 응답의 번호
        val mostFrequentResponse = responses.groupingBy { it }.eachCount().maxByOrNull{it.value}?.key
        Log.d(TAG, "getPageResult) mostFrequentResponse: ${mostFrequentResponse}")
        //ex. reponses = [1,1,2]
        //responses.groupingBy{it}: (key:1, value:1), (key:1, value:1), (key:2, value:2)
        //~.eachCount(): (key:1, value: 2), (key:2, value:1)
        //~.maxByOrNull{it.value}: (key:1, value:2)
        //~.key: 1

        mostFrequentResponse?.let{results.add(it)}
    }
}