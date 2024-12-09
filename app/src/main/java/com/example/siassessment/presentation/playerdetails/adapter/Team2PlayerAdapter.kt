package com.example.siassessment.presentation.playerdetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.siassessment.R
import com.example.siassessment.data.response.PlayerDetails
import com.example.siassessment.databinding.ItemPlayerDetailsBinding

class Team2PlayerAdapter(val onClick: (PlayerDetails) -> Unit) :
    RecyclerView.Adapter<Team2PlayerAdapter.ViewHolder>() {

    var list = ArrayList<PlayerDetails>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPlayerDetailsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (list.get(position).isCaptain == true && list.get(position).isKeeper == true) {
            holder.playerName.text = holder.itemView.context.resources.getString(
                R.string.cwk, list.get(position).nameFull
            )
        } else if (list.get(position).isKeeper == true) {
            holder.playerName.text = holder.itemView.context.resources.getString(
                R.string.wicketkeeper, list.get(position).nameFull
            )
        } else if (list.get(position).isCaptain == true) {
            holder.playerName.text = holder.itemView.context.resources.getString(
                R.string.captain, list.get(position).nameFull
            )
        } else {
            holder.playerName.text = list.get(position).nameFull
        }
        holder.playerName.setOnClickListener {
            onClick.invoke(list.get(position))
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(binding: ItemPlayerDetailsBinding) : RecyclerView.ViewHolder(binding.root) {
        val playerName = binding.tvPlayerName

    }

    fun addToList(playerList: List<PlayerDetails>) {
        list.clear()
        list.addAll(playerList)
        notifyDataSetChanged()
    }
}