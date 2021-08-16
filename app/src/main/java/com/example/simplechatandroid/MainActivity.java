package com.example.simplechatandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {
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
        defaultChatsAdapter =new DefaultChatsAdapter();
        LinearLayoutManager defaultMessageslayoutManager= new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewDefaultMessages.setLayoutManager(defaultMessageslayoutManager);
        recyclerViewDefaultMessages.setAdapter(defaultChatsAdapter);
        recyclerViewChats =findViewById(R.id.rvMessages);
        chatAdapter=new ChatAdapter();
        LinearLayoutManager chatsLinearLayoutManager=new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerViewChats.setLayoutManager(chatsLinearLayoutManager);
        recyclerViewChats.setAdapter(chatAdapter);


        btSendChat.setOnClickListener(view -> {
            if (etChat.getText().length()>0){
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("token",token);
                    jsonObject.put("messages",etChat.getText());
                    socket.emit("driver_chat_employer",jsonObject);

                }catch (JSONException e){
                    e.printStackTrace();
                }
                chatProgressBar.setVisibility(View.VISIBLE);
                btSendChat.setVisibility(View.GONE);
                recyclerViewDefaultMessages.setVisibility(View.INVISIBLE);
                etChat.setText("");

            }
        });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        socket.disconnect();
    }
}