package ru.cs.vsu.ast2.ui.car;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import ru.cs.vsu.ast2.R;
import ru.cs.vsu.ast2.api.AppSession;
import ru.cs.vsu.ast2.api.car.CarRequest;
import ru.cs.vsu.ast2.api.car.brand.dto.CarBrand;
import ru.cs.vsu.ast2.api.car.brand.dto.CarModel;
import ru.cs.vsu.ast2.api.car.dto.Car;
import ru.cs.vsu.ast2.api.car.type.dto.CarType;
import ru.cs.vsu.ast2.databinding.FragmentAddCarBinding;
import ru.cs.vsu.ast2.util.AlertUtil;
import ru.cs.vsu.ast2.util.Args;
import ru.cs.vsu.ast2.util.StringUtil;

import java.util.*;
import java.util.stream.Collectors;

import static ru.cs.vsu.ast2.util.StringUtil.getTypeName;

public class AddCarFragment extends Fragment {
    public static final String TAG = "addCarFragment";

    private AddCarViewModel addCarViewModel;
    private FragmentAddCarBinding binding;

    private Map<String, UUID> brandMap;
    private Map<String, UUID> modelMap = new HashMap();
    private Map<String, UUID> typeMap;
    private Map<String, List<String>> brandModelMap;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addCarViewModel = new ViewModelProvider(this).get(AddCarViewModel.class);
        binding = FragmentAddCarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final EditText nameEditCar = root.findViewById(R.id.nameEditCar);
        final EditText numberEditCar = root.findViewById(R.id.numberEditCar);

        final Spinner brandSpinnerEditCar = root.findViewById(R.id.brandSpinnerEditCar);
        final Spinner modelSpinnerEditCar = root.findViewById(R.id.modelSpinnerEditCar);
        final Spinner typeSpinnerEditCar = root.findViewById(R.id.typeCarSpinnerEditCar);
        final Button saveEditCarButton = root.findViewById(R.id.saveEditCarButton);

        brandMap = AppSession.getInstance().getCarBrands()
                .stream()
                .collect(Collectors.toMap(CarBrand::getName, CarBrand::getId));

        List<CarModel> modelList = AppSession.getInstance().getCarModels();
        for (CarModel cm : modelList) {
            modelMap.put(cm.getName(), cm.getId());
        }

        typeMap = AppSession.getInstance().getCarTypes()
                .stream()
                .collect(Collectors.toMap(CarType::getType, CarType::getId));

        List<String> brandNames = AppSession.getInstance()
                .getCarBrands().stream().map(CarBrand::getName)
                .collect(Collectors.toList());

        List<String> modelsNames = AppSession.getInstance()
                .getCarModels().stream().map(CarModel::getName)
                .collect(Collectors.toList());

        List<String> typesNames = AppSession.getInstance()
                .getCarTypes().stream().map(CarType::getType).map(StringUtil::getTypeName)
                .collect(Collectors.toList());

        ArrayAdapter<String> brandAdapter = new ArrayAdapter<>(root.getContext(),
                android.R.layout.simple_spinner_dropdown_item, brandNames);
        brandSpinnerEditCar.setAdapter(brandAdapter);

        ArrayAdapter<String> modelAdapter = new ArrayAdapter<>(root.getContext(),
                android.R.layout.simple_spinner_dropdown_item, modelsNames);
        modelSpinnerEditCar.setAdapter(modelAdapter);

        ArrayAdapter<String> typeCarAdapter = new ArrayAdapter<>(root.getContext(),
                android.R.layout.simple_spinner_dropdown_item, typesNames);
        typeSpinnerEditCar.setAdapter(typeCarAdapter);

        brandModelMap = new HashMap<>();

        AppSession.getInstance().getCarModels()
                .forEach(carModel -> {
                    String brand = carModel.getBrand().getName();
                    List<String> modelNames = brandModelMap.get(brand);

                    if (modelNames != null && !modelNames.isEmpty()) {
                        modelNames.add(carModel.getName());
                    } else {
                        ArrayList<String> models = new ArrayList<>();
                        models.add(carModel.getName());
                        modelNames = models;
                    }

                    brandModelMap.put(brand, modelNames);

                });

        if (isUpdateCar()) {
            nameEditCar.setText(getArguments().getString(Args.CAR_NAME));
            numberEditCar.setText(getArguments().getString(Args.CAR_NUMBER));

            brandSpinnerEditCar.setSelection(brandAdapter.getPosition(getArguments().getString(Args.CAR_BRAND)));
            modelSpinnerEditCar.setSelection(modelAdapter.getPosition(getArguments().getString(Args.CAR_MODEL)));
            typeSpinnerEditCar.setSelection(typeCarAdapter.getPosition(getArguments().getString(Args.CAR_TYPE)));
        }


        brandSpinnerEditCar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String brand = brandSpinnerEditCar.getSelectedItem().toString();
                ArrayAdapter<String> modelAdapter = new ArrayAdapter<>(root.getContext(),
                        android.R.layout.simple_spinner_dropdown_item, brandModelMap.get(brand));

                modelSpinnerEditCar.setAdapter(modelAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        numberEditCar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                saveEditCarButton.setEnabled(StringUtil.isCarNumber(s.toString()));
            }
        });


        saveEditCarButton.setOnClickListener(view -> {
            CarRequest carRequest = new CarRequest();

            CarBrand carBrand = new CarBrand();
            carBrand.setId(brandMap.get(brandSpinnerEditCar.getSelectedItem().toString()));

            CarModel carModel = new CarModel();
            carModel.setId(modelMap.get(modelSpinnerEditCar.getSelectedItem().toString()));

            CarType carType = new CarType();
            carType.setId(typeMap.get(getTypeName(typeSpinnerEditCar.getSelectedItem().toString())));

            carModel.setBrand(carBrand);

            Car car = Car.builder()
                    .name(nameEditCar.getText().toString())
                    .number(numberEditCar.getText().toString())
                    .model(carModel)
                    .type(carType)
                    .build();

            String message = "Авто \"" + car.getName() + "\" успешно добавлено!";

            if (isUpdateCar()) {
                car.setId(UUID.fromString(getArguments().getString(Args.CAR_ID)));
                message = "Авто успешно обновлено!";
            }

            carRequest.addCar(AppSession.getToken(getContext()), car);

            AlertUtil.alertDialog(root, null, message,
                    (dialog, which) -> carRequest.getUserCars(AppSession.getToken(getContext()), () -> {

                        root.setEnabled(false);
                        root.setVisibility(View.INVISIBLE);
                        MyCarsFragment myCarsFragment = new MyCarsFragment();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .add(R.id.nav_host_fragment_logged_content_main, myCarsFragment).commit();
                        return null;
                    }, () -> null));

        });

        return root;
    }

    private boolean isUpdateCar() {
        return getArguments() != null && getArguments().getString(Args.CAR_ID) != null;
    }

}