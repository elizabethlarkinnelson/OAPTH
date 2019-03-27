package com.onramp.android.takehome;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder> {

    private final LayoutInflater mInflater;
    private List<Item> mItems;

    ItemListAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position){
        if (mItems != null){
            Item current = mItems.get(position);
            holder.itemItemView.setText(current.getItem());
        } else {
            holder.itemItemView.setText("Blank");
        }
    }

    void setItems(List<Item> items){
        mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){
        if (mItems != null){
            return mItems.size();
        } else{
            return 0;
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        private final TextView itemItemView;

        private ItemViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            itemItemView = itemView.findViewById(R.id.coffeeItem);

        }

        @Override
        public void onClick(View view){
            int pos = getLayoutPosition();
            long itemId = getItemId();

            Toast.makeText(view.getContext(), "I DID IT" + Integer.toString(pos)+ Long.toString(itemId),
                    Toast.LENGTH_LONG).show();


        }

    }
}
