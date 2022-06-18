package ru.cs.vsu.ast2.ui.application;

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
import ru.cs.vsu.ast2.api.application.ApplicationRequest;
import ru.cs.vsu.ast2.api.application.dto.Application;
import ru.cs.vsu.ast2.api.car.dto.Car;
import ru.cs.vsu.ast2.api.application.part.dto.Part;
import ru.cs.vsu.ast2.api.application.part.dto.PartCategory;
import ru.cs.vsu.ast2.databinding.FragmentAddApplicationBinding;
import ru.cs.vsu.ast2.util.AlertUtil;
import ru.cs.vsu.ast2.util.Args;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AddApplicationFragment extends Fragment {
    public static final String TAG = "addApplicationFragment";

    private AddApplicationViewModel addApplicationViewModel;
    private FragmentAddApplicationBinding binding;

    private Map<String, Car> carMap;
    private Map<String, UUID> partCategoryMap;
    private Map<String, UUID> partMap;
    private Map<String, List<String>> partCategoryPartMap;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addApplicationViewModel = new ViewModelProvider(this).get(AddApplicationViewModel.class);
        binding = FragmentAddApplicationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final Spinner autoSpinnerAddApplication = root.findViewById(R.id.autoSpinnerAddApplication);
        final Spinner partCategorySpinnerAddApplication = root.findViewById(R.id.partCategorySpinnerAddApplication);
        final Spinner partSpinnerAddApplication = root.findViewById(R.id.partSpinnerAddApplication);

        final EditText descriptionAddApplication = root.findViewById(R.id.descriptionAddApplication);

        final Button saveAddApplicationButton = root.findViewById(R.id.saveAddApplicationButton);

        carMap = AppSession.getInstance().getUserCars()
                .stream()
                .collect(Collectors.toMap(Car::getName, Function.identity()));

        partCategoryMap = AppSession.getInstance().getPartCategories()
                .stream()
                .collect(Collectors.toMap(PartCategory::getName, PartCategory::getId));

        partMap = AppSession.getInstance().getPart()
                .stream()
                .collect(Collectors.toMap(Part::getName, Part::getId));

        List<String> partCategoriesNames = AppSession.getInstance()
                .getPartCategories().stream().map(PartCategory::getName)
                .collect(Collectors.toList());

        List<String> partNames = AppSession.getInstance()
                .getPart().stream().map(Part::getName)
                .collect(Collectors.toList());

        ArrayAdapter<String> partCategoryAdapter = new ArrayAdapter<>(root.getContext(),
                android.R.layout.simple_spinner_dropdown_item, partCategoriesNames);
        partCategorySpinnerAddApplication.setAdapter(partCategoryAdapter);

        ArrayAdapter<String> partAdapter = new ArrayAdapter<>(root.getContext(),
                android.R.layout.simple_spinner_dropdown_item, partNames);
        partSpinnerAddApplication.setAdapter(partAdapter);

        List<String> carNames = AppSession.getInstance().getUserCars()
                .stream()
                .map(Car::getName)
                .collect(Collectors.toList());

        ArrayAdapter<String> carNamesAdapter = new ArrayAdapter<>(root.getContext(),
                android.R.layout.simple_spinner_dropdown_item, carNames);

        autoSpinnerAddApplication.setAdapter(carNamesAdapter);

        partCategoryPartMap = new HashMap<>();

        AppSession.getInstance().getPart()
                .forEach(part -> {
                    String partCategory = part.getPartCategory().getName();
                    List<String> modelNames = partCategoryPartMap.get(partCategory);

                    if (modelNames != null && !modelNames.isEmpty()) {
                        modelNames.add(part.getName());
                    } else {
                        ArrayList<String> models = new ArrayList<>();
                        models.add(part.getName());
                        modelNames = models;
                    }

                    partCategoryPartMap.put(partCategory, modelNames);

                });

        if (isUpdateCar()) {
            partCategorySpinnerAddApplication.setSelection(partCategoryAdapter.getPosition(getArguments().getString(Args.CAR_BRAND)));
            partSpinnerAddApplication.setSelection(partAdapter.getPosition(getArguments().getString(Args.CAR_MODEL)));
        }

        partCategorySpinnerAddApplication.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String brand = partCategorySpinnerAddApplication.getSelectedItem().toString();
                ArrayAdapter<String> modelAdapter = new ArrayAdapter<>(root.getContext(),
                        android.R.layout.simple_spinner_dropdown_item, partCategoryPartMap.get(brand));

                partSpinnerAddApplication.setAdapter(modelAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        saveAddApplicationButton.setOnClickListener(view -> {
            ApplicationRequest applicationRequest = new ApplicationRequest();

            PartCategory partCategory = new PartCategory();
            partCategory.setId(partCategoryMap.get(partCategorySpinnerAddApplication.getSelectedItem().toString()));

            Part part = new Part();
            part.setId(partMap.get(partSpinnerAddApplication.getSelectedItem().toString()));

            part.setPartCategory(partCategory);

            Application application = Application.builder()
                    .price(0)
                    .status("Ожидание мастера")
                    .description(descriptionAddApplication.getText().toString())
                    .part(part)
                    .build();

            String message = "Заявка на замену \"" + application.getPart().getName() + "\" успешно добавлена!";

            if (isUpdateCar()) {
                application.setId(UUID.fromString(getArguments().getString(Args.CAR_ID)));
                message = "Заявка успешна обновлена!";
            }

            applicationRequest.addApplication(AppSession.getToken(getContext()), application);

            AlertUtil.alertDialog(root, null, message,
                    (dialog, which) -> applicationRequest.getUserApplications(AppSession.getToken(getContext()), () -> {

                        root.setEnabled(false);
                        root.setVisibility(View.INVISIBLE);
                        MyApplicationsFragment myApplicationsFragment = new MyApplicationsFragment();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .add(R.id.nav_host_fragment_logged_content_main, myApplicationsFragment).commit();
                        return null;
                    }, () -> null));

        });

            return root;
    }

    private boolean isUpdateCar() {
        return getArguments() != null && getArguments().getString(Args.CAR_ID) != null;
    }

}