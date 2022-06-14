package ru.cs.vsu.ast2.ui.registration;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import ru.cs.vsu.ast2.R;
import ru.cs.vsu.ast2.api.auth.AuthRequests;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        final EditText nameField = findViewById(R.id.registrationNameField);
        final EditText surnameField = findViewById(R.id.registrationSurnameField);
        final EditText phoneNumberField = findViewById(R.id.registrationPhoneNumberField);
        final EditText emailField = findViewById(R.id.registrationEmailField);
        final EditText passwordField = findViewById(R.id.registrationPasswordField);
        final EditText passwordRepeatField = findViewById(R.id.registrationPasswordRepeatField);
        final Button regButton = findViewById(R.id.registrationSaveButton);

        regButton.setOnClickListener(view -> {
            AuthRequests.register(nameField.getText().toString(), surnameField.getText().toString(),
                    phoneNumberField.getText().toString(), emailField.getText().toString(),
                    passwordField.getText().toString(), passwordRepeatField.getText().toString(),
                    RegistrationActivity.this);
        });
    }
}