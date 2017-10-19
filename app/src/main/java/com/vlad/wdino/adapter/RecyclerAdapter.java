package com.vlad.wdino.adapter;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vlad.wdino.R;
import com.vlad.wdino.WDinoApp;
import com.vlad.wdino.model.Dino;
import com.vlad.wdino.model.DinoImage;
import com.vlad.wdino.utils.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView mNameTextView;
        TextView mColorTextView;
        TextView mBirthTextView;
        TextView mAboutTextView;
        ImageView mDinoImage;

        ViewHolder(View v) {
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
        String birthDate = Constants.DINO_BIRTH_DATE + "<i>" + reformattedStr + "</i>";
        holder.mBirthTextView.setText(Html.fromHtml(birthDate));

        String color = Constants.DINO_COLOR + "<i>" + mDataset.get(position).getDino().getDinoColor() + "</i>";
        holder.mColorTextView.setText(Html.fromHtml(color));

        String about = mDataset.get(position).getDino().getDinoAbout();
        holder.mAboutTextView.setText(about);

        final String url = mDataset.get(position).getDino().getDinoImage().getSrc();
        Picasso.with(WDinoApp.getCurrentActivity().getApplicationContext())
                .load(url)
                .error(R.drawable.internet_not_found_img)
                .into(holder.mDinoImage);
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
