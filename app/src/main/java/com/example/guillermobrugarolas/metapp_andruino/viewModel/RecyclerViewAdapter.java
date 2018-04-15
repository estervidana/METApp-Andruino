package com.example.guillermobrugarolas.metapp_andruino.viewModel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guillermobrugarolas.metapp_andruino.R;
import com.example.guillermobrugarolas.metapp_andruino.debug.Debug;

public class RecyclerViewAdapter extends Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final int MAX_ROWS = 5;
    private static final int MAX_COLUMNS = 5;
    private ImageView[] mData = new ImageView[MAX_ROWS*MAX_COLUMNS];
    private LayoutInflater mInflater;

    // data is passed into the constructor
    public RecyclerViewAdapter(Context context, ImageView[] data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the cell layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.lab_grid_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the ImageView in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ImageView cell = mData[position];
        holder.myImageView.setBackground(cell.getBackground());
        holder.myImageView.setBackgroundColor(cell.getDrawingCacheBackgroundColor());
        holder.myImageView.setColorFilter(cell.getColorFilter());
        holder.myImageView.setPadding(cell.getPaddingLeft(), cell.getPaddingTop(), cell.getPaddingRight(), cell.getPaddingBottom());
        holder.myImageView.setImageDrawable(cell.getDrawable());
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.length;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView myImageView;

        ViewHolder(View itemView) {
            super(itemView);
            myImageView = itemView.findViewById(R.id.iv_lab);
        }
    }

    // convenience method for getting data at click position
    ImageView getItem(int index) {
        return mData[index];
    }
}
