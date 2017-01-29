package joaogd53.com.br.ourwedding;

import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import joaogd53.com.br.adapter.StoryAdapter;
import joaogd53.com.br.customViews.DividerItemDecoration;
import joaogd53.com.br.ourweddingapp.application.OurWeddingApp;
import joaogd53.com.br.ourweddingapp.model.Story;

public class StoryFragment extends Fragment {

    private RecyclerView rvwStories;

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_story,
                container, false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        new StoryAsyncTask(this.getActivity(), rootView, mLayoutManager).execute();
//        List<Story> allStories = OurWeddingApp.getInstance().getStories();
        rvwStories = (RecyclerView) rootView.findViewById(R.id.rvwStories);
//        rvwStories.setLayoutManager(mLayoutManager);
//        rvwStories.setItemAnimator(new DefaultItemAnimator());
//        rvwStories.setAdapter(new StoryAdapter(allStories, this.getActivity()));
//        rvwStories.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        return rootView;
    }

    private class StoryAsyncTask extends AsyncTask<Void, Void, Void> {
        private Context context;
        private View view;
        private List<Story> allStories;
        private RecyclerView.LayoutManager mLayoutManager;

        private StoryAsyncTask(Context context, View view, RecyclerView.LayoutManager mLayoutManager) {
            this.context = context;
            this.view = view;
            this.mLayoutManager = mLayoutManager;
        }


        @Override
        protected Void doInBackground(Void... params) {
            this.allStories = OurWeddingApp.getInstance().getStories();
            Collections.sort(this.allStories, new Comparator<Story>() {
                @Override
                public int compare(Story story1, Story story2) {
                    return story1.getIdStory() - story2.getIdStory();
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            rvwStories.setLayoutManager(this.mLayoutManager);
            rvwStories.setItemAnimator(new DefaultItemAnimator());
            rvwStories.setAdapter(new StoryAdapter(allStories, this.context));
            rvwStories.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        }
    }

}
