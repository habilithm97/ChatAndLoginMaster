package com.example.chatandloginmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    EditText edt;
    Button btn;

    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        id = getIntent().getStringExtra("id");

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true); // 아이템을 추가해도 리사이클러뷰의 크기가 변하지 않음

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        String[] dataSet = {"테스트1", "테스트2", "테스트3", "테스트4"};
        adapter = new RecyclerViewAdapter(dataSet);
        recyclerView.setAdapter(adapter);

        edt = (EditText)findViewById(R.id.edt);
        btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = edt.getText().toString();

                FirebaseDatabase database = FirebaseDatabase.getInstance();

                // 채팅 내용들을 날짜 시간대별로 관리
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String dateTime = dateFormat.format(calendar.getTime()); // 날짜 형식에 현재 날짜/시간을 넣음

                DatabaseReference myRef = database.getReference("message").child(dateTime);

                Hashtable<String, String> data = new Hashtable<String, String>();
                data.put("id", id);
                data.put("str", str);

                myRef.setValue(data);
            }
        });
    }
}