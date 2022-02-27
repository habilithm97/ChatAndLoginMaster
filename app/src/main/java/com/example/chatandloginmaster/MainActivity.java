package com.example.chatandloginmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Hashtable;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FirebaseAuth mAuth; // 파이어베이스 인증 객체

    EditText idEdt, pwEdt;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance(); // 파이어베이스 인증 객체를 가져와서 변수에 할당함

        idEdt = (EditText)findViewById(R.id.idEdt);
        pwEdt = (EditText)findViewById(R.id.pwEdt);

        Button registerBtn = (Button)findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = idEdt.getText().toString();
                String pw = pwEdt.getText().toString();

                if(id.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "아이디를 입력하세요. ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pw.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "패스워드를 입력하세요. ", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 신규 회원  가입
                mAuth.createUserWithEmailAndPassword(id, pw).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    Log.d(TAG,  "회원 가입에 성공하였습니다. ");
                                    Toast.makeText(MainActivity.this, "회원 가입에 성공하였습니다. ", Toast.LENGTH_SHORT).show();

                                    FirebaseUser user = mAuth.getCurrentUser(); // 로그인한 회원의 계정정보 가져오기
                                    //updateUI(user);
                                } else {
                                    Log.w(TAG, "회원 가입에 실패하였습니다. ", task.getException());
                                    Toast.makeText(MainActivity.this, "회원 가입에 실패하였습니다. ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        Button loginBtn = (Button)findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = idEdt.getText().toString();
                String pw = pwEdt.getText().toString();

                if(id.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "아이디를 입력하세요. ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pw.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "패스워드를 입력하세요. ", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(id, pw)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if(task.isSuccessful()) {
                                    Log.d(TAG,  "로그인에 성공하였습니다. ");
                                    Toast.makeText(MainActivity.this, "로그인에 성공하였습니다. ", Toast.LENGTH_SHORT).show();

                                    FirebaseUser user = mAuth.getCurrentUser(); // 로그인한 회원의 계정 정보 가져오기
                                    if(user != null) {
                                        String id = user.getEmail();
                                        String name = user.getDisplayName();
                                        Log.d(TAG, "아이디 : " + id + ", 이름 : " + name);
                                    }
                                    //updateUI(user);

                                    Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                                    startActivity(intent);

                                } else {
                                    Log.w(TAG, "로그인에 실패하였습니다. ", task.getException());
                                    Toast.makeText(MainActivity.this, "로그인에 실패하였습니다. ", Toast.LENGTH_SHORT).show();
                                    //updateUI(user);
                                }
                            }
                        });
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // 앱이 시작되면 사용자가 로그인했는지 확인하고 그에 따라 UI를 업데이트함
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }
}