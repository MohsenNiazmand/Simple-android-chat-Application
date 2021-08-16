package com.example.simplechatandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity implements DefaultChatsAdapter.ItemEventListener {
    private String token,time,sender_type,message;
    private ImageView btSendChat;
    private EditText etChat;
    private ProgressBar chatProgressBar;
    private RecyclerView recyclerViewChats,recyclerViewDefaultMessages;
    private ChatAdapter chatAdapter;
    private DefaultChatsAdapter defaultChatsAdapter;
    private Handler handler;
    private Socket socket;

    {
        try {
            socket = IO.socket("Your Server Base Url");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        socket.connect();
        handler = new Handler();




        etChat=findViewById(R.id.etMessage);
        btSendChat=findViewById(R.id.btnSendMessage);
        chatProgressBar=findViewById(R.id.progressChat);

        recyclerViewDefaultMessages=findViewById(R.id.rvDefaultMessages);
        defaultChatsAdapter =new DefaultChatsAdapter(this);
        LinearLayoutManager defaultMessageslayoutManager= new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewDefaultMessages.setLayoutManager(defaultMessageslayoutManager);
        recyclerViewDefaultMessages.setAdapter(defaultChatsAdapter);
        recyclerViewChats =findViewById(R.id.rvMessages);
        chatAdapter=new ChatAdapter();
        LinearLayoutManager chatsLinearLayoutManager=new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerViewChats.setLayoutManager(chatsLinearLayoutManager);
        recyclerViewChats.setAdapter(chatAdapter);


        // in this scenario we send message from user to an unique user and this feature works with socket
        //and connection between them is done through tokens in server
        //you can customize these features as you like for example you can add sending images feature or ticking messages or...
        //and many other things As required


        //First we send our message to server
        btSendChat.setOnClickListener(view -> {
            if (etChat.getText().length()>0){
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("token",token);
                    jsonObject.put("messages",etChat.getText());
                    socket.emit("Sending Message Socket Address",jsonObject);

                }catch (JSONException e){
                    e.printStackTrace();
                }
                chatProgressBar.setVisibility(View.VISIBLE);
                btSendChat.setVisibility(View.GONE);
                recyclerViewDefaultMessages.setVisibility(View.INVISIBLE);
                etChat.setText("");

            }
        });

        //In this event, Server send us our message when it saves in database and we show it
        socket.on("Sent Messages Socket",sentMessages);

        //In this event, the server sends us the other person's messages and we show it
        socket.on("Receiving Messages Socket", getMessages);

    }


    public Emitter.Listener sentMessages = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject)args[0];
            handler.post(() -> {
                try {
                    JSONArray sentMessages;
                    sentMessages=data.getJSONArray("messages");
                    int index=sentMessages.length()-1;
                    JSONObject messageObject=sentMessages.getJSONObject(index);
                    time = messageObject.getString("time");
                    //isSendBoolean for tick feature
                    sender_type=messageObject.getString("sender_type");
                    message=messageObject.getString("message");
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("time",time);
                    jsonObject.put("sender_type",sender_type);
                    jsonObject.put("messages",message);
//                    jsonObject.put("isSent",isSendBoolean);
                    chatProgressBar.setVisibility(View.GONE);
                    btSendChat.setVisibility(View.VISIBLE);
                    recyclerViewDefaultMessages.setVisibility(View.VISIBLE);
                    chatAdapter.newMessage(jsonObject);
                    recyclerViewChats.smoothScrollToPosition(chatAdapter.getItemCount());

                }catch (Exception e){
                    e.printStackTrace();

                }

            });
        }
    };
    public Emitter.Listener getMessages = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject)args[0];
            handler.post(() -> {
                try {

                    JSONArray receiveMessages;
                    receiveMessages=data.getJSONArray("messages");
                    int index=receiveMessages.length()-1;
                    JSONObject messageObject=receiveMessages.getJSONObject(index);
                    time = messageObject.getString("time");
                    sender_type=messageObject.getString("sender_type");
                    message=messageObject.getString("message");
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("time",time);
                    jsonObject.put("sender_type",sender_type);
                    jsonObject.put("messages",message);
                    chatAdapter.newMessage(jsonObject);
                    recyclerViewChats.smoothScrollToPosition(chatAdapter.getItemCount());


                }catch (Exception e){
                    e.printStackTrace();

                }

            });
        }
    };



    @Override
    public void onItemClick(String defaultMessage, int position) {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("token",token);
            jsonObject.put("messages",defaultMessage);
            socket.emit("Sending Message Socket Address",jsonObject);
            chatProgressBar.setVisibility(View.VISIBLE);
            btSendChat.setVisibility(View.GONE);
            recyclerViewDefaultMessages.setVisibility(View.INVISIBLE);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }
}