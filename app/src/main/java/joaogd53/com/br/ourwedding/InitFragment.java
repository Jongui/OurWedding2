package joaogd53.com.br.ourwedding;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import joaogd53.com.br.customViews.DirectionView;

public class InitFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_init,
                container, false);
        DirectionView dviewInit = (DirectionView) rootView.findViewById(R.id.dview_init);
        dviewInit.setQuery("Rua Amazonas, 986 - Água Verde, Curitiba - PR");
        return rootView;
    }
}
