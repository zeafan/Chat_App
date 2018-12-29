package com.zeafan.chat_app;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.quickblox.chat.QBChatService;
import com.quickblox.chat.QBRestChatService;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBDialogType;
import com.quickblox.chat.utils.DialogUtils;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;
import com.zeafan.Common.Common;
import com.zeafan.adapter.ListUsersAdapter;
import com.zeafan.holder.QBUsersHolder;

import java.util.ArrayList;

public class ListUsersActivity extends AppCompatActivity {
ListView lstUsers;
Button btnSelectChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);
        lstUsers=findViewById(R.id.lstUsers);
        btnSelectChat=findViewById(R.id.btn_create_chat);
        btnSelectChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int countChoice = lstUsers.getCount();
                if (lstUsers.getCheckedItemPositions().size() == 1) {
                    createPrivateChat(lstUsers.getCheckedItemPositions());
                } else if (lstUsers.getCheckedItemPositions().size() > 1) {
                    createGroupChat(lstUsers.getCheckedItemPositions());
                }else {
                    Toast.makeText(ListUsersActivity.this, "Please Select Friend To chat", Toast.LENGTH_SHORT).show();
                }
                
            }
        });
        retrieveAllUsers();
    }

    private void createGroupChat(SparseBooleanArray checkedItemPositions) {
        final ProgressDialog mDialog=new ProgressDialog(ListUsersActivity.this);
        mDialog.setMessage("Please Waiting...");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
        ArrayList<Integer> occupantIdsList = new ArrayList<>();
        for(int i=0;i<lstUsers.getCount();i++)
        {
            if(checkedItemPositions.get(i))
            {
                QBUser user= (QBUser) lstUsers.getItemAtPosition(i);
                occupantIdsList.add(user.getId());
            }
        }
        QBChatDialog dialog=new QBChatDialog();
        dialog.setType(QBDialogType.GROUP);
        dialog.setName(Common.createChatDialogName(occupantIdsList));
        dialog.setOccupantsIds(occupantIdsList);
        QBRestChatService.createChatDialog(dialog).performAsync(new QBEntityCallback<QBChatDialog>() {
            @Override
            public void onSuccess(QBChatDialog qbChatDialog, Bundle bundle) {
              mDialog.dismiss();
                Toast.makeText(ListUsersActivity.this, "Create Chat Dialog successful", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(QBResponseException e) {

            }
        });
    }

    private void createPrivateChat(SparseBooleanArray checkedItemPositions) {
        final ProgressDialog mDialog=new ProgressDialog(ListUsersActivity.this);
        mDialog.setMessage("Please Waiting...");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
        for(int i=0;i<lstUsers.getCount();i++)
        {
            if(checkedItemPositions.get(i))
            {
                QBUser user= (QBUser) lstUsers.getItemAtPosition(i);
                QBChatDialog dialog=DialogUtils.buildPrivateDialog(user.getId());
                QBRestChatService.createChatDialog(dialog).performAsync(new QBEntityCallback<QBChatDialog>() {
                    @Override
                    public void onSuccess(QBChatDialog qbChatDialog, Bundle bundle) {
                        mDialog.dismiss();
                        Toast.makeText(ListUsersActivity.this, "Create private Chat Dialog successful", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onError(QBResponseException e) {

                    }
                });
            }
        }
        QBChatDialog dialog=new QBChatDialog();
    }

    private void retrieveAllUsers() {
        QBUsers.getUsers(null).performAsync(new QBEntityCallback<ArrayList<QBUser>>() {
            @Override
            public void onSuccess(ArrayList<QBUser> qbUsers, Bundle bundle) {
                ArrayList<QBUser> qbUsersWithoutCurrent = new ArrayList<>();
                QBUsersHolder.getInstance().putUsers(qbUsers);
                for(QBUser user:qbUsers)
                {
                    if(!user.getLogin().equals(QBChatService.getInstance().getUser().getLogin()))
                    {
                        qbUsersWithoutCurrent.add(user);
                    }
                }
                ListUsersAdapter adapter=new ListUsersAdapter(getBaseContext(),qbUsersWithoutCurrent);
                lstUsers.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(QBResponseException e) {

            }
        });
    }
}
