package com.madgical.saarthiassessment.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.madgical.saarthiassessments.Repository
import com.madgical.saarthiassessments.model.Quiz

class AppViewModel(application: Application) : AndroidViewModel(application){
    var repository: Repository = Repository(application.applicationContext)

    fun getQuizData(): List<Quiz> {
        return repository.getQuizData()
    }
    fun geAllquizBylevel(level: String): List<Quiz> {

        val allQuizzes = repository.getQuizData()
        val quizzesByLevel = allQuizzes.filter { it.Level_Number == level }
        return quizzesByLevel
    }
    fun getLevelTopic(level: String): String{
        val allQuizzes = repository.getQuizData()
        val quizByLevel = allQuizzes.firstOrNull { it.Level_Number == level }
        return quizByLevel?.Level_Topic ?: ""

    }
    fun totalNumberOfLevels() : Int {
        val totalQuiz = repository.getQuizData()
        val totalLevels = totalQuiz.map { it.Level_Number }.distinct().filter { it != "Level Number" }
        return  totalLevels.size
    }
    fun checkCorrectAnswer(quiz: Quiz, userAnswer: Pair<Int, String>): Boolean {
        val (option, answer) = userAnswer
        val result = quiz.Correct_Answer == answer
        return result
    }

}

