package com.zeafan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;
import com.zeafan.chat_app.R;

import java.util.ArrayList;

public class ListUsersAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<QBUser> users;

    public ListUsersAdapter(Context context, ArrayList<QBUser> qbChatDialogs) {
        this.context = context;
        this.users = qbChatDialogs;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=convertView;
        if(view==null)
        {
            LayoutInflater layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflater.inflate(android.R.layout.simple_list_item_multiple_choice,null);
            CheckedTextView textView=view.findViewById(android.R.id.text1);
            textView.setText(users.get(position).getLogin());
        }
        return view;
    }
}
