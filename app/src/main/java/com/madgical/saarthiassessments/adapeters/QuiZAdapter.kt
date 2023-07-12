package com.madgical.saarthiassessments.adapeters


import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.madgical.saarthiassessment.viewmodel.AppViewModel
import com.madgical.saarthiassessments.R
import com.madgical.saarthiassessments.model.Quiz

class QuizAdapter(

    private val viewModel: AppViewModel,
    private val currentLevel: Int,
    private val quizList: List<Quiz>,
    private val recyclerView: RecyclerView,
    private val submitClickListener: SubmitClickListener

) : RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {

    private val selectedOptions = mutableMapOf<Int, Pair<Int, String>>()
    private val enteredTexts = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.quiz_item, parent, false)
        return QuizViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) {
        if (position == quizList.size - 1) {
            // Show submit button for the last question
            holder.submitButtonContainer.visibility = View.VISIBLE

        } else {
            // Hide submit button for other questions
            holder.submitButtonContainer.visibility = View.GONE
        }
        holder.submitButton.setOnClickListener {
            submitClickListener.onSubmitClick()
        }
        val currentQuiz = quizList[position]
        val currentLevel = currentQuiz.Level_Number
        val levelTopic = viewModel.getLevelTopic(currentLevel)
        holder.bind(currentQuiz, levelTopic)

        holder.optionOneContainer.setOnClickListener {
            selectOption(position, 1, holder.optionOneTextView.text.toString())
        }

        holder.optionTwoContainer.setOnClickListener {
            selectOption(position, 2, holder.optionTwoTextView.text.toString())
        }

        holder.optionThreeContainer.setOnClickListener {
            selectOption(position, 3, holder.optionThreeTextView.text.toString())
        }

        holder.optionFourContainer.setOnClickListener {
            selectOption(position, 4, holder.optionFourTextView.text.toString())
        }

        val selectedOption = selectedOptions[position]
        if (selectedOption != null) {
            updateContainerBackgrounds(holder, selectedOption.first)
        } else {
            resetContainerBackgrounds(holder)
        }
    }

    override fun getItemCount(): Int {
        return quizList.size
    }

    private fun selectOption(position: Int, option: Int, text: String) {
        selectedOptions[position] = Pair(option, text)
        notifyDataSetChanged()
    }

    private fun updateContainerBackgrounds(holder: QuizViewHolder, selectedOption: Int?) {
        val defaultBackground = R.drawable.default_option_background
        val selectedBackground = R.drawable.selected_option_background

        val optionOneBackground =
            if (selectedOption == 1) selectedBackground else defaultBackground
        val optionTwoBackground =
            if (selectedOption == 2) selectedBackground else defaultBackground
        val optionThreeBackground =
            if (selectedOption == 3) selectedBackground else defaultBackground
        val optionFourBackground =
            if (selectedOption == 4) selectedBackground else defaultBackground

        holder.optionOneContainer.setBackgroundResource(optionOneBackground)
        holder.optionTwoContainer.setBackgroundResource(optionTwoBackground)
        holder.optionThreeContainer.setBackgroundResource(optionThreeBackground)
        holder.optionFourContainer.setBackgroundResource(optionFourBackground)
    }

    private fun resetContainerBackgrounds(holder: QuizViewHolder) {
        val defaultBackground = R.drawable.default_option_background

        holder.optionOneContainer.setBackgroundResource(defaultBackground)
        holder.optionTwoContainer.setBackgroundResource(defaultBackground)
        holder.optionThreeContainer.setBackgroundResource(defaultBackground)
        holder.optionFourContainer.setBackgroundResource(defaultBackground)
    }

    fun getSelectedOptions(): List<Pair<Int, String>> {
        val selectedAnswers = mutableListOf<Pair<Int, String>>()
        for (position in 0 until quizList.size) {
            val answer = selectedOptions[position]
            selectedAnswers.add(answer ?: Pair(0, ""))
        }
        return selectedAnswers
    }

    fun resetSelectedOption(position: Int) {
        selectedOptions.remove(position)
        notifyDataSetChanged()
    }

    fun getEnteredTexts(): List<Pair<Int, String>> {
        val enteredTextsWithPositions = mutableListOf<Pair<Int, String>>()
        for (position in enteredTexts.indices) {
            val enteredText = enteredTexts[position]
            enteredTextsWithPositions.add(Pair(position, enteredText))
        }
        return enteredTextsWithPositions
    }

    fun getAnswerType(position: Int): String {
        val quiz = quizList[position]
        return quiz.Answer_Type
    }

    inner class QuizViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val topicContainer: RelativeLayout = itemView.findViewById(R.id.topicContainer)
       private val gapAfterTopic: RelativeLayout = itemView.findViewById(R.id.greyGap)
        private val topicText: TextView = itemView.findViewById(R.id.topictext)
        private val topic: TextView = itemView.findViewById(R.id.topic)
        private val questionNumberTextView: TextView =
            itemView.findViewById(R.id.questionnumber)
        private val questionTextView: TextView = itemView.findViewById(R.id.question)
        val optionOneTextView: TextView = itemView.findViewById(R.id.answernumber)
        val optionOneContainer: RelativeLayout = itemView.findViewById(R.id.custombutton)
        val optionTwoTextView: TextView = itemView.findViewById(R.id.answernumbertwo)
        val optionTwoContainer: RelativeLayout = itemView.findViewById(R.id.custombuttontwo)
        val optionThreeTextView: TextView = itemView.findViewById(R.id.answernumberthree)
        val optionThreeContainer: RelativeLayout =
            itemView.findViewById(R.id.custombuttonthree)
        val optionFourTextView: TextView = itemView.findViewById(R.id.answernumberfour)
        val optionFourContainer: RelativeLayout =
            itemView.findViewById(R.id.custombuttonfour)
        var answerTypeText: EditText = itemView.findViewById(R.id.textanswer)
        val answerTextContainer: RelativeLayout =
            itemView.findViewById(R.id.answertextcontainer)
        val questionTypeImageView: ImageView = itemView.findViewById(R.id.imagequestion)

        val submitButton: Button = itemView.findViewById(R.id.button_submit)
        val submitButtonContainer :RelativeLayout = itemView.findViewById(R.id.button_mainContainer)
        private var answerType: String = ""

        fun bind(quiz: Quiz, levelTopic: String) {

            if (adapterPosition == 0) {
                topicContainer.visibility = View.VISIBLE
                gapAfterTopic.visibility = View.VISIBLE
                topic.text = levelTopic
            } else {
                topicContainer.visibility = View.GONE
            }
            val questionNumber = adapterPosition % 5 + 1
            questionNumberTextView.text = questionNumber.toString()
           // questionNumberTextView.text = quiz.Question_Number
            questionTextView.text = quiz.Question
            optionOneTextView.text = quiz.Option_one
            optionTwoTextView.text = quiz.Option_two
            optionThreeTextView.text = quiz.Option_three
            optionFourTextView.text = quiz.Option_four

            answerTypeText.setText(quiz.Option_one)


            if (quiz.Answer_Type == "MCQ") {
                answerTypeText.visibility = View.GONE
            } else {
                answerTypeText.visibility = View.VISIBLE
                optionOneContainer.visibility = View.GONE
                optionTwoContainer.visibility = View.GONE
                optionThreeContainer.visibility = View.GONE
                optionFourContainer.visibility = View.GONE
            }
            if (quiz.Question_Type == "Image") {
                Glide.with(itemView.context)
                    .load(quiz.Question)
                    .into(questionTypeImageView)
                questionTypeImageView.visibility = View.VISIBLE
                questionTextView.visibility = View.GONE
            } else {
                questionTypeImageView.visibility = View.GONE
                questionTextView.text = quiz.Question
            }
            answerType = quiz.Answer_Type
            answerTypeText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    // Not needed for this implementation
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    // Update the entered text for this position
                    enteredTexts[adapterPosition] = s?.toString() ?: ""
                }

                override fun afterTextChanged(s: Editable?) {
                    // Not needed for this implementation
                }
            })

            // Initialize the entered text for this position
            enteredTexts.add(adapterPosition, answerTypeText.text.toString())
        }
    }




}

interface SubmitClickListener {
    fun onSubmitClick()
}

