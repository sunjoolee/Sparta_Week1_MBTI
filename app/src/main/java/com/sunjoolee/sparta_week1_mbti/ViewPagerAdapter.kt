package com.sunjoolee.sparta_week1_mbti

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    //사용할 페이지의 수 (4개의 질문지 사용)
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return QuestionFragment.newInstance(position)
    }
}