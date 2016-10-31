package joaogd53.com.br.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import joaogd53.com.br.imageloader.ImageLoader;
import joaogd53.com.br.ourwedding.R;
import joaogd53.com.br.ourweddingapp.model.Guest;

public class GuestsFoldingCellAdapter extends ArrayAdapter<Guest> {
    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private Context context;
    private List<Guest> guests;

    public GuestsFoldingCellAdapter(Context context, List<Guest> guests) {
        super(context, 0, guests);
        this.context = context;
        this.guests = new ArrayList<>();
        for (Iterator<Guest> it = guests.iterator(); it.hasNext(); ) {
            Guest guest = it.next();
            if (guest.getAge() >= 12) {
                this.guests.add(guest);
            }
        }
        Collections.sort(this.guests, new GuestComparator());
    }

    @Override
    public int getCount() {
        return this.guests.size();
    }


    @Override
    @NonNull
    public View getView(final int position, View convertView, ViewGroup parent) {
        // get gift for selected view
        Guest guest;
        try {
            guest = this.guests.get(position);
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
            guest = Guest.GuestBuilder.buildGuest();
        }
        // if cell is exists - reuse it, if not - create the new one from resource
        FoldingCell cell = (FoldingCell) convertView;
        ViewHolder viewHolder;
        if (cell == null) {
            viewHolder = new ViewHolder();
            LayoutInflater vi = LayoutInflater.from(getContext());
            cell = (FoldingCell) vi.inflate(R.layout.cell_small, parent, false);
            viewHolder.giftName = (TextView) cell.findViewById(R.id.title_text_2);
//            viewHolder.stores = (TextView) cell.findViewById(R.id.title_text_4);
            cell.setTag(viewHolder);
        } else {
            // for existing cell set valid valid state(without animation)
            if (unfoldedIndexes.contains(position)) {
                this.unfoldView(cell);
            } else {
                this.foldView(cell);
            }
            viewHolder = (ViewHolder) cell.getTag();
        }
//        RelativeLayout headerImage = (RelativeLayout) cell.findViewById(R.id.headerImage);
        RelativeLayout layout = (RelativeLayout) cell.findViewById(R.id.left_title_part);
        layout.setBackgroundColor(cell.getResources().getColor(R.color.colorSecondary));
        viewHolder.giftName.setText(guest.getPersonName());
//        viewHolder.stores.setText(size.toString());
        ImageView imgGuest = (ImageView) cell.findViewById(R.id.imgSmall);
//        ImageView imgExpanded = (ImageView) cell.findViewById(R.id.expanded_image);
        try {
            ImageLoader il = new ImageLoader(this.context);
            il.displayImage(guest.getPersonPhoto().toString(), imgGuest);
        } catch (NullPointerException ex) {
            imgGuest.setImageResource(R.drawable.ic_menu_camera);
        }
        ImageView statusImage = (ImageView) cell.findViewById(R.id.title_image_1);
        TextView statusText = (TextView) cell.findViewById(R.id.title_text_3);
        switch (guest.getStatus()) {
            case 1:
                statusImage.setImageResource(R.drawable.ic_invite);
                statusText.setText(cell.getResources().getString(R.string.invited));
                break;
            case 2:
                statusImage.setImageResource(R.drawable.ic_confirm_green);
                statusText.setText(cell.getResources().getString(R.string.confirmed));
                break;
            case 3:
                statusImage.setImageResource(R.drawable.ic_cancel);
                statusText.setText(cell.getResources().getString(R.string.not_going));
                break;
            default:
                statusImage.setImageResource(R.drawable.ic_invite);
                statusText.setText(cell.getResources().getString(R.string.invited));
                break;
        }
//        imgGuest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                zoomImageFromThumb(thumb1View, R.drawable.image1);
//            }
//        });
        ImageView menuItem = (ImageView) cell.findViewById(R.id.menuItem);
        menuItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Menu", Toast.LENGTH_SHORT).show();
            }
        });
//        ListView listView = (ListView) cell.findViewById(R.id.contentList);
//        ViewGroup.LayoutParams params = this.buildParams(listView.getLayoutParams(), 5);
//        listView.setLayoutParams(params);
//        listView.requestLayout();
//        final GuestContentAdapter adapter = new GuestContentAdapter(this.getContext(), guest);
//        listView.setAdapter(adapter);
//        final FoldingCell cell = cell;
//        headerImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                cell.toggle(false);
//                // register in adapter that state for selected cell is toggled
//                adapter.notifyDataSetChanged();
//                GuestsFoldingCellAdapter.this.registerToggle(position);
//            }
//        });
        return cell;
    }

    private void foldView(FoldingCell foldingCell) {
//        try {
        foldingCell.fold(true);
//        } catch (ViewHeightToSmall viewHeightToSmall) {
//            foldingCell.resize();
//            this.foldView(foldingCell);
//        }
    }

    private void unfoldView(FoldingCell foldingCell) {
//        try {
        foldingCell.unfold(true);
//        } catch (ViewHeightToSmall viewHeightToSmall) {
//            foldingCell.resize();
//            this.unfoldView(foldingCell);
//        }
    }


    private ViewGroup.LayoutParams buildParams(ViewGroup.LayoutParams layoutParams, int size) {
        int height = 362 * (size - 1);
        layoutParams.height = height;
        return layoutParams;
    }

    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    private void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    private void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    private static class ViewHolder {
        TextView giftName;
//        TextView stores;
    }

    private class GuestComparator implements Comparator<Guest> {
        @Override
        public int compare(Guest g1, Guest g2) {
            return g1.getPersonName().compareToIgnoreCase(g2.getPersonName());
        }
    }

}
