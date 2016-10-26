package joaogd53.com.br.ourwedding;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import joaogd53.com.br.dialog.StoryCommentDialogFragment;
import joaogd53.com.br.imageloader.ImageLoader;
import joaogd53.com.br.ourweddingapp.application.OurWeddingApp;
import joaogd53.com.br.ourweddingapp.model.Story;

public class StoryFragment extends Fragment {

    private TextView txtTitle;
    private TextView txtText;
    private TextView txtSignature;
    private Button btnComment;

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_story,
                container, false);
//        CardView cardView = (CardView) rootView.findViewById(R.id.storyCard);
        ImageView imgCard = (ImageView) rootView.findViewById(R.id.imgCard);
        final Story story = OurWeddingApp.getInstance().lastStory();
        try {
            Context context = this.getActivity();
            ImageLoader il = new ImageLoader(context);
            il.displayImage(story.getImage().toString(), imgCard);
        } catch (NullPointerException ex) {
            imgCard.setImageResource(R.drawable.img_card);
        }
        txtTitle = (TextView) rootView.findViewById(R.id.txtTitle);
        txtText = (TextView) rootView.findViewById(R.id.txtText);
        txtSignature = (TextView) rootView.findViewById(R.id.txtSignature);
        txtTitle.setText(story.getTitle());
        txtText.setText(story.getText());
        txtSignature.setText(story.getSignature());
        btnComment = (Button) rootView.findViewById(R.id.btnComment);
        int replace = story.getCommentsCount();
        String text = rootView.getResources().getQuantityString(R.plurals.comments, replace);
        String btnText = String.format(text, replace);
        btnComment.setText(btnText);
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = StoryFragment.this.getActivity();
//                FragmentManager fm = StoryFragment.this.getFragmentManager();
                DialogFragment scdf = new StoryCommentDialogFragment();
                ((StoryCommentDialogFragment) scdf).setStory(story);
                scdf.show(activity.getFragmentManager(), "tag");
            }
        });
        return rootView;
    }
}
