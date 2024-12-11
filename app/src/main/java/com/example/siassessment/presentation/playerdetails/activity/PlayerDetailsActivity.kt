package com.example.siassessment.presentation.playerdetails.activity

import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.constraintlayout.widget.Guideline
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.siassessment.R
import com.example.siassessment.base.BaseActivity
import com.example.siassessment.data.response.PlayerDetails
import com.example.siassessment.data.response.Teams
import com.example.siassessment.databinding.ActivityPlayerDetailsBinding
import com.example.siassessment.databinding.CustomdialogBinding
import com.example.siassessment.presentation.matchdetails.viewmodel.MatchDetailsViewModel
import com.example.siassessment.presentation.playerdetails.FilterBottomSheet
import com.example.siassessment.presentation.playerdetails.adapter.Team1PlayerAdapter
import com.example.siassessment.presentation.playerdetails.adapter.Team2PlayerAdapter
import com.example.siassessment.presentation.states.ResourceState
import com.example.siassessment.utils.Constants.IND_NZ
import com.example.siassessment.utils.Constants.SA_PAK
import com.example.siassessment.utils.Constants.TEAM_NAME
import com.example.siassessment.utils.hide
import com.example.siassessment.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PlayerDetailsActivity : BaseActivity<ActivityPlayerDetailsBinding>() {

    private val viewModel: MatchDetailsViewModel by viewModels()
    private lateinit var team1PlayerAdapter: Team1PlayerAdapter
    private lateinit var team2PlayerAdapter: Team2PlayerAdapter
    private var team1Name = ""
    private var team2Name = ""

    override fun getViewBinding(): ActivityPlayerDetailsBinding {
        return ActivityPlayerDetailsBinding.inflate(layoutInflater)
    }

    override fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.teamIndNz.collectLatest {
                        setTeamDetailsData(it)
                    }
                }

                launch {
                    viewModel.teamSaPak.collectLatest {
                        setTeamDetailsData(it)
                    }
                }

                launch {
                    viewModel.indNzMatchDetails.collectLatest {
                        val response = it ?: return@collectLatest
                        when (response.status) {
                            ResourceState.LOADING -> {
                                customProgressBar.show()
                            }

                            ResourceState.SUCCESS -> {
                                customProgressBar.hide()
                            }

                            ResourceState.ERROR -> {
                                customProgressBar.hide()
                                Toast.makeText(
                                    this@PlayerDetailsActivity,
                                    "Failed to get Data: ${it.error?.msg}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }

                launch {
                    viewModel.saPakMatchDetails.collectLatest {
                        val response = it ?: return@collectLatest
                        when (response.status) {
                            ResourceState.LOADING -> {
                                customProgressBar.show()
                            }

                            ResourceState.SUCCESS -> {
                                customProgressBar.hide()
                            }

                            ResourceState.ERROR -> {
                                customProgressBar.hide()
                                Toast.makeText(
                                    this@PlayerDetailsActivity,
                                    "Failed to get Data: ${it.error?.msg}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setTeamDetailsData(it: List<Teams>?) {
        with(binding){

            tvTeam1Name.text = it?.get(0)?.nameFull
            tvTeam2Name.text = it?.get(1)?.nameFull

            team1Name = it?.get(0)?.nameFull ?: resources.getString(R.string.team_1)
            team2Name = it?.get(1)?.nameFull ?: resources.getString(R.string.team_2)

            it?.get(0)?.Players?.let { it1 -> team1PlayerAdapter.addToList(it1) }
            it?.get(1)?.Players?.let { it1 -> team2PlayerAdapter.addToList(it1) }
            rvTeam1PlayerDetails.adapter = team1PlayerAdapter
            rvTeam2PlayerDetails.adapter = team2PlayerAdapter
        }
    }


    override fun initialize() {
        if (intent != null) {
            val teamName = intent.getStringExtra(TEAM_NAME).toString()
            if (teamName == IND_NZ) {
                viewModel.getIndNzMatchDetails()
            } else if (teamName == SA_PAK){
                viewModel.getSaPakMatchDetails()
            }
        }
        team1PlayerAdapter = Team1PlayerAdapter(
            onClick = {
                showTeamPlayerStatsDialog(it)
            }
        )
        team2PlayerAdapter = Team2PlayerAdapter(
            onClick = {
                showTeamPlayerStatsDialog(it)
            }
        )
        binding.fabFilter.setOnClickListener {
            val bottomSheet = FilterBottomSheet(onDisplayAllClick = {
                binding.tvTeam1Name.show()
                binding.rvTeam1PlayerDetails.show()
                binding.tvTeam2Name.show()
                binding.rvTeam2PlayerDetails.show()
                updateGuidelinePercent(binding.gl50, 0.5f)
            },onTeam1Click = {
                binding.tvTeam1Name.show()
                binding.rvTeam1PlayerDetails.show()
                binding.tvTeam2Name.hide()
                binding.rvTeam2PlayerDetails.hide()
                updateGuidelinePercent(binding.gl50, 1.0f)
            },onTeam2Click = {
                binding.tvTeam1Name.hide()
                binding.rvTeam1PlayerDetails.hide()
                binding.tvTeam2Name.show()
                binding.rvTeam2PlayerDetails.show()
                updateGuidelinePercent(binding.gl50, 0.0f)
            })
            bottomSheet.setTeamNames(team1Name, team2Name)
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
    }

    private fun updateGuidelinePercent(guideline: Guideline, percent: Float) {
        // Get the parent ConstraintLayout
        val constraintLayout = guideline.parent as ConstraintLayout

        // Create a ConstraintSet
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout) // Clone the current layout

        // Update the Guide_percent value
        constraintSet.setGuidelinePercent(guideline.id, percent)

        // Apply the updated constraints
        constraintSet.applyTo(constraintLayout)
    }

    private fun showTeamPlayerStatsDialog(it: PlayerDetails) {
        val dialog = AlertDialog.Builder(this)
        val bindingStats = CustomdialogBinding.inflate(layoutInflater)
        dialog.setView(bindingStats.root)


        with(bindingStats) {
            if (it.bowling?.average.equals("0")) {
                groupBowling.hide()
            }

            if (it.batting?.runs.equals("0")) {
                groupBatting.show()
            }

            tvStyle.text = resources.getString(R.string.Style, it.batting?.style)
            tvAverage.text = resources.getString(R.string.Average, it.batting?.average)
            tvStrikeRate.text =
                resources.getString(R.string.StrikeRate, it.batting?.strikeRate)
            tvRuns.text = resources.getString(R.string.battingRuns, it.batting?.runs)

            tvBowlingStyle.text = resources.getString(R.string.Style, it.bowling?.style)
            tvBowlingAverage.text =
                resources.getString(R.string.Average, it.bowling?.average)
            tvEconomyRate.text =
                resources.getString(R.string.economyRate, it.bowling?.economyRate)
            tvWickets.text = resources.getString(R.string.wickets, it.bowling?.wickets)

        }
        dialog.setCancelable(true)
        // Create and show the dialog
        val dialogStats = dialog.create()
        dialogStats.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialogStats.show()
    }
}