package com.danielsoto.coolstuff;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginFragment extends Fragment {
    private DatabaseHelper databaseHelper;
    private Context context;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        final TextInputLayout usernameTextInput = view.findViewById(R.id.username_text_input);
        final TextInputEditText usernameTextEditText = view.findViewById(R.id.username_edit_text);
        final TextInputLayout passwordTextInput = view.findViewById(R.id.password_text_input);
        final TextInputEditText passwordTextEditText = view.findViewById(R.id.password_edit_text);
        MaterialButton loginButton = view.findViewById(R.id.button_login);
        MaterialButton registerButton = view.findViewById(R.id.button_register);
        databaseHelper = new DatabaseHelper(this.getContext());

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getUsername = usernameTextEditText.getText().toString();
                String getPassword = passwordTextEditText.getText().toString();
                databaseHelper.registerUser(new Data(getUsername, getPassword));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getUsername = usernameTextEditText.getText().toString();
                String getPassword = passwordTextEditText.getText().toString();
                databaseHelper.loginUser(new Data(getUsername, getPassword));
                if(!isPasswordValid(passwordTextEditText.getText())) {
                    passwordTextInput.setError(getString(R.string.error_password));
                } else {
                    passwordTextInput.setError(null);
                    ((NavigationHost) getActivity()).navigateTo(new ProductGridFragment(), false);
                }
            }
        });

        passwordTextEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(isPasswordValid(passwordTextEditText.getText())){
                    passwordTextInput.setError(null);
                }
                return false;
            }
        });
    return view;
    }
    private boolean isPasswordValid(@Nullable Editable text) {
        return text != null && text.length() >= 3;
    }
}
