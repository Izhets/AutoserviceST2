package ru.cs.vsu.ast2.ui.news;

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
import ru.cs.vsu.ast2.api.news.dto.News;
import ru.cs.vsu.ast2.databinding.FragmentNewsBinding;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {

    private NewsViewModel newsViewModel;
    private FragmentNewsBinding binding;
    private View root;

    List<News> news = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        binding = FragmentNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        news = AppSession.getInstance().getNews();

        RecyclerView recyclerView = root.findViewById(R.id.newsList);
        NewsAdapter adapter = new NewsAdapter(getActivity(), root.getContext(), news);
        recyclerView.setAdapter(adapter);
        return root;
    }

}