package com.madgical.saarthiassessments.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.madgical.saarthiassessment.viewmodel.AppViewModel
import com.madgical.saarthiassessments.R
import com.madgical.saarthiassessments.adapeters.QuizAdapter
import com.madgical.saarthiassessments.adapeters.SubmitClickListener
import com.madgical.saarthiassessments.model.Quiz

class TestScreen : Fragment(), SubmitClickListener {
    private lateinit var quizAdapter: QuizAdapter
    private lateinit var currentLevel: String
    private var totalCorrectAnswer: Int = 0
    private lateinit var quizList: List<Quiz>
    private var totalNumberOflevels: Int = 0
    private var correctAnswerInLevel: HashMap<String, Int> = HashMap()

    private val viewModel: AppViewModel by lazy {
        ViewModelProvider(requireActivity()).get(AppViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_test_screen, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentLevel = arguments?.getString("currentLevel") ?: "10"
        val currentTopic = viewModel.getLevelTopic(currentLevel)

        var levelTopic = arguments?.getString("levelTopic") ?: currentTopic
        totalNumberOflevels = viewModel.totalNumberOfLevels()
        correctAnswerInLevel = arguments?.getSerializable("correctAnswerInLevel") as? HashMap<String, Int>
            ?: HashMap()

        if (correctAnswerInLevel == null) {
            correctAnswerInLevel = HashMap()
        }

        // Get all the questions for the current level
        val allQuizList = viewModel.geAllquizBylevel(currentLevel)

        // Shuffle the questions randomly
        val shuffledQuizList = allQuizList.shuffled()

        // Choose the first 5 questions from the shuffled list
        quizList = shuffledQuizList.take(5)

        Log.i("quizList", quizList.toString())
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        quizAdapter = QuizAdapter(viewModel, currentLevel.toInt(), quizList, recyclerView, this)
        recyclerView.adapter = quizAdapter

        view.findViewById<Button>(R.id.backbutton).setOnClickListener {
            showLeaveConfirmationPopup()
        }
    }

    override fun onSubmitClick() {

        val answerType = quizAdapter.getAnswerType(0)
        if(answerType == "Text"){
            val userAnswers = quizAdapter.getEnteredTexts()
            for ((index, quiz) in quizList.withIndex()) {
                if (index < userAnswers.size) {
                    val userAnswer = userAnswers[index]
                    val isCorrect = viewModel.checkCorrectAnswer(quiz, userAnswer)

                    // Perform any required actions based on whether the answer is correct or not
                    if (isCorrect) {
                        // Increment the total correct answers count
                        totalCorrectAnswer++
                        // Update the levelWiseCorrectAnswers map
                        correctAnswerInLevel?.set(currentLevel, totalCorrectAnswer)
                    }
                }
            }
        }else{
            val userAnswers = quizAdapter.getSelectedOptions()

            // Iterate through the quizList and check each answer
            for ((index, quiz) in quizList.withIndex()) {
                val userAnswer = userAnswers[index]
                val isCorrect = viewModel.checkCorrectAnswer(quiz, userAnswer)

                // Perform any required actions based on whether the answer is correct or not
                if (isCorrect) {
                    // Increment the total correct answers count
                    totalCorrectAnswer++
                    // Update the levelWiseCorrectAnswers map
                    correctAnswerInLevel?.set(currentLevel, totalCorrectAnswer)
                }
            }
        }
        // Get the user's selected answers from the QuizAdapter

        correctAnswerInLevel[currentLevel] = totalCorrectAnswer


        val answerTextType = quizAdapter.getEnteredTexts()

        // Implement the logic for level Increment or Decrement or navigation to the score screen
        if (currentLevel.toInt() < totalNumberOflevels) {
            if (totalCorrectAnswer >= 4) {
                val nextLevel = currentLevel.toInt() + 1
                currentLevel = nextLevel.toString()
                val nextLevelTopic = viewModel.getLevelTopic(currentLevel)
                findNavController().navigate(
                    R.id.action_testScreen_self,
                    Bundle().apply {
                        putSerializable("correctAnswerInLevel", correctAnswerInLevel)
                        putString("currentLevel", currentLevel)
                        putString("levelTopic", nextLevelTopic)
                        putInt(
                            "totalCorrectAnswer",
                            totalCorrectAnswer
                        )
                    }
                )
            }
            else if (totalCorrectAnswer < 3 && currentLevel.toInt() != 1 ) {
                currentLevel = (currentLevel.toInt() - 1).toString()
                val nextLevelTopic = viewModel.getLevelTopic(currentLevel)
                findNavController().navigate(
                    R.id.action_testScreen_self,
                    Bundle().apply {
                        putSerializable("correctAnswerInLevel", correctAnswerInLevel)
                        putString("currentLevel", currentLevel)
                        putString("levelTopic", nextLevelTopic)
                        putInt(
                            "totalCorrectAnswer",
                            totalCorrectAnswer
                        )
                    }
                )
            }
            else if (totalCorrectAnswer < 3 && currentLevel.toInt() == 1 ) {
                currentLevel = (currentLevel.toInt() - 1).toString()
                val nextLevelTopic = viewModel.getLevelTopic(currentLevel)
                findNavController().navigate(R.id.action_testScreen_to_homeScreen)
            }
            else if (totalCorrectAnswer == 3) {
                currentLevel
                val nextLevelTopic = viewModel.getLevelTopic(currentLevel)
                findNavController().navigate(
                    R.id.action_testScreen_self,
                    Bundle().apply {
                        putSerializable("correctAnswerInLevel", correctAnswerInLevel)
                        putString("currentLevel", currentLevel)
                        putString("levelTopic", nextLevelTopic)
                    }
                )
            }
        }
        else if(currentLevel.toInt()== totalNumberOflevels) {
            if (totalCorrectAnswer >= 4) {
                val nextLevel = currentLevel.toInt()
                currentLevel = nextLevel.toString()
                val nextLevelTopic = viewModel.getLevelTopic(currentLevel)
                findNavController().navigate(
                    R.id.action_testScreen_to_scoreScreen,
                    Bundle().apply {
                        putSerializable("correctAnswerInLevel", correctAnswerInLevel)
                        putString("currentLevel", currentLevel)
                        putString("levelTopic", currentLevel)
                        putInt(
                            "totalCorrectAnswer",
                            totalCorrectAnswer
                        )
                    }
                )
            }
            else if (totalCorrectAnswer < 3  ) {
                currentLevel = (currentLevel.toInt() - 1).toString()
                val nextLevelTopic = viewModel.getLevelTopic(currentLevel)
                findNavController().navigate(
                    R.id.action_testScreen_self,
                    Bundle().apply {
                        putSerializable("correctAnswerInLevel", correctAnswerInLevel)
                        putString("currentLevel", currentLevel)
                        putString("levelTopic", nextLevelTopic)
                        putInt(
                            "totalCorrectAnswer",
                            totalCorrectAnswer
                        )
                    }
                )
            }
            else if (totalCorrectAnswer == 3) {
                currentLevel
                val nextLevelTopic = viewModel.getLevelTopic(currentLevel)
                findNavController().navigate(
                    R.id.action_testScreen_self,
                    Bundle().apply {
                        putSerializable("correctAnswerInLevel", correctAnswerInLevel)
                        putString("currentLevel", currentLevel)
                        putString("levelTopic", nextLevelTopic)
                    }
                )
            }
        }
        // Reset selected options in QuizAdapter
        quizAdapter.resetSelectedOption(0)

    }
    //Show the popup
    private fun showLeaveConfirmationPopup() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.backbuttonpopup, null)
        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(false)
        val alertDialog = dialogBuilder.create()

        val leaveButton = dialogView.findViewById<Button>(R.id.btn_leave)
        val stayButton = dialogView.findViewById<Button>(R.id.btn_stay)

        leaveButton.setOnClickListener {
            findNavController().navigate(R.id.action_testScreen_to_homeScreen)
            alertDialog.dismiss()

        }

        stayButton.setOnClickListener {

            alertDialog.dismiss()

        }

        alertDialog.show()
    }

}

