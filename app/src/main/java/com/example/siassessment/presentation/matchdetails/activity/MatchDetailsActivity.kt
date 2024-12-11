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
import com.example.siassessment.data.response.Teams
import com.example.siassessment.databinding.ActivityMatchDetailsBinding
import com.example.siassessment.presentation.matchdetails.viewmodel.MatchDetailsViewModel
import com.example.siassessment.presentation.playerdetails.activity.PlayerDetailsActivity
import com.example.siassessment.presentation.states.ResourceState
import com.example.siassessment.utils.Constants.IND_NZ
import com.example.siassessment.utils.Constants.SA_PAK
import com.example.siassessment.utils.Constants.TEAM_NAME
import com.example.siassessment.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MatchDetailsActivity : BaseActivity<ActivityMatchDetailsBinding>() {

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
                                binding.cvIndNz.show()
                            }

                            ResourceState.ERROR -> {
                                customProgressBar.hide()
                                Toast.makeText(
                                    this@MatchDetailsActivity,
                                    "Failed to get Data: ${it.error?.msg}",
                                    Toast.LENGTH_SHORT
                                ).show()
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
                                binding.cvSaPak.show()
                            }

                            ResourceState.ERROR -> {
                                customProgressBar.hide()
                                Toast.makeText(
                                    this@MatchDetailsActivity,
                                    "Failed to get Data: ${it.error?.msg}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }

                launch {
                    matchDetailsViewModel.teamIndNz.collectLatest {
                        setTeamIndNzDetailsData(it)
                    }
                }

                launch {
                    matchDetailsViewModel.teamSaPak.collectLatest {
                        setTeamSaPakDetailsData(it)
                    }
                }
            }
        }
    }

    private fun setTeamSaPakDetailsData(it: List<Teams>?) {
        binding.tvTeamSa.text = it?.get(0)?.nameFull
        binding.tvTeamPak.text = it?.get(1)?.nameFull
    }

    private fun setTeamIndNzDetailsData(it: List<Teams>?) {
        binding.tvTeamIndia.text = it?.get(0)?.nameFull
        binding.tvTeamNz.text = it?.get(1)?.nameFull
    }

    private fun setupUI(data: INDNZMatchDetailsResponse?) {

        with(binding) {
            tvDateIndNz.text = data?.matchDetail?.Match?.Date
            tvSeriesNameIndNz.text = data?.matchDetail?.Series?.TourName
            tvTimeIndNz.text = data?.matchDetail?.Match?.Time
            tvVenueIndNz.text = data?.matchDetail?.Venue?.Name

        }
    }

    private fun setupUI(data: SAPAKMatchDetailsResponse?) {

        with(binding) {
            tvDateSaPak.text = data?.Matchdetail?.Match?.Date
            tvSeriesNameSaPak.text = data?.Matchdetail?.Series?.TourName
            tvTimeSaPak.text = data?.Matchdetail?.Match?.Time
            tvVenueSaPak.text = data?.Matchdetail?.Venue?.Name
        }
    }

    override fun initialize() {

        matchDetailsViewModel.getIndNzMatchDetails()
        matchDetailsViewModel.getSaPakMatchDetails()

        binding.cvIndNz.setOnClickListener {
            val intent = Intent(this, PlayerDetailsActivity::class.java)
            intent.putExtra(TEAM_NAME, IND_NZ)
            startActivity(intent)
        }

        binding.cvSaPak.setOnClickListener {
            val intent = Intent(this, PlayerDetailsActivity::class.java)
            intent.putExtra(TEAM_NAME, SA_PAK)
            startActivity(intent)
        }
    }
}