package joaogd53.com.br.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ramotion.foldingcell.FoldingCell;

import java.util.HashSet;
import java.util.List;

import joaogd53.com.br.ourwedding.R;
import joaogd53.com.br.ourweddingapp.model.Store;

public class StoresFoldingCellAdapter extends ArrayAdapter<Store> {
    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private List<Store> stores;

    public StoresFoldingCellAdapter(Context context, List<Store> stores) {
        super(context, 0, stores);
        this.stores = stores;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        // get gift for selected view
        final Store store = this.stores.get(position);
        // if temp is exists - reuse it, if not - create the new one from resource
        FoldingCell temp = (FoldingCell) convertView;
        ViewHolder viewHolder;
        if (temp == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            temp = (FoldingCell) vi.inflate(R.layout.cell, parent, false);
            // binding view parts to view holder
            viewHolder.storeName = (TextView) temp.findViewById(R.id.title_text_2);
            viewHolder.contentRequestBtn = (TextView) temp.findViewById(R.id.content_request_btn);
            viewHolder.lblStorePhone = (TextView) temp.findViewById(R.id.lbl_text_1);
            temp.setTag(viewHolder);
        } else {
            // for existing temp set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                this.unfoldView(temp);
            } else {
                this.foldView(temp);
            }
            viewHolder = (ViewHolder) temp.getTag();
        }
        viewHolder.lblStorePhone.setText(store.getPhone());
        viewHolder.contentRequestBtn.setText(temp.getResources().getString(R.string.gifts_list));
        RelativeLayout headerImage = (RelativeLayout) temp.findViewById(R.id.headerImage);
        // bind data from selected element to view through view holder
        viewHolder.storeName.setText(store.getName());
        viewHolder.contentRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(store.getUrl()));
                view.getContext().startActivity(browserIntent);

            }
        });
        ImageView menuItem = (ImageView) temp.findViewById(R.id.menuItem);
        menuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Menu", Toast.LENGTH_SHORT).show();
            }
        });
        ImageView imgLarge = (ImageView) temp.findViewById(R.id.imgLarge);
        imgLarge.setImageResource(store.getImgLarge());
        ImageView imgSmall = (ImageView) temp.findViewById(R.id.imgSmall);
        imgSmall.setImageResource(store.getImgSmall());
        ListView listView = (ListView) temp.findViewById(R.id.contentList);
//        ViewGroup.LayoutParams params = this.buildParams(listView.getLayoutParams(), temp.getHeight());
//        listView.setLayoutParams(params);
//        listView.requestLayout();
        StoresContentAdapter adapter = new StoresContentAdapter(this.getContext(), store);
        listView.setAdapter(adapter);
        temp.initialize(1000, R.color.bgBackSideColor, 3);
        final FoldingCell cell = temp;
        headerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cell.toggle(false);
//         register in adapter that state for selected temp is toggled
                StoresFoldingCellAdapter.this.registerToggle(position);
            }
        });
        return cell;
    }

    private void foldView(FoldingCell foldingCell) {
        foldingCell.fold(true);
    }

    private void unfoldView(FoldingCell foldingCell) {
        foldingCell.unfold(true);
    }

//    private ViewGroup.LayoutParams buildParams(ViewGroup.LayoutParams layoutParams, int height) {
//        return layoutParams;
//    }

    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    // View lookup cache
    private static class ViewHolder {
        //        TextView price;
        TextView contentRequestBtn;
        TextView lblStorePhone;
        //        TextView lblStoresCount;
        //        TextView desiredLevel;
        TextView storeName;
//        TextView stores;
    }

}
