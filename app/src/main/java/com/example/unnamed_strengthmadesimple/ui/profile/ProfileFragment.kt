package com.example.unnamed_strengthmadesimple.ui.profile

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.unnamed_strengthmadesimple.R
import com.example.unnamed_strengthmadesimple.database.Evaluation
import com.example.unnamed_strengthmadesimple.database.User
import com.example.unnamed_strengthmadesimple.databinding.FragmentProfileBinding
import kotlin.math.pow

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    // Use ViewModelProvider to initialize the ViewModel once
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Initialize ViewModel
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe user data
        profileViewModel.userLiveData.observe(viewLifecycleOwner) { user ->
            user?.let {
                updateUserLevelAndExperience(it) // Handle level-up and update experience
                binding.nameLabel.text = "Name: ${user.name}"
                binding.weightLabel.text = "Weight: ${user.weight} kg"
                binding.levelLabel.text = "Level: ${user.level}"

                val totalExp = user.experience
                val expForNextLevel = 1000 * (2.0.pow(user.level)).toInt()
                val expInCurrentLevel = totalExp % expForNextLevel

                binding.expLabel.text = "Exp: $expInCurrentLevel / $expForNextLevel"
                binding.expProgressBar.progress = calculateExpProgress(user.experience, user.level)
                profileViewModel.evaluationLiveData.observe(viewLifecycleOwner) { evaluation ->
                    evaluation?.let {
                        val strValue = calculateStrength(user, it)
                        binding.strLabel.text = "STR: $strValue"

                        profileViewModel.updateUserStats(user, it)
                    }
                }
            }
        }

        // Handle button click
        binding.reevaluateButton.setOnClickListener {
            showEvaluationDialog()
        }
    }

    private fun calculateExpProgress(exp: Int, level: Int): Int {
        val expForNextLevel = 1000 * (2.0.pow(level)).toInt() // Calculate required EXP
        val currentLevelExp = exp % expForNextLevel // Calculate progress within current level
        return (currentLevelExp / expForNextLevel.toFloat() * 100).toInt() // Return percentage progress
    }

    private fun updateUserLevelAndExperience(user: User) {
        var currentExp = user.experience
        var currentLevel = user.level
        var expForNextLevel = 1000 * (2.0.pow(currentLevel)).toInt()

        while (currentExp >= expForNextLevel) {
            currentExp -= expForNextLevel
            currentLevel++
            expForNextLevel = 1000 * (2.0.pow(currentLevel)).toInt()
        }

        val updatedUser = user.copy(level = currentLevel, experience = currentExp)
        profileViewModel.updateUser(updatedUser)
    }

    private fun calculateStrength(user: User, evaluation: Evaluation): Int {
        val benchRatio = evaluation.benchPr / user.weight
        val squatRatio = evaluation.squatPr / user.weight
        val deadliftRatio = evaluation.deadliftPr / user.weight

        val benchStr = (benchRatio / 3.0 * 33).coerceIn(0.0, 33.0)
        val squatStr = (squatRatio / 5.0 * 33).coerceIn(0.0, 33.0)
        val deadliftStr = (deadliftRatio / 6.0 * 33).coerceIn(0.0, 33.0)

        return (benchStr + squatStr + deadliftStr).toInt()
    }

    private fun showEvaluationDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_reevaluation, null)
        val benchPrInput = dialogView.findViewById<EditText>(R.id.benchPrInput)
        val squatPrInput = dialogView.findViewById<EditText>(R.id.squatPrInput)
        val deadliftPrInput = dialogView.findViewById<EditText>(R.id.deadliftPrInput)

        AlertDialog.Builder(requireContext())
            .setTitle("Re-Evaluate PRs")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val benchPr = benchPrInput.text.toString().toFloatOrNull() ?: 0f
                val squatPr = squatPrInput.text.toString().toFloatOrNull() ?: 0f
                val deadliftPr = deadliftPrInput.text.toString().toFloatOrNull() ?: 0f

                val evaluation = Evaluation(
                    benchPr = benchPr,
                    squatPr = squatPr,
                    deadliftPr = deadliftPr
                )
                profileViewModel.saveEvaluation(evaluation)

                Toast.makeText(requireContext(), "PRs updated successfully!", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
