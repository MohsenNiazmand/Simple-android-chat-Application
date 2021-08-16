package com.example.simplechatandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    private String token,time,sender_type,message;
    private ImageView btSendChat;
    private EditText etChat;
    private ProgressBar chatProgressBar;
    private RecyclerView recyclerViewChats,recyclerViewDefaultMessages;
    private ChatAdapter chatAdapter;
    private DefaultChatsAdapter defaultChatsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    }
}