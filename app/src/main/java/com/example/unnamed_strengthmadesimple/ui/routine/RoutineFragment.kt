package com.example.unnamed_strengthmadesimple.ui.routine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.unnamed_strengthmadesimple.R
import com.example.unnamed_strengthmadesimple.databinding.FragmentRoutineBinding

class RoutineFragment : Fragment() {

    private var _binding: FragmentRoutineBinding? = null
    private val binding get() = _binding!!

    private lateinit var routineViewModel: RoutineViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoutineBinding.inflate(inflater, container, false)
        routineViewModel = ViewModelProvider(this).get(RoutineViewModel::class.java)

        setupCheckBoxListeners()

        binding.checkProgressButton.setOnClickListener {
            checkProgressAndUpdateExperience()
        }

        binding.chooseRoutineButton.setOnClickListener {
            Toast.makeText(requireContext(), "This has yet to be implemented", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    private fun setupCheckBoxListeners() {
        // Handle checkbox interactions for "Wake Up"
        binding.root.findViewById<CheckBox>(R.id.checkWakeUp).setOnCheckedChangeListener { _, isChecked ->
            val textView = binding.root.findViewById<TextView>(R.id.textWakeUp)
            handleCheckEffect(textView, isChecked)
        }
        // Handle checkbox interactions for "Gym"
        binding.root.findViewById<CheckBox>(R.id.checkGym).setOnCheckedChangeListener { _, isChecked ->
            val textView = binding.root.findViewById<TextView>(R.id.textGym)
            handleCheckEffect(textView, isChecked)
        }
        // Handle checkbox interactions for "Gym"
        binding.root.findViewById<CheckBox>(R.id.checkBreakfast).setOnCheckedChangeListener { _, isChecked ->
            val textView = binding.root.findViewById<TextView>(R.id.textBreakfast)
            handleCheckEffect(textView, isChecked)
        }
        // Handle checkbox interactions for "Gym"
        binding.root.findViewById<CheckBox>(R.id.checkWork).setOnCheckedChangeListener { _, isChecked ->
            val textView = binding.root.findViewById<TextView>(R.id.textWork)
            handleCheckEffect(textView, isChecked)
        }
        // Handle checkbox interactions for "Gym"
        binding.root.findViewById<CheckBox>(R.id.checkLunch).setOnCheckedChangeListener { _, isChecked ->
            val textView = binding.root.findViewById<TextView>(R.id.textLunch)
            handleCheckEffect(textView, isChecked)
        }
        // Handle checkbox interactions for "Gym"
        binding.root.findViewById<CheckBox>(R.id.checkSwim).setOnCheckedChangeListener { _, isChecked ->
            val textView = binding.root.findViewById<TextView>(R.id.textSwim)
            handleCheckEffect(textView, isChecked)
        }
        // Handle checkbox interactions for "Gym"
        binding.root.findViewById<CheckBox>(R.id.checkDinner).setOnCheckedChangeListener { _, isChecked ->
            val textView = binding.root.findViewById<TextView>(R.id.textDinner)
            handleCheckEffect(textView, isChecked)
        }
        // Handle checkbox interactions for "Gym"
        binding.root.findViewById<CheckBox>(R.id.checkFree).setOnCheckedChangeListener { _, isChecked ->
            val textView = binding.root.findViewById<TextView>(R.id.textFree)
            handleCheckEffect(textView, isChecked)
        }
        // Handle checkbox interactions for "Gym"
        binding.root.findViewById<CheckBox>(R.id.checkSleep).setOnCheckedChangeListener { _, isChecked ->
            val textView = binding.root.findViewById<TextView>(R.id.textSleep)
            handleCheckEffect(textView, isChecked)
        }
    }

    // Apply fade-out effect and text color change
    private fun handleCheckEffect(textView: TextView, isChecked: Boolean) {
        if (isChecked) {
            textView.setTextColor(requireContext().getColor(android.R.color.darker_gray)) // Light gray
            textView.alpha = 0.5f // Faded appearance
        } else {
            textView.setTextColor(requireContext().getColor(android.R.color.black)) // Black
            textView.alpha = 1.0f // Fully visible
        }
    }

    private fun checkProgressAndUpdateExperience() {
        var checkedCount = 0

        // Check each routine item's checkbox and count how many are checked
        val checkBoxes = listOf(
            binding.root.findViewById<CheckBox>(R.id.checkWakeUp),
            binding.root.findViewById<CheckBox>(R.id.checkGym),
            binding.root.findViewById<CheckBox>(R.id.checkBreakfast),
            binding.root.findViewById<CheckBox>(R.id.checkWork),
            binding.root.findViewById<CheckBox>(R.id.checkLunch),
            binding.root.findViewById<CheckBox>(R.id.checkSwim),
            binding.root.findViewById<CheckBox>(R.id.checkDinner),
            binding.root.findViewById<CheckBox>(R.id.checkFree),
            binding.root.findViewById<CheckBox>(R.id.checkSleep),
        )

        for (checkBox in checkBoxes) {
            if (checkBox.isChecked) {
                checkedCount++
                checkBox.isChecked = false // Reset the checkbox
            }
        }

        // Calculate experience points
        val expGained = checkedCount * 100

        // Update the user's experience in the database
        routineViewModel.updateUserExperience(expGained) { success ->
            if (success) {
                // Show a Toast message when the update is successful
                Toast.makeText(
                    requireContext(),
                    "You gained $expGained experience!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Failed to update experience. Please try again!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
