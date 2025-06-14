package com.example.quizapplication1

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.content.Intent
import android.graphics.Typeface
import android.media.MediaPlayer
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt


 class QuizActivity : AppCompatActivity(), View.OnClickListener  {
    private var mediaPlayer: MediaPlayer? = null
    private var mCurrentPosition: Int = 1
    private var mQuestionList: ArrayList<Question>?= null
    private var mSelectedOptionPosition: Int = 0
    private var isSelectedAnswer: Boolean = false
    private var mUserName: String? = null
    private var mCorrectAnswer: Int = 0

    private var progressBar: ProgressBar?=null
    private var tvProgress: TextView? = null
    private var tvQuestion: TextView? = null

    private var ivImage: ImageView? = null
    private var tvOptionOne: TextView? = null
    private var tvOptionTwo: TextView? = null
    private var tvOptionThree: TextView? = null
    private var tvOptionFour: TextView? = null
    private var btnSubmit: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        setPassingRetrieveDataIntent()
        setUpView()
        getQuestions()
        setQuestionList()
        defaultOptionsView()
        mediaPlayer = MediaPlayer.create(this,R.raw.background_music)
        mediaPlayer?.isLooping= true
        mediaPlayer?.start()
    }
     override fun onPause() {
         super.onPause()
         mediaPlayer?.pause()
     }
     override fun onResume() {
         super.onResume()
         mediaPlayer?.start()
     }
     override fun onDestroy() {
         super.onDestroy()
         mediaPlayer?.release()
         mediaPlayer = null
     }

    fun setUpView() {
        progressBar = findViewById(R.id.progressBar)
        tvProgress = findViewById(R.id.tv_progress)
        tvQuestion = findViewById(R.id.tv_question)
        ivImage = findViewById(R.id.tv_image)
        tvOptionOne = findViewById(R.id.tv_option_one)
        tvOptionTwo = findViewById(R.id.tv_option_two)
        tvOptionThree = findViewById(R.id.tv_option_three)
        tvOptionFour = findViewById(R.id.tv_option_four)
        btnSubmit = findViewById(R.id.btn_submit)

        tvOptionOne?.setOnClickListener(this)
        tvOptionTwo?.setOnClickListener(this)
        tvOptionThree?.setOnClickListener(this)
        tvOptionFour?.setOnClickListener(this)
        btnSubmit?.setOnClickListener(this)
    }

    fun setPassingRetrieveDataIntent() {
        mUserName = intent.getStringExtra(Constants.USER_NAME)
    }

    fun getQuestions() {
        mQuestionList = Constants.getQuestions()
    }

    @SuppressLint("SetTextI18n")
    fun setQuestionList() {
        defaultOptionsView()
        mQuestionList?.let {
            val questionsList = it
            var currentPosition = mCurrentPosition
            val question : Question = questionsList[currentPosition - 1]

            ivImage?.setImageResource(question.image)
            progressBar?.progress = currentPosition

            tvProgress?.text = "$currentPosition/${questionsList.size}"
            tvQuestion?.text = question.question
            tvOptionOne?.text = question.optionOne
            tvOptionTwo?.text = question.optionTwo
            tvOptionThree?.text = question.optionThree
            tvOptionFour?.text = question.optionFour

            if (currentPosition > questionsList.size) {
                btnSubmit?.text = "BEENDEN"
            } else {
                btnSubmit?.text = "ABSCHICKEN"
            }
        }
    }

    private fun defaultOptionsView() {
        val options = ArrayList<TextView>()
        tvOptionOne?.let {
            options.add(0,it)
        }
        tvOptionTwo?.let {
            options.add(1,it)
        }
        tvOptionThree?.let {
            options.add(2,it)
        }
        tvOptionFour?.let {
            options.add(3,it)
        }

        for (option in options) {
            option.setTextColor("#7A8089".toColorInt())
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this,R.drawable.default_option_bg)
        }
    }

    private fun selectedOptionView(tv:TextView, selectedOptionNum: Int) {
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum
        isSelectedAnswer = true
        tv.setTextColor("#671967".toColorInt())
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this,R.drawable.selected_option_bg)
    }

    override fun onClick(view: View?) {
        val isAnswerNotSubmitDone = btnSubmit?.text == getString(R.string.abschicken)
        when (view?.id) {

            R.id.tv_option_one -> {
                if  (isAnswerNotSubmitDone) {
                    tvOptionOne?.let {
                        selectedOptionView(it,1)
                    }
                }
            }

            R.id.tv_option_two -> {
                if  (isAnswerNotSubmitDone) {
                    tvOptionTwo?.let {
                        selectedOptionView(it,2)
                    }
                }
            }

            R.id.tv_option_three -> {
                if  (isAnswerNotSubmitDone) {
                    tvOptionThree?.let {
                        selectedOptionView(it,3)
                    }
                }

            }

            R.id.tv_option_four -> {
                if  (isAnswerNotSubmitDone) {
                    tvOptionFour?.let {
                        selectedOptionView(it,4)
                    }
                }

            }

            R.id.btn_submit -> {
                btnSubmit?.let {
                    if (mSelectedOptionPosition == 0) {
                        if (isSelectedAnswer == true) {
                            isSelectedAnswer = false

                            mCurrentPosition++

                            when {
                                mCurrentPosition <= mQuestionList!!.size -> {
                                    setQuestionList()
                                }
                                else -> {
//                                    Toast.makeText(this,"Congratulation you made to the end",Toast.LENGTH_LONG).show()
                                    val intent = Intent(this,ResultActivity::class.java)
                                    intent.putExtra(Constants.USER_NAME, mUserName)
                                    intent.putExtra(Constants.CORRECT_ANSWER, mCorrectAnswer)
                                    intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionList?.size)
                                    startActivity(intent)
                                    finish()
                                }
                            }
                        } else {
                            Toast.makeText(this,"Bitte eine Option auswählen",Toast.LENGTH_SHORT).show()
                        }

                    } else {
                        val question = mQuestionList?.get(mCurrentPosition - 1)
                        question?.let {

                            if(it.correctAnswer != mSelectedOptionPosition) {
                                answerView(mSelectedOptionPosition,R.drawable.wrong_option_bg)
                            } else {
                                mCorrectAnswer++
                            }
                            answerView(it.correctAnswer,R.drawable.correct_option_bg)

                            if (mCurrentPosition == mQuestionList!!.size){
                                btnSubmit?.text = "BEENDEN"
                            } else {
                                btnSubmit?.text = "NÄCHSTE FRAGE"
                            }

                            mSelectedOptionPosition = 0

                        }


                    }

                }
            }
        }
    }

    private fun answerView(answer: Int, drawableView: Int) {
        when (answer) {
            1 -> {
                tvOptionOne?.background = ContextCompat.getDrawable(this,drawableView)
            }
            2 -> {
                tvOptionTwo?.background = ContextCompat.getDrawable(this,drawableView)
            }
            3 -> {
                tvOptionThree?.background = ContextCompat.getDrawable(this,drawableView)
            }
            4 -> {
                tvOptionFour?.background = ContextCompat.getDrawable(this,drawableView)
            }
        }
    }
  }

