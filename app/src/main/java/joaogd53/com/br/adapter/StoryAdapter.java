package joaogd53.com.br.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import joaogd53.com.br.customViews.StoryCardView;
import joaogd53.com.br.imageloader.ImageLoader;
import joaogd53.com.br.ourwedding.R;
import joaogd53.com.br.ourweddingapp.model.Story;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.StoryAdapterViewHolder> {

    private List<Story> storyList;
    private Context context;

    public StoryAdapter(List<Story> storyList, Context context) {
        this.storyList = storyList;
        this.context = context;
        Collections.reverse(storyList);
    }

    @Override
    public StoryAdapter.StoryAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View storyCard = LayoutInflater.from(this.context).inflate(R.layout.story_card_layout, parent, false);
        return new StoryAdapterViewHolder(storyCard);
    }

    @Override
    public void onBindViewHolder(StoryAdapterViewHolder holder, int position) {
        Story story = storyList.get(position);
        try {
            ImageLoader il = new ImageLoader(this.context);
            il.displayImage(story.getImage().toString(), holder.imgCard);
        } catch (NullPointerException ex) {
            holder.imgCard.setImageResource(R.drawable.img_card);
        }
        holder.storyCardView.setStory(story);
        holder.txtSignature.setText(story.getSignature());
        holder.txtText.setText(story.getText());
        holder.txtTitle.setText(story.getTitle());
        int replace = story.getCommentsCount();
        String text = this.context.getResources().getQuantityString(R.plurals.comments, replace);
        String btnText = String.format(text, replace);
        holder.btnComment.setText(btnText);
    }

    @Override
    public int getItemCount() {
        return this.storyList.size();
    }

    public class StoryAdapterViewHolder extends RecyclerView.ViewHolder {

        private TextView txtTitle;
        private TextView txtText;
        private TextView txtSignature;
        private Button btnComment;
        private StoryCardView storyCardView;
        private ImageView imgCard;

        public StoryAdapterViewHolder(View view) {
            super(view);
            storyCardView = (StoryCardView) view;
            txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            txtText = (TextView) view.findViewById(R.id.txtText);
            txtSignature = (TextView) view.findViewById(R.id.txtSignature);
            btnComment = (Button) view.findViewById(R.id.btnComment);
            btnComment.setOnClickListener(storyCardView);
            imgCard = (ImageView) view.findViewById(R.id.imgCard);
        }
    }

    private class StoryComparator implements Comparator<Story> {

        @Override
        public int compare(Story story1, Story story2) {
            Integer id1 = story1.getIdStory();
            Integer id2 = story2.getIdStory();
            return id1.compareTo(id2);
        }
    }
}
