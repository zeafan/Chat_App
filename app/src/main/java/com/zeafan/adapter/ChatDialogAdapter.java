package com.zeafan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.quickblox.chat.model.QBChatDialog;
import com.zeafan.chat_app.R;

import java.util.ArrayList;

public class ChatDialogAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<QBChatDialog> qbChatDialogs;

    public ChatDialogAdapter(Context context, ArrayList<QBChatDialog> qbChatDialogs) {
        this.context = context;
        this.qbChatDialogs = qbChatDialogs;
    }

    @Override
    public int getCount() {
        return qbChatDialogs.size();
    }

    @Override
    public Object getItem(int position) {
        return qbChatDialogs.get(position);
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
            view=layoutInflater.inflate(R.layout.list_chat_dialog,null);
            TextView title=view.findViewById(R.id.list_item_Title);
            TextView mess=view.findViewById(R.id.list_item_message);
            ImageView img=view.findViewById(R.id.image_chatDialog);
            title.setText(qbChatDialogs.get(position).getName());
            mess.setText(qbChatDialogs.get(position).getLastMessage());
            int randomColor=ColorGenerator.MATERIAL.getRandomColor();
            TextDrawable.IBuilder builder=TextDrawable.builder().beginConfig()
                    .withBorder(4).endConfig().round();
         TextDrawable textDrawable=   builder.build(title.getText().toString().substring(0,1),randomColor);
            img.setImageDrawable(textDrawable);
        }



        return view;
    }
}
