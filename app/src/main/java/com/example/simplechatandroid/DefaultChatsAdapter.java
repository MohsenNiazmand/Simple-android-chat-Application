package com.example.simplechatandroid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DefaultChatsAdapter extends RecyclerView.Adapter<DefaultChatsAdapter.DefaultChatsViewHolder> {
    private ArrayList<String> defaultMessages = new ArrayList<>();
    private ItemEventListener itemEventListener;

    public DefaultChatsAdapter(ItemEventListener itemEventListener) {
        this.itemEventListener = itemEventListener;
        defaultMessages.add("Hi");
        defaultMessages.add("How Are you");
        defaultMessages.add("Where are You?");
        defaultMessages.add("I'm fine Thanks");
        defaultMessages.add("Please Wait");
        defaultMessages.add("I'm ready let's go");
        defaultMessages.add("Take care");
        defaultMessages.add("Bye");
    }

    @NonNull
    @Override
    public DefaultChatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_default_message,parent,false);
        return new DefaultChatsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DefaultChatsViewHolder holder, int position) {
        holder.bind(defaultMessages.get(position));
    }

    @Override
    public int getItemCount() {
        return defaultMessages.size();
    }

    public class DefaultChatsViewHolder extends RecyclerView.ViewHolder{
        TextView messageTv;
        public DefaultChatsViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTv=itemView.findViewById(R.id.defaultMessageTv);
        }
        public void bind(String defaultMsg){
            messageTv.setText(defaultMsg);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemEventListener.onItemClick(defaultMsg,getAdapterPosition());
                }
            });
        }
    }
    public interface ItemEventListener {
        void onItemClick(String defaultMessage,int position);
    }
}
