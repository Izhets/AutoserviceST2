package ru.cs.vsu.ast2.ui.application.history;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import ru.cs.vsu.ast2.R;
import ru.cs.vsu.ast2.api.application.dto.Application;

import java.util.ArrayList;
import java.util.List;

public class ApplicationHistoryAdapter extends RecyclerView.Adapter<ApplicationHistoryAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<Application> applications = new ArrayList<>();
    private FragmentActivity activity;

    ApplicationHistoryAdapter(FragmentActivity activity, Context context, List<Application> applications) {
        this.inflater = LayoutInflater.from(context);
        this.activity = activity;
        for (int i = 0; i < applications.size(); i++) {
            if (applications.get(i).getStatus().equals("Оплачено")) {
                this.applications.add(applications.get(i));
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.application_history_item_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "WrongConstant"})
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
            Application application = applications.get(i);
            viewHolder.nameApplicationItem.setText("Заявка" + getItemCount());
            viewHolder.descriptionAddApplication.setText("Сообщение к заявке: " + application.getDescription());
            //viewHolder.partCategoryApplication.setText("Категория ремонта: " + application.getPart().getPartCategory().getName());
            viewHolder.partApplication.setText("Запчасть: " + application.getPart().getName());
            viewHolder.statusApplication.setText("Статус: " + application.getStatus());
            viewHolder.priceApplication.setText("Счёт: " + application.getPrice());
    }

    @Override
    public int getItemCount() {
        if (applications == null) return 0;
        return applications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameApplicationItem;
        final TextView descriptionAddApplication;
        final TextView partCategoryApplication;
        final TextView partApplication;
        final TextView statusApplication;
        final TextView priceApplication;

        public ViewHolder(View itemView) {
            super(itemView);
            nameApplicationItem = itemView.findViewById(R.id.nameHistoryApplicationItem);
            descriptionAddApplication = itemView.findViewById(R.id.descriptionAddHistoryApplication);
            partCategoryApplication = itemView.findViewById(R.id.partCategoryHistoryApplication);
            partApplication = itemView.findViewById(R.id.partHistoryApplication);
            statusApplication = itemView.findViewById(R.id.statusHistoryApplication);
            priceApplication = itemView.findViewById(R.id.priceHistoryApplication);
        }
    }
}