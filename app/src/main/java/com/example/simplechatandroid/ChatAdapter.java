package com.example.simplechatandroid;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter {
    public static final int MSG_EMPLOYER =0;
    public static final int MSG_DRIVER=1;
    private final List<JSONObject> messages=new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0){
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_gray,parent,false);
            return new ReceiveMessagesViewHolder(view);
        }else{
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_white,parent,false);
            return new SendMessagesViewHolder(view);
        }      }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {

            if (getItemViewType(position)==MSG_DRIVER){
                SendMessagesViewHolder sendMessagesViewHolder= (SendMessagesViewHolder) holder;
                sendMessagesViewHolder.driverMessageTv.setText(messages.get(position).getString("messages"));
                sendMessagesViewHolder.messageTimeDriver.setText(messages.get(position).getString("time"));
            }else {
                ReceiveMessagesViewHolder receiveMessagesViewHolder= (ReceiveMessagesViewHolder) holder;
                receiveMessagesViewHolder.employerMessageTv.setText(messages.get(position).getString("messages"));
                receiveMessagesViewHolder.messageTimeEmployer.setText(messages.get(position).getString("time"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        JSONObject message=messages.get(position);
        try {
            if (message.getString("sender_type").equals("driver")){
                return MSG_DRIVER;
            }else {
                return MSG_EMPLOYER;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return 2;
        }
    }


    public static class SendMessagesViewHolder extends RecyclerView.ViewHolder{
        private final TextView driverMessageTv;
        private final TextView messageTimeDriver;


        public SendMessagesViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            driverMessageTv=itemView.findViewById(R.id.chatTxtWhite);
            messageTimeDriver=itemView.findViewById(R.id.timeTvDriver);

        }

    }
    public static class ReceiveMessagesViewHolder extends RecyclerView.ViewHolder{
        private final TextView employerMessageTv;
        private final TextView messageTimeEmployer;

        public ReceiveMessagesViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            employerMessageTv=itemView.findViewById(R.id.chatTxtGray);
            messageTimeEmployer=itemView.findViewById(R.id.timeTvEmployer);

        }

    }
    @SuppressLint("NotifyDataSetChanged")
    public void newMessage(JSONObject jsonObject) throws JSONException {
        messages.add(jsonObject);
        notifyDataSetChanged();
    }
}
