package com.example.openweathermapcase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.openweathermapcase.R;
import com.example.openweathermapcase.model.weatherForecast.Daily;
import com.example.openweathermapcase.model.weatherForecast.WeatherForecast;
import com.example.openweathermapcase.util.CommonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    List<Daily> itemList;
    Context context;

    public WeatherAdapter(Context context, List<Daily> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView
                = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_weather_daily,
                        parent,
                        false);


        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


        holder.tvDate.setText(CommonUtils.getDateFromTimestamp(itemList.get(position).getDt()));
        holder.tvTemp.setText(itemList.get(position).getTemp().getDay() + " °C");
        holder.tvWeatherStatus.setText(itemList.get(position).getWeather().get(0).getMain());


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.tvDate)
        TextView tvDate;
        @BindView(R.id.tvTemp)
        TextView tvTemp;
        @BindView(R.id.tvWeatherStatus)
        TextView tvWeatherStatus;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
