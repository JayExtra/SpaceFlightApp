package com.dev.james.launchlibraryapi.features.adapters

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.dev.james.launchlibraryapi.R
import com.dev.james.launchlibraryapi.databinding.LaunchCardBinding
import com.dev.james.launchlibraryapi.models.LaunchList
import java.util.*
import java.util.concurrent.TimeUnit

class LaunchListAdapter(
    private val action : (String? , Boolean) -> Unit,
) : PagingDataAdapter<LaunchList , LaunchListAdapter.LaunchListViewHolder>(DiffCallback()) {

    private val TAG = "LaunchListAdapter"
    private lateinit var  context : Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchListViewHolder {
        val binding = LaunchCardBinding.inflate(LayoutInflater.from(parent.context),
            parent , false)
        return LaunchListViewHolder(binding)
    }
    override fun onBindViewHolder(holder: LaunchListViewHolder, position: Int) {
        val currentItem = getItem(position)
        context = holder.itemView.context
        if(holder.countDownTimer != null){
            holder.countDownTimer!!.cancel()
        }

        if(currentItem != null){
            holder.binding(currentItem)
        }
    }


    inner class LaunchListViewHolder(private val binding : LaunchCardBinding) :
            RecyclerView.ViewHolder(binding.root){

            private var agency = ""
            var countDownTimer: CountDownTimer? = null

        fun binding( launch : LaunchList){
                    binding.apply {

                        nameTv.text = launch.name
                        agency = "${launch.serviceProvider?.name} "+ " | " + "${launch.serviceProvider?.type}"
                        lsProviderTv.text = agency
                        launchPadTv.text = launch.pad.location.name
                        dateText.text = launch.createdDateFormatted
                        setStatus(launch , binding)
                        loadImage(launch , binding)
                        setTimer(launch.launchDate , binding)

                        statusTv.setOnClickListener {
                            action.invoke(launch.status?.name , false)
                        }

                        root.setOnClickListener {
                            action.invoke(null , true)
                        }

                    }


                }

        private fun setTimer(launchDate: Date, binding: LaunchCardBinding) {
           val futureTimeMill = launchDate.time
            val c_date = Calendar.getInstance().timeInMillis
            var timeDiff = futureTimeMill - c_date

            countDownTimer = object : CountDownTimer(timeDiff , 1000) {
                override fun onTick(millscUntilFinish: Long) {
                    binding.apply {


                        countDownTv.text = context.getString(R.string.updated_timer,
                            TimeUnit.MILLISECONDS.toDays(millscUntilFinish) , TimeUnit.MILLISECONDS.toHours(millscUntilFinish) % 24  ,
                            TimeUnit.MILLISECONDS.toMinutes(millscUntilFinish) % 60  , TimeUnit.MILLISECONDS.toSeconds(millscUntilFinish) %60
                        )

                    }
                }

                override fun onFinish() {
                    Log.d(TAG, "onFinish: timer has finished its work")
                    binding.apply {
                        countDownTv.setTextColor(Color.GREEN)
                        countDownTv.text = context.getText(R.string.vehicle_liftoff)
                        daysHrsTxt.isVisible = false
                    }
                }
            }

            (countDownTimer as CountDownTimer).start()


        }

        private fun setStatus(launch: LaunchList, binding: LaunchCardBinding) {
            binding.apply {
                statusTv.text = launch.status?.abbrev

                if(launch.status?.id == 2 ){
                    statusTv.setTextColor(Color.BLUE)
                    countDownTv.setTextColor(Color.WHITE)
                    countDownTv.isVisible = true
                    date2Txt.isVisible = false
                    descrptTxt.isVisible = false
                    daysHrsTxt.isVisible = true
                    dateText.isVisible = true
                    remindTxt.isVisible = true
                    remindChip.isVisible = true
                }
                if(launch.status?.id == 3 || launch.status?.id == 4  ){

                    statusTv.setTextColor(Color.GREEN)
                    countDownTv.isVisible = false
                    daysHrsTxt.isVisible = false
                    dateText.isVisible = false
                    remindTxt.isVisible = false
                    remindChip.isVisible = false
                    date2Txt.isVisible = true
                    descrptTxt.isVisible = true
                    descrptTxt.text = launch.mission?.description
                    date2Txt.text = launch.createdDateFormatted
                }
                if(launch.status?.id == 4){
                    statusTv.setTextColor(Color.RED)
                    countDownTv.isVisible = false
                    daysHrsTxt.isVisible = false
                    dateText.isVisible = false
                    remindTxt.isVisible = false
                    remindChip.isVisible = false
                    date2Txt.isVisible = true
                    descrptTxt.isVisible = true
                    descrptTxt.text = launch.mission?.description
                    date2Txt.text = launch.createdDateFormatted
                }
                if(launch.status?.id == 8 ){
                    statusTv.setTextColor(Color.YELLOW)
                    countDownTv.setTextColor(Color.WHITE)
                    countDownTv.isVisible = true
                    date2Txt.isVisible = false
                    descrptTxt.isVisible = false
                    daysHrsTxt.isVisible = true
                    dateText.isVisible = true
                    remindTxt.isVisible = true
                    remindChip.isVisible = true
                }

                if(launch.status?.id == 1){
                    statusTv.setTextColor(Color.CYAN)
                    //countDownTv.text = context.getText(R.string.vehicle_liftoff)
                    countDownTv.setTextColor(Color.WHITE)
                    countDownTv.isVisible = true
                    date2Txt.isVisible = false
                    descrptTxt.isVisible = false
                    daysHrsTxt.isVisible = true
                    dateText.isVisible = true
                    remindTxt.isVisible = true
                    remindChip.isVisible = true
                }
            }
        }

        private fun loadImage(launch: LaunchList, binding: LaunchCardBinding) {
            binding.apply {
                Glide.with(binding.root)
                    .load(launch.image)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            loadImageProgress.isInvisible = true
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            loadImageProgress.isVisible = false
                            return false
                        }

                    })
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .error(R.drawable.ic_baseline_error_24)
                    .centerCrop()
                    .into(launchImage)
            }
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<LaunchList>(){
        override fun areItemsTheSame(oldItem: LaunchList, newItem: LaunchList): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: LaunchList, newItem: LaunchList): Boolean =
            oldItem == newItem
    }


}