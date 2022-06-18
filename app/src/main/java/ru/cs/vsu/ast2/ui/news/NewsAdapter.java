package ru.cs.vsu.ast2.ui.news;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import ru.cs.vsu.ast2.R;
import ru.cs.vsu.ast2.api.news.dto.News;
import ru.cs.vsu.ast2.util.ImageManager;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<News> news;
    private FragmentActivity activity;
    private Context context;

    NewsAdapter(FragmentActivity activity, Context context, List<News> news) {
        this.inflater = LayoutInflater.from(context);
        this.news = news;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.news_item_view, viewGroup, false);
        return new ViewHolder(view);
    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onBindViewHolder(NewsAdapter.ViewHolder viewHolder, int i) {
        News n = news.get(i);
        viewHolder.newsTitle.setText(n.getTitle());
        viewHolder.newsContent.setText(n.getContent());
        viewHolder.newsContent.setText(n.getContent());
        ImageManager.fetchImage(n.getImgUrl(), viewHolder.newsImage);
    }

    @Override
    public int getItemCount() {
        if (news == null) {
            Toast.makeText(context, "Новостей нет или возникли проблемы с интернет соединением", Toast.LENGTH_SHORT).show();
            return 0;
        }
        return news.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView newsTitle;
        final TextView newsContent;
        final ImageView newsImage;

        public ViewHolder(View itemView) {
            super(itemView);
            newsTitle = itemView.findViewById(R.id.newsTitle);
            newsContent = itemView.findViewById(R.id.newsContent);
            newsImage = itemView.findViewById(R.id.newsImage);
        }
    }
}
