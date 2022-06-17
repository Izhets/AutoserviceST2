package ru.cs.vsu.ast2.ui.car;

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
import ru.cs.vsu.ast2.api.car.dto.Car;
import ru.cs.vsu.ast2.databinding.FragmentMyCarsBinding;

import java.util.ArrayList;
import java.util.List;

public class MyCarsFragment extends Fragment {

    public static final String TAG = "myCarsFragment";

    private MyCarsViewModel myCarsViewModel;
    private FragmentMyCarsBinding binding;
    private View root;

    private AddCarFragment addCarFragment = new AddCarFragment();

    List<Car> cars = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        myCarsViewModel = new ViewModelProvider(this).get(MyCarsViewModel.class);
        binding = FragmentMyCarsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final FloatingActionButton floatingActionButton = root.findViewById(R.id.addCar);

        cars = AppSession.getInstance().getUserCars();

        RecyclerView recyclerView = root.findViewById(R.id.carsList);
        CarAdapter adapter = new CarAdapter(getActivity(), root.getContext(), cars);
        recyclerView.setAdapter(adapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                root.setEnabled(false);
                root.setVisibility(View.INVISIBLE);
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment_logged_content_main, addCarFragment).commit();
            }
        });


        return root;
    }

}