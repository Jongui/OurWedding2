package joaogd53.com.br.ourwedding;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import joaogd53.com.br.adapter.InitAdapter;
import joaogd53.com.br.customViews.DirectionView;
import joaogd53.com.br.customViews.DividerItemDecoration;

public class InitFragment extends Fragment {

    private RecyclerView rvwInit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_init,
                container, false);
        rvwInit = (RecyclerView) rootView.findViewById(R.id.rvwInit);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this.getActivity());
        rvwInit.setLayoutManager(mLayoutManager);
//        rvwInit.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        rvwInit.setItemAnimator(new DefaultItemAnimator());
        rvwInit.setAdapter(new InitAdapter());
        rvwInit.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        return rootView;
    }
}
