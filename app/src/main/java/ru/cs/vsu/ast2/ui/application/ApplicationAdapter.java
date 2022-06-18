package ru.cs.vsu.ast2.ui.application;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import ru.cs.vsu.ast2.R;
import ru.cs.vsu.ast2.api.AppSession;
import ru.cs.vsu.ast2.api.account.AccountRequests;
import ru.cs.vsu.ast2.api.application.ApplicationRequest;
import ru.cs.vsu.ast2.api.application.dto.Application;
import ru.cs.vsu.ast2.util.AlertUtil;

import java.util.ArrayList;
import java.util.List;

public class ApplicationAdapter extends RecyclerView.Adapter<ApplicationAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<Application> applications = new ArrayList<>();
    private FragmentActivity activity;

    ApplicationAdapter(FragmentActivity activity, Context context, List<Application> applications) {
        this.inflater = LayoutInflater.from(context);
        this.activity = activity;
        for (int i = 0; i < applications.size(); i++) {
            if (!applications.get(i).getStatus().equals("Оплачено")) {
                this.applications.add(applications.get(i));
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.application_item_view, viewGroup, false);
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

        viewHolder.deleteApplicationButton.setOnClickListener(v ->
                AlertUtil.alertDialog(viewHolder.itemView,
                        "Удаление заявки",
                        String.format("Удалить заявку \"%s\"?", application.getId()),
                        (dialog, which) -> {
                            ApplicationRequest applicationRequest = new ApplicationRequest();
                            applicationRequest.deleteApplication(AppSession.getToken(v.getContext()), application.getId(), () -> null, () -> null);
                            applications.remove(i);
                            this.notifyItemRemoved(i);
                            this.notifyItemRangeChanged(i, applications.size());

                        }, (dialog, which) -> dialog.dismiss()));

        viewHolder.buyApplicationButton.setOnClickListener(v ->
                AlertUtil.alertDialog(viewHolder.itemView,
                        "Удаление заявки",
                        String.format("Удалить заявку \"%s\"?", application.getId()),
                        (dialog, which) -> {

                            AccountRequests applicationRequest = new AccountRequests();
                            applicationRequest.withdrawMoney(AppSession.getToken(v.getContext()), application.getPrice(), () -> {

                                Toast.makeText(inflater.getContext(), "Оплата заявки прошла успешно!", 3).show();

                                applications.get(i).setStatus("Оплачено");

                                ApplicationRequest applicationRequest1 = new ApplicationRequest();
                                applicationRequest1.addApplication(AppSession.getToken(v.getContext()), applications.get(i));

                                applications.remove(i);
                                this.notifyItemRemoved(i);
                                this.notifyItemRangeChanged(i, applications.size());

                                return null;
                            }, () -> {
                                Toast.makeText(inflater.getContext(), "Оплата заявки: что-то пошло не так...", 3).show();
                                return null;
                            });
                        }, (dialog, which) -> dialog.dismiss()));
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
        final Button buyApplicationButton;
        final Button deleteApplicationButton;

        public ViewHolder(View itemView) {
            super(itemView);
            nameApplicationItem = itemView.findViewById(R.id.nameApplicationItem);
            descriptionAddApplication = itemView.findViewById(R.id.descriptionAddApplication);
            partCategoryApplication = itemView.findViewById(R.id.partCategoryApplication);
            partApplication = itemView.findViewById(R.id.partApplication);
            statusApplication = itemView.findViewById(R.id.statusApplication);
            priceApplication = itemView.findViewById(R.id.priceApplication);
            buyApplicationButton = itemView.findViewById(R.id.buyApplicationButton);
            deleteApplicationButton = itemView.findViewById(R.id.deleteApplicationButton);
        }
    }
}
