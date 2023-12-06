package com.sunjoolee.sparta_week1_mbti

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

class QuestionFragment : Fragment() {
    private val TAG = "QuestionFragment"

    private var questionType = 0

    private val questionTitle = listOf(
        R.string.question1_title,
        R.string.question2_title,
        R.string.question3_title,
        R.string.question4_title
    )
    private val questionTexts = listOf(
        listOf(R.string.question1_1,R.string.question1_2,R.string.question1_3),
        listOf(R.string.question2_1,R.string.question2_2,R.string.question2_3),
        listOf(R.string.question3_1,R.string.question3_2,R.string.question3_3),
        listOf(R.string.question4_1,R.string.question4_2,R.string.question4_3)
    )
    private val questionAnswers = listOf(
        listOf(
            R.string.question1_1_answer1,R.string.question1_1_answer2,
            R.string.question1_2_answer1,R.string.question1_2_answer2,
            R.string.question1_3_answer1,R.string.question1_3_answer2,
        ),
        listOf(
            R.string.question2_1_answer1,R.string.question2_1_answer2,
            R.string.question2_2_answer1,R.string.question2_2_answer2,
            R.string.question2_3_answer1,R.string.question2_3_answer2,
        ),
        listOf(
            R.string.question3_1_answer1,R.string.question3_1_answer2,
            R.string.question3_2_answer1,R.string.question3_2_answer2,
            R.string.question3_3_answer1,R.string.question3_3_answer2,
        ),
        listOf(
            R.string.question4_1_answer1,R.string.question4_1_answer2,
            R.string.question4_2_answer1,R.string.question4_2_answer2,
            R.string.question4_3_answer1,R.string.question4_3_answer2,
        )
    )

    companion object{
        private val TAG = "QuestionFragment-companion object"

        private const val ARG_QUESTION_TYPE = "QuestionType"

        fun newInstance(questionType:Int):QuestionFragment{
            Log.d(TAG, "newInstance) questionType: ${questionType}")
            val fragment = QuestionFragment()
            val args = Bundle()
            args.putInt(ARG_QUESTION_TYPE, questionType)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let{
            questionType = it.getInt(ARG_QUESTION_TYPE)
            Log.d(TAG, "onCreate) questionType: ${questionType}")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_question, container, false)

        Log.d(TAG, "onCreateView) questionType: ${questionType}")
        val questionTitleTextView = view.findViewById<TextView>(R.id.tv_question_title)
        val questionTextViews = listOf<TextView>(
            view.findViewById(R.id.tv_question_1),
            view.findViewById(R.id.tv_question_2),
            view.findViewById(R.id.tv_question_3)
        )
        val questionAnswerRadioGroups = listOf<RadioGroup>(
            view.findViewById(R.id.rg_answer_1),
            view.findViewById(R.id.rg_answer_2),
            view.findViewById(R.id.rg_answer_3)
        )

        questionTitleTextView.text = getString(questionTitle[questionType])
        val numOfQuestions = 3
        for(i in 0 until numOfQuestions) {
            questionTextViews[i].text = getString(questionTexts[questionType][i])

            val questionAnswerRadioButton1 = questionAnswerRadioGroups[i].getChildAt(0) as RadioButton
            val questionAnswerRadioButton2 = questionAnswerRadioGroups[i].getChildAt(1) as RadioButton
            questionAnswerRadioButton1.text = getString(questionAnswers[questionType][i*2])
            questionAnswerRadioButton2.text = getString(questionAnswers[questionType][i*2 + 1])
        }

        val nextButton = view.findViewById<Button>(R.id.btn_next)
        //마지막 질문지 인 경우:
        if(questionType == 3) nextButton.text = "결과 보기"
        else nextButton.text = "다음으로"
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated) questionType: ${questionType}")

        val questionAnswerRadioGroups = listOf<RadioGroup>(
            view.findViewById(R.id.rg_answer_1),
            view.findViewById(R.id.rg_answer_2),
            view.findViewById(R.id.rg_answer_3)
        )

        val nextButton = view.findViewById<Button>(R.id.btn_next)
        nextButton.setOnClickListener {
            //all: 모든 원소가 조건을 만족하는지 true/false 반환
            val isAllAnswered = questionAnswerRadioGroups.all{it.checkedRadioButtonId != -1}

            if(isAllAnswered){
                Log.d(TAG, "모든 질문에 응답했습니다.")
                val responses = questionAnswerRadioGroups.map { radioGroup ->
                    val firstRadioButton = radioGroup.getChildAt(0) as RadioButton
                    if (firstRadioButton.isChecked) 1 else 2
                }
                Log.d(TAG, "responses: ${responses[0]}, ${responses[1]}, ${responses[2]}")
                (activity as? TestActivity)?.questionResult?.getPageResult(responses)
                (activity as? TestActivity)?.moveToNextPage()
            }
            else{
                Log.d(TAG, "모든 질문에 응답하지 않았습니다.")
            }
        }
    }
}