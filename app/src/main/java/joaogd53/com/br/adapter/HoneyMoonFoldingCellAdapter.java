package joaogd53.com.br.adapter;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ramotion.foldingcell.FoldingCell;

import java.util.HashSet;
import java.util.List;

import joaogd53.com.br.dialog.QuotaInfoDialog;
import joaogd53.com.br.formatters.Formaters;
import joaogd53.com.br.imageloader.ImageLoader;
import joaogd53.com.br.ourwedding.R;
import joaogd53.com.br.ourweddingapp.model.HoneyMoonGift;

/**
 * Created by root on 13/09/16.
 */
public class HoneyMoonFoldingCellAdapter extends ArrayAdapter<HoneyMoonGift> {
    private Context context;
    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private List<HoneyMoonGift> gifts;

    public HoneyMoonFoldingCellAdapter(Context context, List<HoneyMoonGift> gifts) {
        super(context, 0, gifts);
        this.gifts = gifts;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        // get gift for selected view
        final HoneyMoonGift gift = this.gifts.get(position);
        // if cell is exists - reuse it, if not - create the new one from resource
        FoldingCell temp = (FoldingCell) convertView;
        ViewHolder viewHolder;
        if (temp == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            temp = (FoldingCell) vi.inflate(R.layout.cell, parent, false);
            // binding view parts to view holder
            viewHolder.giftDescription = (TextView) temp.findViewById(R.id.title_text_2);
//            viewHolder.stores = (TextView) cell.findViewById(R.id.title_text_4);
            viewHolder.contentRequestBtn = (TextView) temp.findViewById(R.id.content_request_btn);
            viewHolder.lblStorePhone = (TextView) temp.findViewById(R.id.lbl_text_1);
            viewHolder.txtPrice = (TextView) temp.findViewById(R.id.lbl_text_2);
//            viewHolder.lblStoresCount = (TextView) cell.findViewById(R.id.lblStoresCount);
//            viewHolder.desiredLevel = (TextView) cell.findViewById(R.id.title_text_6);
            temp.setTag(viewHolder);
        } else {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                temp.unfold(true);
            } else {
                temp.fold(true);
            }
            viewHolder = (ViewHolder) temp.getTag();
        }
        viewHolder.contentRequestBtn.setText(temp.getResources().getString(R.string.buyQuota));
        viewHolder.contentRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("idHoneyMoonGift", gift.getIdHoneyMoonGift());
                DialogFragment df = new QuotaInfoDialog();
                df.setArguments(bundle);
                Activity activity = (Activity) HoneyMoonFoldingCellAdapter.this.context;
                df.show(activity.getFragmentManager(), "tag");
            }
        });
        viewHolder.giftDescription.setText(gift.getDescription());
        viewHolder.txtPrice.setText(Formaters.formatCurrency(gift.getTotalValue()));
        ListView listView = (ListView) temp.findViewById(R.id.contentList);
        HoneyMoonContentAdapter adapter = new HoneyMoonContentAdapter(this.getContext(), gift);
        listView.setAdapter(adapter);
        temp.initialize(1000, R.color.bgBackSideColor, 3);
        final FoldingCell cell = temp;
        RelativeLayout headerImage = (RelativeLayout) temp.findViewById(R.id.headerImage);
        headerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cell.toggle(false);
//         register in adapter that state for selected temp is toggled
                HoneyMoonFoldingCellAdapter.this.registerToggle(position);
            }
        });
        ImageView imgLarge = (ImageView) temp.findViewById(R.id.imgLarge);
        try {
            ImageLoader il = new ImageLoader(this.context);
            il.displayImage(gift.getLargeImage().toString(), imgLarge);
        } catch (NullPointerException ex) {

        }
        ImageView imgSmall = (ImageView) temp.findViewById(R.id.imgSmall);
        try {
            ImageLoader il = new ImageLoader(this.context);
            il.displayImage(gift.getSmallImage().toString(), imgSmall);
        } catch (NullPointerException ex) {

        }
        return cell;
    }

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
        TextView txtPrice;
        TextView contentRequestBtn;
        TextView lblStorePhone;
        //        TextView lblStoresCount;
        //        TextView desiredLevel;
        TextView giftDescription;
//        TextView stores;
    }
}