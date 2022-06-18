package ru.cs.vsu.ast2.ui.application.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import ru.cs.vsu.ast2.R;
import ru.cs.vsu.ast2.api.AppSession;
import ru.cs.vsu.ast2.api.application.dto.Application;
import ru.cs.vsu.ast2.databinding.FragmentApplicationHistoryBinding;

import java.util.ArrayList;
import java.util.List;

public class ApplicationHistoryFragment extends Fragment {

    public static final String TAG = "applicationHistoryFragment";

    private ApplicationHistoryViewModel applicationHistoryViewModel;
    private FragmentApplicationHistoryBinding binding;
    private View root;

    List<Application> applications = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        applicationHistoryViewModel = new ViewModelProvider(this).get(ApplicationHistoryViewModel.class);
        binding = FragmentApplicationHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        applications = AppSession.getInstance().getUserApplications();

        RecyclerView recyclerView = root.findViewById(R.id.applicationsHistoryList);

        ApplicationHistoryAdapter adapter = new ApplicationHistoryAdapter(getActivity(), root.getContext(), applications);
        recyclerView.setAdapter(adapter);

        return root;
    }

}