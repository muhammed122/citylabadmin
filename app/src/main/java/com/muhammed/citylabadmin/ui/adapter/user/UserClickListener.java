package com.muhammed.citylabadmin.ui.adapter.user;

import com.muhammed.citylabadmin.data.model.user.User;

public interface UserClickListener {

     void openWhatsApp(User user);
     void sendResultToUser(User user);
}
