package joaogd53.com.br.customViews;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;

import joaogd53.com.br.dialog.StoryCommentDialogFragment;
import joaogd53.com.br.ourwedding.StoryFragment;
import joaogd53.com.br.ourweddingapp.model.Story;

public class StoryCardView extends CardView implements View.OnClickListener {

    private Story story;

    public StoryCardView(Context context) {
        super(context);
    }

    public StoryCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StoryCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public void setStory(Story story) {
        this.story = story;
    }

    @Override
    public void onClick(View v) {
        Activity activity = (Activity) this.getContext();
        DialogFragment scdf = new StoryCommentDialogFragment();
        ((StoryCommentDialogFragment) scdf).setStory(this.story);
        scdf.show(activity.getFragmentManager(), "tag");
    }
}
