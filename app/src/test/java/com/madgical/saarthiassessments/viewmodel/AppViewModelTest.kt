package com.madgical.saarthiassessments.viewmodel

import android.app.Application
import android.content.Context
import com.madgical.saarthiassessment.viewmodel.AppViewModel
import com.madgical.saarthiassessments.Repository
import com.madgical.saarthiassessments.model.Quiz
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class AppViewModelTest {

    @Mock
    private lateinit var application: Application

    @Mock
    private lateinit var repository: Repository

    private lateinit var appViewModel: AppViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        `when`(application.applicationContext).thenReturn(mock(Context::class.java))
        appViewModel = AppViewModel(application)
        appViewModel.repository = repository
    }


    @Test
    fun testGetQuizData() {
        val quizList = listOf(
            Quiz("1", "1", "Count & write", "Answer the following Question", "https://thumbs.dreamstime.com/z/printable-worksheet-kindergarten-preschool-count-write-numbers-to-harvest-ripe-berries-fruits-122801253.jpg", "Image", "MCQ",
                "1", "5", "7", "9", "9"),

        )

        `when`(repository.getQuizData()).thenReturn(quizList)

        val result = appViewModel.getQuizData()

        assertEquals(quizList, result)
    }



    @Test
    fun testGetAllQuizzesByLevel() {
        val quizList = listOf(
            Quiz("1", "1", "Count & write", "Answer the following Question", "https://thumbs.dreamstime.com/z/printable-worksheet-kindergarten-preschool-count-write-numbers-to-harvest-ripe-berries-fruits-122801253.jpg", "Image", "MCQ",
                "1", "5", "7", "9", "9"),
            Quiz("2", "1", "Count & write", "Answer the following Question", "https://thumbs.dreamstime.com/z/printable-worksheet-kindergarten-preschool-count-write-numbers-to-harvest-ripe-berries-fruits-122801253.jpg", "Image", "MCQ",
                "11", "7", "15", "19", "19"),
            Quiz("1", "2", "What comes after", "Answer the following Question", "8,_____", "String", "MCQ",
                "9", "10", "7", "6", "9"),
        )

        `when`(repository.getQuizData()).thenReturn(quizList)

        val result = appViewModel.geAllquizBylevel("1")

        val expected = listOf(
            Quiz("1", "1", "Count & write", "Answer the following Question", "https://thumbs.dreamstime.com/z/printable-worksheet-kindergarten-preschool-count-write-numbers-to-harvest-ripe-berries-fruits-122801253.jpg", "Image", "MCQ",
                "1", "5", "7", "9", "9"),
            Quiz("2", "1", "Count & write", "Answer the following Question", "https://thumbs.dreamstime.com/z/printable-worksheet-kindergarten-preschool-count-write-numbers-to-harvest-ripe-berries-fruits-122801253.jpg", "Image", "MCQ",
                "11", "7", "15", "19", "19"),
        )

        assertEquals(expected, result)
    }

    @Test
    fun getLevelTopic() {
        val quizList = listOf(
            Quiz("1", "1", "Count & write", "Answer the following Question", "https://thumbs.dreamstime.com/z/printable-worksheet-kindergarten-preschool-count-write-numbers-to-harvest-ripe-berries-fruits-122801253.jpg", "Image", "MCQ",
                "1", "5", "7", "9", "9"),
            Quiz("2", "1", "Count & write", "Answer the following Question", "https://thumbs.dreamstime.com/z/printable-worksheet-kindergarten-preschool-count-write-numbers-to-harvest-ripe-berries-fruits-122801253.jpg", "Image", "MCQ",
                "11", "7", "15", "19", "19"),
        )

        `when`(repository.getQuizData()).thenReturn(quizList)

        val result = appViewModel.getLevelTopic("1")

        assertEquals("Count & write", result)
    }

    @Test
    fun totalNumberOfLevels() {
        val quizList = listOf(
            Quiz("1", "1", "Count & write", "Answer the following Question", "https://thumbs.dreamstime.com/z/printable-worksheet-kindergarten-preschool-count-write-numbers-to-harvest-ripe-berries-fruits-122801253.jpg", "Image", "MCQ",
                "1", "5", "7", "9", "9"),
            Quiz("1", "2", "What comes after", "Answer the following Question", "8,_____", "String", "MCQ",
                "9", "10", "7", "6", "9"),
            Quiz("1", "3", "What comes before", "Answer the following Question", "____,14", "String", "MCQ",
                "12", "12", "13", "14", "13"),
        )

        `when`(repository.getQuizData()).thenReturn(quizList)

        val result = appViewModel.totalNumberOfLevels()

        assertEquals(3, result)
    }


    @Test
    fun checkCorrectAnswer() {
        val quiz =  Quiz("1", "2", "What comes after", "Answer the following Question", "8,_____", "String", "MCQ",
            "9", "10", "7", "6", "9")

        val userAnswer = Pair(1, "9")

        val result = appViewModel.checkCorrectAnswer(quiz, userAnswer)

        assertEquals(true, result)
    }
}