package com.example.myapplication.presentation.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemCityBinding

class CityAdapter(
    private val itemClick: (Int) -> Unit
) : ListAdapter<City, CityAdapter.CityViewHolder>
    (object : DiffUtil.ItemCallback<City>() {
    override fun areItemsTheSame(oldItem: City, newItem: City): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: City, newItem: City): Boolean = oldItem == newItem
}){
    inner class CityViewHolder(private val binding: ItemCityBinding, private val itemClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(city: City) {
            with(binding) {
                root.setOnClickListener {
                    itemClick.invoke(city.id)
                }
                root.setBackgroundColor(ContextCompat.getColor(binding.root.context, R.color.white_warm))
                tvName.text = city.name
                tvTemperature.text = city.temperature.toInt().toString()
                tvTemperature.setTextColor(ContextCompat.getColor(binding.root.context, getColorByTemp(city.temperature)))
            }
        }

        private fun getColorByTemp(temperature: Double): Int{
            var color : Int = R.color.black
            if(temperature <-25) color = R.color.blue
            if(temperature >= -25 && temperature < -5)color = R.color.light_blue
            if(temperature >= -5 && temperature < 10)color = R.color.green
            if(temperature >= 10 && temperature < 25)color = R.color.yellow
            if(temperature >= 25 && temperature < 40)color = R.color.orange
            if(temperature >=40)color = R.color.red
            return color
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder =
        CityViewHolder(
            ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false), itemClick
        )

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) =
        holder.bind(getItem(position))

    override fun submitList(list: MutableList<City>?) {
        super.submitList(list?.let { ArrayList(it) })
    }
}