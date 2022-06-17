package ru.cs.vsu.ast2.ui.lk;

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
import ru.cs.vsu.ast2.api.account.AccountRequests;
import ru.cs.vsu.ast2.databinding.FragmentEditProfileBinding;

public class EditProfileFragment extends Fragment {

    public static final String TAG = "editProfileFragment";

    private EditProfileViewModel editProfileViewModel;
    private FragmentEditProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        editProfileViewModel = new ViewModelProvider(this).get(EditProfileViewModel.class);
        binding = FragmentEditProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final EditText editProfileNameField = root.findViewById(R.id.editProfileNameField);
        final EditText editProfileSurnameField = root.findViewById(R.id.editProfileSurnameField);
        final EditText editProfilePasswordField = root.findViewById(R.id.editProfilePasswordField);
        final EditText editProfilePasswordRepeatField = root.findViewById(R.id.editProfilePasswordRepeatField);
        final Button editProfileSaveButton = root.findViewById(R.id.editProfileSaveButton);

        editProfileSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccountRequests accountRequests = new AccountRequests();
                accountRequests.updateAccountInfo(AppSession.getToken(root.getContext()),
                        editProfileNameField.getText().toString(),
                        editProfileSurnameField.getText().toString(),
                        editProfilePasswordField.getText().toString(),
                        editProfilePasswordRepeatField.getText().toString(),
                        root.getContext());
            }
        });

        return root;
    }

}