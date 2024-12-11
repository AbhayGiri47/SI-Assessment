package com.example.siassessment.presentation.playerdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.siassessment.R
import com.example.siassessment.databinding.LayoutFilterBottomsheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterBottomSheet(val onTeam1Click: () -> Unit, val onTeam2Click: () -> Unit, val onDisplayAllClick: () -> Unit) : BottomSheetDialogFragment() {
    private lateinit var binding: LayoutFilterBottomsheetBinding
    private var team1Name = ""
    private var team2Name = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LayoutFilterBottomsheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvTeam1.text = team1Name
        binding.tvTeam2.text = team2Name
        setupClickListeners()
    }

    private fun setupClickListeners() {
        with(binding){
            tvDisplayAll.setOnClickListener {
                onDisplayAllClick()
                dismiss()
            }
            tvTeam1.setOnClickListener {
                onTeam1Click()
                dismiss()
            }
            tvTeam2.setOnClickListener {
                onTeam2Click()
                dismiss()
            }
        }
    }

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog
    }

    fun setTeamNames(team1: String, team2: String) {
        team1Name = team1
        team2Name = team2
    }
}