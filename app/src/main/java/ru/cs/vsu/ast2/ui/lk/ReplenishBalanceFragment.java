package ru.cs.vsu.ast2.ui.lk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import ru.cs.vsu.ast2.R;
import ru.cs.vsu.ast2.api.AppSession;
import ru.cs.vsu.ast2.api.account.AccountRequests;
import ru.cs.vsu.ast2.databinding.FragmentReplenishBalanceBinding;

import java.util.concurrent.Callable;

public class ReplenishBalanceFragment extends Fragment {

    public static final String TAG = "replenishBalanceFragment";

    private ReplenishBalanceViewModel replenishBalanceViewModel;
    private FragmentReplenishBalanceBinding binding;

    String[] paymentMethods = {"Банковская карта"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        replenishBalanceViewModel = new ViewModelProvider(this).get(ReplenishBalanceViewModel.class);
        binding = FragmentReplenishBalanceBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView actualBalanceField = root.findViewById(R.id.actualBalanceField);
        final EditText replenishBalanceField = root.findViewById(R.id.replenishBalanceField);
        final Spinner spinner = root.findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(root.getContext(),
                android.R.layout.simple_spinner_dropdown_item, paymentMethods);
        spinner.setAdapter(adapter);

        Button replenishBalanceButton = root.findViewById(R.id.replenishBalanceButton);

        AccountRequests accountRequests = new AccountRequests();
        accountRequests.getAccountInfo(AppSession.getToken(root.getContext()), new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                actualBalanceField.setText("Текущий баланс: " + accountRequests.getAccountInfo().getMoney() + " р.");
                return null;
            }
        }, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                android.app.AlertDialog.Builder dlgAlert = new android.app.AlertDialog.Builder(getContext());
                dlgAlert.setMessage("Проверьте соединение и попробуйте снова");
                dlgAlert.setTitle("Не удалось выполнить операцию");
                return null;
            }
        });

        replenishBalanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (replenishBalanceField.getText() != null) {
                    AccountRequests accountRequests = new AccountRequests();
                    accountRequests.replenishBalanceAccount(AppSession.getToken(root.getContext()),
                            spinner.getSelectedItem().toString(),
                            Integer.valueOf(replenishBalanceField.getText().toString()),
                            root.getContext());
                    root.setEnabled(false);
                    root.setVisibility(View.INVISIBLE);
                    Fragment lkFragment = new LkFragment();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_logged_content_main, lkFragment, "lkFragment").commit();
                }
            }
        });

        return root;
    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            Fragment lkFragment = new LkFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment_logged_content_main, lkFragment, "lkFragment").commit();
        } catch (Exception e) {
        }
    }

}