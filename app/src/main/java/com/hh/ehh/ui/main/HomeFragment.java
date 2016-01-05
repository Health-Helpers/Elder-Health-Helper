package com.hh.ehh.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hh.ehh.R;
import com.hh.ehh.model.News;
import com.poliveira.parallaxrecyclerview.ParallaxRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mpifa on 23/11/15.
 */
public class HomeFragment extends Fragment{
    private RecyclerView rv = null;
    private ParallaxRecyclerAdapter<News> parallaxRecyclerAdapter;
    private SwipeRefreshLayout refreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        rv = (RecyclerView) view.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                    }
                }
        );
        List<News> newsList = new ArrayList<>();
        newsList.add(new News("New updates!","We have achieved new updates on this app",null));
        newsList.add(new News("Version 2.5!","New features on this new release",null));
        newsList.add(new News("Version 2.4!","New features on this new release",null));
        newsList.add(new News("Version 2.3!","New features on this new release",null));
        newsList.add(new News("Version 2.0!","New features on this new release",null));
        newsList.add(new News("Version 1.2!","New features on this new release",null));
        fillAdapter(newsList);
    }
    private void fillAdapter(final List<News> newsList) {
        parallaxRecyclerAdapter = new ParallaxRecyclerAdapter<News>(newsList) {
            @Override
            public void onBindViewHolderImpl(RecyclerView.ViewHolder viewHolder, ParallaxRecyclerAdapter<News> parallaxRecyclerAdapter, int i) {
                News news = parallaxRecyclerAdapter.getData().get(i);
                NewsHolder newsHolder = (NewsHolder) viewHolder;
                newsHolder.newsHeader.setText(news.getTitle());
                newsHolder.newsBody.setText(news.getBody());
                newsHolder.newsPhoto.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.medical_wallpaper));
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolderImpl(ViewGroup viewGroup, ParallaxRecyclerAdapter<News> parallaxRecyclerAdapter, int i) {
                return new NewsHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_list_item, viewGroup, false));
            }

            @Override
            public int getItemCountImpl(ParallaxRecyclerAdapter<News> parallaxRecyclerAdapter) {
                return newsList.size();
            }
        };
        parallaxRecyclerAdapter.setParallaxHeader(getActivity().getLayoutInflater().inflate(R.layout.news_list_header, rv, false), rv);
        rv.setAdapter(parallaxRecyclerAdapter);
    }

    private class NewsHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView newsHeader;
        TextView newsBody;
        ImageView newsPhoto;

        public NewsHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            newsHeader = (TextView) itemView.findViewById(R.id.news_tittle);
            newsBody = (TextView) itemView.findViewById(R.id.news_body);
            newsPhoto = (ImageView) itemView.findViewById(R.id.news_photo);
        }
    }

}
