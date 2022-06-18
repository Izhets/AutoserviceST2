package ru.cs.vsu.ast2.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import ru.cs.vsu.ast2.R;
import ru.cs.vsu.ast2.api.AppSession;
import ru.cs.vsu.ast2.api.auth.AuthRequests;
import ru.cs.vsu.ast2.databinding.FragmentLoginBinding;
import ru.cs.vsu.ast2.ui.logged.LoggedMainActivity;
import ru.cs.vsu.ast2.ui.registration.RegistrationActivity;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;
    private FragmentLoginBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final Button loginButton = root.findViewById(R.id.auth_button);
        final Button regButton = root.findViewById(R.id.registrationSaveButton);
        final EditText loginField = root.findViewById(R.id.loginField);
        final EditText passwordField = root.findViewById(R.id.passwordField);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppSession.clearUserData(root.getContext());
                AuthRequests.login(loginField.getText().toString(), passwordField.getText().toString(), root.getContext(), new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getContext(), LoggedMainActivity.class);
                                startActivity(intent);
                            }
                        }, 3000);
                        AppSession.getInstance().collectAuthData(Objects.requireNonNull(getContext()));
                        return null;
                    }
                }, new Callable<Void>() {

                    @Override
                    public Void call() throws Exception {
                        return null;
                    }
                });
                return;
            }
        });

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RegistrationActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}