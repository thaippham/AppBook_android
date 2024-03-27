package com.example.chooseimage;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class AccountFragment extends Fragment {
    DBImageTitle dbImageTitle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        dbImageTitle = new DBImageTitle(getActivity());
        Button logoutButton = view.findViewById(R.id.logout_user);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserSession.getInstance().logout();
                updateUIAfterLogout(view);
            }
        });
        if (UserSession.getInstance().isLoggedIn()) {
            displayUserData(view);
        } else {
            displayLoginAndRegisterButtons(view);
        }
        Button login = view.findViewById(R.id.login_user);
        Button signup = view.findViewById(R.id.signup_user);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainLogin.class);
                startActivity(intent);
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainRegister.class);
                startActivity(intent);
            }
        });
        return view;
    }
    private void displayUserData(View view) {
        TextView nameUser = view.findViewById(R.id.nameuser);
        TextView emailUser = view.findViewById(R.id.emailuser);
        int userId = UserSession.getInstance().getUserId();
        String username = getUsernameById(userId);
        String email = getEmailById(userId);
        nameUser.setText(username);
        emailUser.setText(email);
    }
    private void displayLoginAndRegisterButtons(View view) {
        LinearLayout lnl_info = view.findViewById(R.id.info_user);
        lnl_info.setVisibility(View.GONE);
        Button btn_logout = view.findViewById(R.id.logout_user);
        btn_logout.setVisibility(View.GONE);
        LinearLayout lnl_btn = view.findViewById(R.id.btn_login_signup);
        lnl_btn.setVisibility(View.VISIBLE);
    }
    private void updateUIAfterLogout(View view) {
        LinearLayout lnl_info = view.findViewById(R.id.info_user);
        lnl_info.setVisibility(View.GONE);

        Button btn_logout = view.findViewById(R.id.logout_user);
        btn_logout.setVisibility(View.GONE);

        LinearLayout lnl_btn = view.findViewById(R.id.btn_login_signup);
        lnl_btn.setVisibility(View.VISIBLE);
        Toast.makeText(getActivity(), "Bạn đã đăng xuất!", Toast.LENGTH_SHORT).show();
    }
    private String getUsernameById(int userId) {
        return dbImageTitle.getUsernameById(userId);
    }

    private String getEmailById(int userId) {
        return dbImageTitle.getEmailById(userId);
    }

}