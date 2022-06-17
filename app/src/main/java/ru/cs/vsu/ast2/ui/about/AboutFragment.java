package ru.cs.vsu.ast2.ui.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import ru.cs.vsu.ast2.databinding.FragmentAboutBinding;


public class AboutFragment extends Fragment {

    private AboutViewModel aboutViewModel;
    private FragmentAboutBinding binding;


    public AboutFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        aboutViewModel = new ViewModelProvider(this).get(AboutViewModel.class);
        binding = FragmentAboutBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}