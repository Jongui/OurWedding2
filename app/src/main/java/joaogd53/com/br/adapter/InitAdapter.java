package joaogd53.com.br.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import joaogd53.com.br.customViews.DirectionView;
import joaogd53.com.br.ourwedding.R;

public class InitAdapter extends RecyclerView.Adapter<InitAdapter.InitAdapterViewHolder> {

    private static final int INIT_CARD = 0;
    private static final int DIRECT_CARD = 1;

    @Override
    public InitAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        switch (viewType) {
            case InitAdapter.INIT_CARD:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.init_card_view, parent, false);
                break;
            case InitAdapter.DIRECT_CARD:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.church_direction, parent, false);
                break;
            default:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.church_direction, parent, false);
                break;
        }
        return new InitAdapterViewHolder(v);
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return InitAdapter.INIT_CARD;
            case 1:
                return InitAdapter.DIRECT_CARD;
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(InitAdapterViewHolder holder, int position) {
        switch (position) {
            case InitAdapter.INIT_CARD:
                break;
            case InitAdapter.DIRECT_CARD:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class InitAdapterViewHolder extends RecyclerView.ViewHolder {
        private DirectionView dviewInit;

        public InitAdapterViewHolder(View view) {
            super(view);
            dviewInit = (DirectionView) view.findViewById(R.id.churchDirection);
            if (dviewInit != null) {
                dviewInit.setQuery("Rua Amazonas, 986 - Água Verde, Curitiba - PR");
            }
        }
    }
}
