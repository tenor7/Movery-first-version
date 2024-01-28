package com.example.mediatec.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.mediatec.Models.BannerMovies;
import com.example.mediatec.MovieDetails;
import com.example.mediatec.MovieDetailsHorizontal;
import com.example.mediatec.R;

import java.util.List;


public class BannerMoviesPagerAdapter extends PagerAdapter {

    Context context;
    List<BannerMovies> bannerMoviesList;

    public BannerMoviesPagerAdapter(Context context, List<BannerMovies> bannerMoviesList) {
        this.context = context;
        this.bannerMoviesList = bannerMoviesList;
    }

    @Override
    public int getCount() {
        return bannerMoviesList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.banner_movie_layout, null);
        ImageView bannerImage = view.findViewById(R.id.banner_image);
        Glide.with(context).load(bannerMoviesList.get(position).getImageUrl()).into(bannerImage);
        container.addView(view);

        bannerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, MovieDetailsHorizontal.class);
                i.putExtra("movieId", bannerMoviesList.get(position).getId());
                i.putExtra("movieName", bannerMoviesList.get(position).getMovieName());
                i.putExtra("movieImageUrl", bannerMoviesList.get(position).getImageUrl());
                i.putExtra("movieFile", bannerMoviesList.get(position).getFileUrl());
                context.startActivity(i);
            }
        });

        return view;
    }
}