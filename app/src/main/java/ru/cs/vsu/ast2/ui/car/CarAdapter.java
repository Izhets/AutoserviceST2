package ru.cs.vsu.ast2.ui.car;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import ru.cs.vsu.ast2.R;
import ru.cs.vsu.ast2.api.AppSession;
import ru.cs.vsu.ast2.api.car.CarRequest;
import ru.cs.vsu.ast2.api.car.dto.Car;
import ru.cs.vsu.ast2.util.AlertUtil;
import ru.cs.vsu.ast2.util.Args;
import ru.cs.vsu.ast2.util.StringUtil;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<Car> cars;
    private FragmentActivity activity;

    CarAdapter(FragmentActivity activity, Context context, List<Car> cars) {
        this.inflater = LayoutInflater.from(context);
        this.cars = cars;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.car_item_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Car car = cars.get(i);
        viewHolder.nameCarItem.setText(car.getName());
        viewHolder.typeCarItem.setText("Тип авто: " + StringUtil.getTypeName(car.getType().getType()));
        viewHolder.brandCarItem.setText("Бренд: " + car.getModel().getBrand().getName());
        viewHolder.modelCarItem.setText("Модель: " + car.getModel().getName());
        viewHolder.numberCarItem.setText("Гос. номер: " + car.getNumber());

        viewHolder.deleteCarItemButton.setOnClickListener(v ->
                AlertUtil.alertDialog(viewHolder.itemView,
                        "Удаление автомобиля",
                        String.format("Удалить автомобиль \"%s\"?", car.getName()),
                        (dialog, which) -> {
                            CarRequest carRequest = new CarRequest();
                            carRequest.deleteCar(AppSession.getToken(v.getContext()), car.getId(), () -> null, () -> null);
                            cars.remove(i);
                            this.notifyItemRemoved(i);
                            this.notifyItemRangeChanged(i, cars.size());

                        }, (dialog, which) -> dialog.dismiss()));

        viewHolder.editCarItemButton.setOnClickListener(v -> {
            viewHolder.itemView.setEnabled(false);
            viewHolder.itemView.setVisibility(View.INVISIBLE);

            Bundle args = new Bundle();

            args.putString(Args.CAR_ID, car.getId().toString());
            args.putString(Args.CAR_NAME, car.getName());
            args.putString(Args.CAR_NUMBER, car.getNumber());
            args.putString(Args.CAR_MODEL, car.getModel().getName());
            args.putString(Args.CAR_BRAND, car.getModel().getBrand().getName());
            args.putString(Args.CAR_TYPE, car.getType().getType());

            AddCarFragment addCarFragment = new AddCarFragment();
            addCarFragment.setArguments(args);

            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.nav_host_fragment_logged_content_main, addCarFragment)
                    .commit();
        });

    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameCarItem;
        final TextView typeCarItem;
        final TextView brandCarItem;
        final TextView modelCarItem;
        final TextView numberCarItem;
        final Button editCarItemButton;
        final Button deleteCarItemButton;

        public ViewHolder(View itemView) {
            super(itemView);
            nameCarItem = itemView.findViewById(R.id.nameCarItem);
            typeCarItem = itemView.findViewById(R.id.typeCarItem);
            brandCarItem = itemView.findViewById(R.id.brandCarItem);
            modelCarItem = itemView.findViewById(R.id.modelCarItem);
            numberCarItem = itemView.findViewById(R.id.numberCarItem);
            editCarItemButton = itemView.findViewById(R.id.editCarItemButton);
            deleteCarItemButton = itemView.findViewById(R.id.deleteCarItemButton);
        }
    }
}
