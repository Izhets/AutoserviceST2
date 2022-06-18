package ru.cs.vsu.ast2.ui.application;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import ru.cs.vsu.ast2.R;
import ru.cs.vsu.ast2.api.AppSession;
import ru.cs.vsu.ast2.api.application.dto.Application;
import ru.cs.vsu.ast2.databinding.FragmentMyApplicationsBinding;

import java.util.ArrayList;
import java.util.List;

public class MyApplicationsFragment extends Fragment {

    public static final String TAG = "myCarsFragment";

    private MyApplicationsViewModel myApplicationsViewModel;
    private FragmentMyApplicationsBinding binding;
    private View root;

    private AddApplicationFragment addApplicationFragment = new AddApplicationFragment();

    List<Application> applications = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        myApplicationsViewModel = new ViewModelProvider(this).get(MyApplicationsViewModel.class);
        binding = FragmentMyApplicationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final FloatingActionButton floatingActionButton = root.findViewById(R.id.addApplication);

        applications = AppSession.getInstance().getUserApplications();

        RecyclerView recyclerView = root.findViewById(R.id.applicationsList);

        ApplicationAdapter adapter = new ApplicationAdapter(getActivity(), root.getContext(), applications);
        recyclerView.setAdapter(adapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                root.setEnabled(false);
                root.setVisibility(View.INVISIBLE);
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment_logged_content_main, addApplicationFragment).commit();
            }
        });

        return root;
    }

}