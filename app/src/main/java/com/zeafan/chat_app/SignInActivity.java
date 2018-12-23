package com.zeafan.chat_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.quickblox.auth.session.QBSettings;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

public class SignInActivity extends AppCompatActivity {
final String ApplicationID="59385";
    final String Authorization_key="JMkX-ruFCugjNJM";
    final String Authorization_secret="wj86eVOp8ZBzdfm";
    final String Account_key="DFCgsxe1Yu4os2k7gy8s";
    Button btnLogin;
    TextView tv_register;
    EditText eduser_name;
    EditText edpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initFramWork();
        eduser_name=findViewById(R.id.edUserName);
        edpassword=findViewById(R.id.edPassword);
        btnLogin=findViewById(R.id.btnLogin);
        tv_register=findViewById(R.id.tv_register);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name=eduser_name.getText().toString();
                String password=edpassword.getText().toString();
                if(!user_name.isEmpty()&&password.length()>4)
                {
                    QBUser qbUser=new QBUser(user_name,password);
                    QBUsers.signIn(qbUser).performAsync(new QBEntityCallback<QBUser>() {
                        @Override
                        public void onSuccess(QBUser qbUser, Bundle bundle) {
                            Toast.makeText(SignInActivity.this, "Successfull", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(QBResponseException e) {
                            Toast.makeText(SignInActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this,SignUPactivity.class));
            }
        });
    }

    private void initFramWork() {
        QBSettings.getInstance().init(getApplicationContext(),ApplicationID,Authorization_key,Authorization_secret);
        QBSettings.getInstance().setAccountKey(Account_key);
    }
}
