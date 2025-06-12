package com.example.quizapplication1

import android.annotation.SuppressLint


object Constants {
    const val USER_NAME: String ="user_name"
    const val TOTAL_QUESTIONS: String="total_questions"
    const val CORRECT_ANSWER: String = "correct_answers"

    @SuppressLint("SuspiciousIndentation")
    fun getQuestions(): ArrayList<Question> {
        val questionsList = ArrayList<Question>()
     // Q1
     val que1 = Question (
         1,"Zu welcher Katzenrasse gehört diese Katze?",
         R.drawable.ic_siamese,
         "Siamkatze", "Britisch Kurzharr",
         "Bengal", "Heilige Birma", 1
        )
         questionsList.add(que1)
      //Q2
         val que2 = Question (
         2, "Zu welcher Katzenrasse gehört diese Katze?",
             R.drawable.ic_siberian,
             "Himalayakatze", "Somali-Katze",
             "Deutsche Langhaarkatze", "Siberische Katze", 4
         )
         questionsList.add(que2)
     // Q3
        val que3 = Question (
         3, "Zu welcher Katzenrasse gehört diese Katze?",
            R.drawable.ic_munchkin,
            "Ragdoll", "Munchkin",
            "Norwegische Waldkatze", "Peterbald", 2
        )
        questionsList.add(que3)
     // Q4
        val que4 = Question (
        4, "Zu welcher Katzenrasse gehört diese Katze?",
            R.drawable.ic_mainecoon,
            "Ragamuffin", "Scottish Fold",
            "Maine Coon", "Neva Masquarade", 3
        )
        questionsList.add(que4)
    // Q5
        val que5 = Question (
        5, "Zu welcher Katzenrasse gehört diese Katze?",
        R.drawable.ic_british_long_hair,
        "Deutsche Langhaarkatze", "Britisch Langhaar",
          "Lykoi", "Perserkatze", 2
        )
        questionsList.add(que5)
        return questionsList
    }
    var MAX_PROGRESS_VALUE = 5
}