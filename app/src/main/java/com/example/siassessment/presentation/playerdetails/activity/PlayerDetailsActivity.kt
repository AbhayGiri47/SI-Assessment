package com.example.siassessment.presentation.playerdetails.activity

import android.app.Dialog
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.siassessment.R
import com.example.siassessment.base.BaseActivity
import com.example.siassessment.databinding.ActivityPlayerDetailsBinding
import com.example.siassessment.presentation.playerdetails.adapter.Team1PlayerAdapter
import com.example.siassessment.presentation.playerdetails.adapter.Team2PlayerAdapter
import com.example.siassessment.presentation.playerdetails.viewmodel.PlayerDetailsViewModel
import com.example.siassessment.utils.Constants.TEAM_NAME
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlayerDetailsActivity : BaseActivity<ActivityPlayerDetailsBinding>() {

    private val viewModel: PlayerDetailsViewModel by viewModels()
    private var teamName = ""
    lateinit var team1PlayerAdapter: Team1PlayerAdapter
    lateinit var team2PlayerAdapter: Team2PlayerAdapter

    override fun getViewBinding(): ActivityPlayerDetailsBinding {
        return ActivityPlayerDetailsBinding.inflate(layoutInflater)
    }

    override fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.team1.observe(this@PlayerDetailsActivity) {
                        binding.tvTeam1Name.text = it[0].nameFull
                        binding.tvTeam2Name.text = it[1].nameFull

                        it[0].Players?.let { it1 -> team1PlayerAdapter.addToList(it1) }
                        it[1].Players?.let { it1 -> team2PlayerAdapter.addToList(it1) }
                        binding.rvTeam1PlayerDetails.adapter = team1PlayerAdapter
                        binding.rvTeam2PlayerDetails.adapter = team2PlayerAdapter

                    }
                }

                launch {
                    viewModel.team2.observe(this@PlayerDetailsActivity) {
                        binding.tvTeam1Name.text = it[0].nameFull
                        binding.tvTeam2Name.text = it[1].nameFull

                        it[0].Players?.let { it1 -> team1PlayerAdapter.addToList(it1) }
                        it[1].Players?.let { it1 -> team2PlayerAdapter.addToList(it1) }

                        binding.rvTeam1PlayerDetails.adapter = team1PlayerAdapter
                        binding.rvTeam2PlayerDetails.adapter = team2PlayerAdapter
                    }
                }
            }
        }
    }



    override fun initialize() {
        if (intent != null) {
            teamName = intent.getStringExtra(TEAM_NAME).toString()
            if (teamName=="INDNZ"){
                viewModel.getIndNzMatchDetails()
            }else{
                viewModel.getSaPakMatchDetails()
            }
        }
        team1PlayerAdapter = Team1PlayerAdapter(
            onClick = {
                val dialog = Dialog(this)
                dialog.setContentView(R.layout.customdialog)

                if (it.bowling?.average.equals("0")) {
                    val clBowling = dialog.findViewById<ConstraintLayout>(R.id.clBowlingDetails)
                    clBowling.visibility = View.GONE
                }

                if (it.batting?.runs.equals("0")) {
                    val clBatting = dialog.findViewById<ConstraintLayout>(R.id.clBattingDetails)
                    clBatting.visibility = View.GONE
                }

                val battingStyle = dialog.findViewById<TextView>(R.id.tvStyle)
                battingStyle.text = resources.getString(R.string.Style, it.batting?.style)

                val battingAverage = dialog.findViewById<TextView>(R.id.tvAverage)
                battingAverage.text = resources.getString(R.string.Average, it.batting?.average)

                val battingStrikeRate = dialog.findViewById<TextView>(R.id.tvStrikeRate)
                battingStrikeRate.text =
                    resources.getString(R.string.StrikeRate, it.batting?.strikeRate)

                val battingRuns = dialog.findViewById<TextView>(R.id.tvRuns)
                battingRuns.text = resources.getString(R.string.battingRuns, it.batting?.runs)

                val bowlingStyle = dialog.findViewById<TextView>(R.id.tvBowlingStyle)
                bowlingStyle.text = resources.getString(R.string.Style, it.bowling?.style)

                val bowlingAverage = dialog.findViewById<TextView>(R.id.tvBowlingAverage)
                bowlingAverage.text = resources.getString(R.string.Average, it.bowling?.average)

                val economyRate = dialog.findViewById<TextView>(R.id.tvEconomyRate)
                economyRate.text = resources.getString(R.string.economyRate, it.bowling?.economyRate)

                val wickets = dialog.findViewById<TextView>(R.id.tvWickets)
                wickets.text = resources.getString(R.string.wickets, it.bowling?.wickets)

                dialog.show()
            }
        )
        team2PlayerAdapter = Team2PlayerAdapter(
            onClick = {
                val dialog = Dialog(this)
                dialog.setContentView(R.layout.customdialog)

                if (it.bowling?.average.equals("0")) {
                    val clBowling = dialog.findViewById<ConstraintLayout>(R.id.clBowlingDetails)
                    clBowling.visibility = View.GONE
                }

                if (it.batting?.runs.equals("0")) {
                    val clBatting = dialog.findViewById<ConstraintLayout>(R.id.clBattingDetails)
                    clBatting.visibility = View.GONE
                }

                val battingStyle = dialog.findViewById<TextView>(R.id.tvStyle)
                battingStyle.text = resources.getString(R.string.Style, it.batting?.style)

                val battingAverage = dialog.findViewById<TextView>(R.id.tvAverage)
                battingAverage.text = resources.getString(R.string.Average, it.batting?.average)

                val battingStrikeRate = dialog.findViewById<TextView>(R.id.tvStrikeRate)
                battingStrikeRate.text =
                    resources.getString(R.string.StrikeRate, it.batting?.strikeRate)

                val battingRuns = dialog.findViewById<TextView>(R.id.tvRuns)
                battingRuns.text = resources.getString(R.string.battingRuns, it.batting?.runs)

                val bowlingStyle = dialog.findViewById<TextView>(R.id.tvBowlingStyle)
                bowlingStyle.text = resources.getString(R.string.Style, it.bowling?.style)

                val bowlingAverage = dialog.findViewById<TextView>(R.id.tvBowlingAverage)
                bowlingAverage.text = resources.getString(R.string.Average, it.bowling?.average)

                val economyRate = dialog.findViewById<TextView>(R.id.tvEconomyRate)
                economyRate.text = resources.getString(R.string.economyRate, it.bowling?.economyRate)

                val wickets = dialog.findViewById<TextView>(R.id.tvWickets)
                wickets.text = resources.getString(R.string.wickets, it.bowling?.wickets)

                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show()
            }
        )
    }
}