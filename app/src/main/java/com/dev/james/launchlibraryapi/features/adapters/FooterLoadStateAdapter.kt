package com.dev.james.launchlibraryapi.features.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dev.james.launchlibraryapi.databinding.LoadStateFooterBinding

class FooterLoadStateAdapter(private val retry : () -> Unit) :
LoadStateAdapter<FooterLoadStateAdapter.FooterViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): FooterViewHolder {
        return  FooterViewHolder(
            LoadStateFooterBinding.inflate(
                LayoutInflater.from(parent.context) ,
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: FooterViewHolder, loadState: LoadState) {
        val progressBar = holder.binding.progressBarFooter
        val textErrorMessage = holder.binding.errorFooter
        val retryButton = holder.binding.buttonFooter

        progressBar.isVisible = loadState is LoadState.Loading
        textErrorMessage.isVisible = loadState is LoadState.Error
        retryButton.isVisible = loadState is LoadState.Error

        if(loadState is LoadState.Error){
            textErrorMessage.text = loadState.error.localizedMessage
        }
        retryButton.setOnClickListener {
            retry.invoke()
        }
    }

    inner class FooterViewHolder(val binding : LoadStateFooterBinding) :
            RecyclerView.ViewHolder(binding.root)


}