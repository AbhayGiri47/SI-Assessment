package com.example.siassessment.presentation.matchdetails.activity

import android.content.Intent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.siassessment.base.BaseActivity
import com.example.siassessment.data.response.INDNZMatchDetailsResponse
import com.example.siassessment.data.response.SAPAKMatchDetailsResponse
import com.example.siassessment.databinding.ActivityMatchDetailsBinding
import com.example.siassessment.presentation.matchdetails.viewmodel.MatchDetailsViewModel
import com.example.siassessment.presentation.playerdetails.activity.PlayerDetailsActivity
import com.example.siassessment.utils.Constants.TEAM_NAME
import com.example.siassessment.presentation.states.ResourceState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MatchDetailsActivity : BaseActivity<ActivityMatchDetailsBinding>(){


    private val matchDetailsViewModel: MatchDetailsViewModel by viewModels()

    override fun getViewBinding(): ActivityMatchDetailsBinding {
        return ActivityMatchDetailsBinding.inflate(layoutInflater)
    }

    override fun observeViewModel() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    matchDetailsViewModel.indNzMatchDetails.collectLatest {
                        val response = it ?: return@collectLatest
                        when (response.status) {
                            ResourceState.LOADING -> {
                                customProgressBar.show()
                            }
                            ResourceState.SUCCESS -> {
                                customProgressBar.hide()
                                val data = it.data
                                setupUI(data)
                            }

                            ResourceState.ERROR -> {
                                customProgressBar.hide()
                                Toast.makeText(this@MatchDetailsActivity, "Failed to get Data: ${it.error?.msg}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }

                launch {
                    matchDetailsViewModel.saPakMatchDetails.collectLatest {
                        val response = it ?: return@collectLatest
                        when (response.status) {
                            ResourceState.LOADING -> {
                                customProgressBar.show()
                            }
                            ResourceState.SUCCESS -> {
                                customProgressBar.hide()
                                val data = it.data
                                setupUI(data)
                            }

                            ResourceState.ERROR -> {
                                customProgressBar.hide()
                                Toast.makeText(this@MatchDetailsActivity, "Failed to get Data: ${it.error?.msg}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupUI(data: INDNZMatchDetailsResponse?) {

        with(binding){
            tvDateIndNz.text = data?.Matchdetail?.Match?.Date
            tvSeriesNameIndNz.text = data?.Matchdetail?.Series?.TourName
            tvTimeIndNz.text = data?.Matchdetail?.Match?.Time
            tvVenueIndNz.text = data?.Matchdetail?.Venue?.Name
            tvTeamIndia.text = data?.Teams?.team1?.nameFull
            tvTeamNz.text = data?.Teams?.team2?.nameFull

        }
    }

    private fun setupUI(data: SAPAKMatchDetailsResponse?) {

        with(binding){
            tvDateSaPak.text = data?.Matchdetail?.Match?.Date
            tvSeriesNameSaPak.text = data?.Matchdetail?.Series?.TourName
            tvTimeSaPak.text = data?.Matchdetail?.Match?.Time
            tvVenueSaPak.text = data?.Matchdetail?.Venue?.Name
            tvTeamSa.text = data?.Teams?.team1?.nameFull
            tvTeamPak.text = data?.Teams?.team2?.nameFull
        }
    }

    override fun initialize() {

        binding.tvViewMore.setOnClickListener {
            val intent = Intent(this, PlayerDetailsActivity::class.java)
            intent.putExtra(TEAM_NAME, "INDNZ")
            startActivity(intent)
        }

        binding.tvViewMoreSaPak.setOnClickListener {
            val intent = Intent(this, PlayerDetailsActivity::class.java)
            intent.putExtra(TEAM_NAME, "SAPAK")
            startActivity(intent)
        }


    }


}