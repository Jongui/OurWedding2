package joaogd53.com.br.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import joaogd53.com.br.adapter.StoryCommentAdapter;
import joaogd53.com.br.ourwedding.R;
import joaogd53.com.br.ourweddingapp.application.OurWeddingApp;
import joaogd53.com.br.ourweddingapp.model.Story;
import joaogd53.com.br.ourweddingapp.model.StoryComment;
import joaogd53.com.br.sweetalertdialog.SweetAlertDialog;

public class StoryCommentDialogFragment extends DialogFragment {
    private Story story;

    public StoryCommentDialogFragment() {

    }

    public void setStory(Story story) {
        this.story = story;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.story_comment_dialog_fragment, container);
        ListView commentListView = (ListView) view.findViewById(R.id.commentListView);
        final TextView txtComment = (TextView) view.findViewById(R.id.txtComment);
        final StoryCommentAdapter adapter = new StoryCommentAdapter(this.getActivity(), this.story);
        commentListView.setAdapter(adapter);
        Button btnSend = (Button) view.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = story.getComments().size() + 1;

                String text = txtComment.getText().toString();
                txtComment.setText("");
                if (text.length() > 200) {
                    new SweetAlertDialog(view.getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(view.getContext().getResources().getString(R.string.comment_to_long))
                            .setContentText(view.getResources().getString(R.string.comment_limit))
                            .show();
                    return;
                }
                if (text.equalsIgnoreCase("")) {
                    new SweetAlertDialog(view.getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(view.getContext().getResources().getString(R.string.comment_text_empty))
                            .show();
                    return;
                }
                StoryComment comment = StoryComment.StoryCommentBuilder.buildNewComment(story, position, text,
                        OurWeddingApp.getInstance().getUser());
                int returnCode = OurWeddingApp.getInstance().saveComment(comment);
                if (returnCode == 0) {
                    story.getComments().add(comment);
                    new SweetAlertDialog(view.getContext(), SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText(view.getContext().getResources().getString(R.string.comment_saved))
                            .show();
                    adapter.notifyDataSetChanged();
                } else {
                    new SweetAlertDialog(view.getContext(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText(view.getContext().getResources().getString(R.string.comment_not_save))
                            .setContentText(view.getResources().getString(R.string.comment_internal_error))
                            .show();
                }
            }
        });
        return view;
    }
}
