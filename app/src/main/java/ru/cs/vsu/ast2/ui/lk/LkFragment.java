package ru.cs.vsu.ast2.ui.lk;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import ru.cs.vsu.ast2.MainActivity;
import ru.cs.vsu.ast2.R;
import ru.cs.vsu.ast2.api.AppSession;
import ru.cs.vsu.ast2.api.account.AccountRequests;
import ru.cs.vsu.ast2.databinding.FragmentLkBinding;

public class LkFragment extends Fragment {

    public static final String TAG = "lkFragment";

    private LkViewModel lkViewModel;
    private FragmentLkBinding binding;
    private View root;

    private EditProfileFragment editProfileFragment = new EditProfileFragment();
    private ReplenishBalanceFragment replenishBalanceFragment = new ReplenishBalanceFragment();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        lkViewModel = new ViewModelProvider(this).get(LkViewModel.class);
        binding = FragmentLkBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView userNameField = root.findViewById(R.id.userNameField);
        final TextView phoneNumberField = root.findViewById(R.id.phoneNumberField);
        final TextView emailField = root.findViewById(R.id.emailField);
        final TextView depositField = root.findViewById(R.id.depositField);
        final Button depositButton = root.findViewById(R.id.depositButton);
        final Button accountSettingsButton = root.findViewById(R.id.accountSettingsButton);
        final Button accountLogoutButton = root.findViewById(R.id.accountLogoutButton);

        AccountRequests accountRequests = new AccountRequests();
        accountRequests.getAccountInfo(AppSession.getToken(root.getContext()), () -> {
            userNameField.setText(accountRequests.getAccountInfo().getName() + " " + accountRequests.getAccountInfo().getSurname());
            phoneNumberField.setText("Телефон: " + accountRequests.getAccountInfo().getPhone());
            emailField.setText("Почта: " + accountRequests.getAccountInfo().getEmail());
            depositField.setText("Баланс: " + accountRequests.getAccountInfo().getMoney() + " р.");
            return null;
        }, () -> {
            android.app.AlertDialog.Builder dlgAlert = new android.app.AlertDialog.Builder(getContext());
            dlgAlert.setMessage("Проверьте соединение и попробуйте снова");
            dlgAlert.setTitle("Не удалось выполнить операцию");
            return null;
        });

        accountSettingsButton.setOnClickListener(view -> {
            root.setEnabled(false);
            root.setVisibility(View.INVISIBLE);
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment_logged_content_main, editProfileFragment).commit();
        });

        depositButton.setOnClickListener(view -> {
            root.setEnabled(false);
            root.setVisibility(View.INVISIBLE);
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment_logged_content_main, replenishBalanceFragment).commit();
        });

        accountLogoutButton.setOnClickListener(view -> {
            new AlertDialog.Builder(root.getContext())
                    .setTitle("Выйти из аккаунта?")
                    .setMessage("Вы действительно хотите выйти?")
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, (dialogInterface, i) -> {
                        AppSession.clearUserData(root.getContext());
                        Intent intent = new Intent(view.getContext(), MainActivity.class);
                        view.getContext().startActivity(intent);
                    }).create().show();
        });

        return root;
    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            getActivity().getSupportFragmentManager().beginTransaction().remove(editProfileFragment).commit();
            getActivity().getSupportFragmentManager().beginTransaction().remove(replenishBalanceFragment).commit();
        } catch (Exception e) {
        }
    }

}