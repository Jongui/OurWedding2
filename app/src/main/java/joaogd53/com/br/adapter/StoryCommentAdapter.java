package joaogd53.com.br.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import joaogd53.com.br.imageloader.ImageLoader;
import joaogd53.com.br.ourwedding.R;
import joaogd53.com.br.ourweddingapp.application.OurWeddingApp;
import joaogd53.com.br.ourweddingapp.model.Guest;
import joaogd53.com.br.ourweddingapp.model.Story;
import joaogd53.com.br.ourweddingapp.model.StoryComment;

public class StoryCommentAdapter extends BaseAdapter {
    private Story story;
    private Context context;

    public StoryCommentAdapter(Context context, Story story) {
        this.story = story;
        if (this.story.getComments().size() == 0) {
            OurWeddingApp.getInstance().getStoryComments(story);
        }
        this.context = context;
    }

    @Override
    public int getCount() {
        return ((story.getComments().size() != 0) ? story.getComments().size() : 1);
    }

    @Override
    public Object getItem(int position) {
        return story.getComments().get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        try {
            return this.commentLineView(position, view, viewGroup);
        } catch (Exception ex) {
            return this.noCommentLineView(view, viewGroup);
        }
    }

    private View noCommentLineView(View view, ViewGroup viewGroup) {
        View convertView = view;
        if (view == null) {
            LayoutInflater vi = LayoutInflater.from(this.context);
            convertView = vi.inflate(R.layout.no_comment_line, viewGroup, false);
        }
        return convertView;
    }

    private View commentLineView(int position, View view, ViewGroup viewGroup) {
        View convertView = view;
        StoryComment comment = story.getComments().get(position);
        LayoutInflater vi = LayoutInflater.from(this.context);
        convertView = vi.inflate(R.layout.comment_line_layout, viewGroup, false);
        TextView txtCommentText = (TextView) convertView.findViewById(R.id.txtCommentText);
        txtCommentText.setText(comment.getText());
        ImageView imgGuest = (ImageView) convertView.findViewById(R.id.imgGuest);
        Guest guest = comment.getGuest();
        if (guest.getPersonName().equalsIgnoreCase(""))
            guest = OurWeddingApp.getInstance().getGuestById(guest.getIdGuest());
        try {
            ImageLoader il = new ImageLoader(this.context);
            il.displayImage(guest.getPersonPhoto().toString(), imgGuest);
        } catch (NullPointerException ex) {
            imgGuest.setImageResource(R.drawable.ic_menu_camera);
        }
        TextView txtUserName = (TextView) convertView.findViewById(R.id.txtUserName);
        txtUserName.setText(guest.getPersonName());
        return convertView;
    }


}
