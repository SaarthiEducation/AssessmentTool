package com.madgical.saarthiassessments

import android.content.Context
import android.util.Log
import com.madgical.saarthiassessments.model.Quiz
import com.opencsv.CSVReader
import java.io.InputStreamReader

open class Repository(private val context: Context) {

//    fun getQuizData(): List<Quiz> {
//        val quizData = mutableListOf<Quiz>()

    open fun getQuizData(): List<Quiz> {
        val quizData = mutableListOf<Quiz>()

        try {
            val inputStream = context.assets.open("quiz_data.csv")
            val reader = InputStreamReader(inputStream)
            val csvReader = CSVReader(reader)

            var record = csvReader.readNext()

            while (record != null) {
                val quiz = Quiz(
                    Question_Number = record[0],
                    Level_Number = record[1],
                    Level_Topic = record[2],
                    Instruction = record[3],
                    Question = record[4],
                    Question_Type = record[5],
                    Answer_Type = record[6],
                    Option_one = record[7],
                    Option_two = record[8],
                    Option_three = record[9],
                    Option_four = record[10],
                    Correct_Answer = record[11]
                )
                quizData.add(quiz)
                record = csvReader.readNext()
            }

            csvReader.close()
            reader.close()
            inputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        Log.i("quizDataRepo", quizData.toString())



        return quizData
    }

}


