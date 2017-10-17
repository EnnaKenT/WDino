package com.vlad.wdino.adapter;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vlad.wdino.R;
import com.vlad.wdino.model.Dino;
import com.vlad.wdino.model.DinoImage;
import com.vlad.wdino.model.Dino_;
import com.vlad.wdino.utils.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<Dino> mDataset;

    public RecyclerAdapter(List<Dino> dataset) {
        mDataset = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item, parent, false);

        return new ViewHolder(v);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mNameTextView;
        public TextView mColorTextView;
        public TextView mBirthTextView;
        public TextView mAboutTextView;
        public ImageView mDinoImage;

        public ViewHolder(View v) {
            super(v);
            mNameTextView = v.findViewById(R.id.dino_name);
            mColorTextView = v.findViewById(R.id.dino_color);
            mBirthTextView = v.findViewById(R.id.dino_birth_date);
            mAboutTextView = v.findViewById(R.id.dino_about);
            mDinoImage = v.findViewById(R.id.dino_pic);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mNameTextView.setText(mDataset.get(position).getDino().getDinoTitle());


        String date = mDataset.get(position).getDino().getDinoBirthdate();
        SimpleDateFormat fromUser = new SimpleDateFormat("dd.MM.yyyy", Locale.US);
        SimpleDateFormat myFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
        String reformattedStr = null;
        try {
            reformattedStr = myFormat.format(fromUser.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String birthDate = Constants.DINO_BIRTH_DATE + reformattedStr;
        holder.mBirthTextView.setText(Html.fromHtml(birthDate));

        String color = Constants.DINO_COLOR + mDataset.get(position).getDino().getDinoColor();
        holder.mColorTextView.setText(Html.fromHtml(color));

        holder.mAboutTextView.setText(mDataset.get(position).getDino().getDinoAbout());

        Bitmap dinoImage = mDataset.get(position).getDino().getDinoImage().getImage();
        holder.mDinoImage.setImageBitmap(dinoImage);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
