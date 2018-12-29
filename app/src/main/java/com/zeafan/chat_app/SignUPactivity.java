package com.zeafan.chat_app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.quickblox.auth.QBAuth;
import com.quickblox.auth.session.QBSession;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

public class SignUPactivity extends AppCompatActivity {
Button btnregister;
EditText eduser_name;
EditText edpassword,edFullName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_upactivity);
        RegisterSeesion();
        edFullName=findViewById(R.id.edFullName);
        eduser_name=findViewById(R.id.edUserName);
        edpassword=findViewById(R.id.edPassword);
        btnregister=findViewById(R.id.btnRegister);
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name=eduser_name.getText().toString();
                String password=edpassword.getText().toString();
                if(!user_name.isEmpty()&&password.length()>4)
                {
                    QBUser qbUser=new QBUser(user_name,password);
                    qbUser.setFullName(edFullName.getText().toString());
                    QBUsers.signUp(qbUser).performAsync(new QBEntityCallback<QBUser>() {
                        @Override
                        public void onSuccess(QBUser qbUser, Bundle bundle) {
                            Toast.makeText(SignUPactivity.this, "Successfull", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onError(QBResponseException e) {
                            Toast.makeText(SignUPactivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    private void RegisterSeesion() {
        QBAuth.createSession().performAsync(new QBEntityCallback<QBSession>() {
            @Override
            public void onSuccess(QBSession qbSession, Bundle bundle) {

            }

            @Override
            public void onError(QBResponseException e) {
                Toast.makeText(SignUPactivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
