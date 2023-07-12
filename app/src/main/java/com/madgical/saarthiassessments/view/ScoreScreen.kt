package com.madgical.saarthiassessments.view

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.madgical.saarthiassessments.R

class ScoreScreen : Fragment() {
    private val handler = Handler()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_score_screen, container, false)
    }override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scoreTableLayout: TableLayout = view.findViewById(R.id.scoreTableLayoutvalue)
        val levelWiseCorrectAnswers: Map<*, *>? =
            arguments?.getSerializable("correctAnswerInLevel") as? Map<*, *>

        // Create Rows
        levelWiseCorrectAnswers?.forEach { (level, correctAnswers) ->

            val tableRow = TableRow(requireContext())

            // Set the row background drawable
            tableRow.background = ContextCompat.getDrawable(requireContext(), R.drawable.row_border)

            val levelTextView = TextView(requireContext())
            levelTextView.text = "Level $level"
            levelTextView.layoutParams =
                TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            levelTextView.gravity = Gravity.CENTER
            levelTextView.setTextColor(Color.BLACK)
            levelTextView.setBackgroundColor(Color.WHITE)
            levelTextView.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.row_border)

            // Set the TextView text color to bold
            levelTextView.setTypeface(null, Typeface.BOLD)

            tableRow.addView(levelTextView)

            val correctAnswersTextView = TextView(requireContext())
            correctAnswersTextView.text = correctAnswers.toString()
            correctAnswersTextView.layoutParams =
                TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            correctAnswersTextView.gravity = Gravity.CENTER
            levelTextView.setTextColor(Color.BLACK)
            correctAnswersTextView.setBackgroundColor(Color.WHITE)
            correctAnswersTextView.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.row_border)

            // Set the TextView text color to bold
            correctAnswersTextView.setTypeface(null, Typeface.BOLD)

            tableRow.addView(correctAnswersTextView)

            val totalQuestionsTextView = TextView(requireContext())
            totalQuestionsTextView.text = "5"
            totalQuestionsTextView.layoutParams =
                TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f)
            totalQuestionsTextView.gravity = Gravity.CENTER
            levelTextView.setTextColor(Color.BLACK)
            totalQuestionsTextView.setBackgroundColor(Color.WHITE)
            totalQuestionsTextView.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.row_border)

            // Set the TextView text color to bold
            totalQuestionsTextView.setTypeface(null, Typeface.BOLD)

            tableRow.addView(totalQuestionsTextView)

            scoreTableLayout.addView(tableRow)
        }

        view.findViewById<Button>(R.id.button_goback).setOnClickListener {
            findNavController().navigate(R.id.action_scoreScreen_to_homeScreen)
        }
        showInitialPopup()
    }
    //Show the popup
    private fun showInitialPopup() {
        val finalLevel = arguments?.getString("currentLevel") ?: ""
        Log.i("FinalLevel", finalLevel)
        view?.findViewById<Button>(R.id.backbutton)?.setOnClickListener {
            showLeaveConfirmationPopup()
        }
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.finalscreenpopup, null)
        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(false)
        val alertDialog = dialogBuilder.create()
        alertDialog.show()

        val finalLevelTextView = dialogView.findViewById<TextView>(R.id.finallevelNumber)
        finalLevelTextView.text = finalLevel

        handler.postDelayed({
            alertDialog.dismiss()
        }, 2000)
    }

    private fun showLeaveConfirmationPopup() {

        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.backbuttonpopup, null)
        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(false)
        val alertDialog = dialogBuilder.create()

        val leaveButton = dialogView.findViewById<Button>(R.id.btn_leave)
        val stayButton = dialogView.findViewById<Button>(R.id.btn_stay)

        leaveButton.setOnClickListener {
            findNavController().navigate(R.id.action_scoreScreen_to_homeScreen)
            alertDialog.dismiss()

        }

        stayButton.setOnClickListener {

            alertDialog.dismiss()

        }

        alertDialog.show()
    }



}
