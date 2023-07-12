package com.madgical.saarthiassessments.model

data class Quiz(
    val Question_Number: String,
    val Level_Number: String,
    val Level_Topic: String,
    val Instruction: String,
    val Question: String,
    val Question_Type: String,
    val Answer_Type: String,
    val Option_one: String,
    val Option_two: String,
    val Option_three: String,
    val Option_four: String,
    val Correct_Answer: String
)