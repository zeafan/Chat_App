package com.zeafan.holder;

import android.util.SparseArray;

import com.quickblox.users.model.QBUser;

import java.util.ArrayList;
import java.util.List;

public class QBUsersHolder {
    private static QBUsersHolder instanse;
    private SparseArray<QBUser> qbUserSparseArray;
public static synchronized QBUsersHolder getInstance()
{
    if(instanse==null)
    {
        instanse = new QBUsersHolder();
    }
    return instanse;
}
private QBUsersHolder()
{
    qbUserSparseArray=new SparseArray<>();
}
public void putUsers(List<QBUser> users)
{
    for(QBUser user:users)
    {
        putUser(user);
    }
}

    private void putUser(QBUser user) {
        qbUserSparseArray.put(user.getId(),user);
    }

    public QBUser getUser(int id)
    {
        return qbUserSparseArray.get(id);
    }
    public List<QBUser> getUsersByIds(List<Integer> ids)
    {
        List<QBUser> users=new ArrayList<>();
        for(Integer id:ids)
        {
            QBUser user=getUser(id);
            if(user!=null)
            {
                users.add(user);
            }
        }
        return users;
    }
}
