package uk.co.rossfenning.android.here2beer;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import uk.co.rossfenning.android.here2beer.model.Pub;

import java.util.LinkedList;
import java.util.List;

public class FavouritesActivity extends ListActivity {

    private LinkedList<Pub> favourites;
    private final FavouritesDAO dao = new FavouritesDAO(this);

    @Override
    public void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        this.favourites = dao.getAllFavourites();

        final ArrayAdapter<Pub> arrayAdapter
            = new FavouritesAdapter(this, android.R.layout.simple_list_item_1, favourites);

        this.setListAdapter(arrayAdapter);

    }

    private class FavouritesAdapter extends ArrayAdapter<Pub> {

        public FavouritesAdapter(Context context, int textViewResourceId, List<Pub> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) FavouritesActivity.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            final View rowView = inflater.inflate(R.layout.row_favourite, parent, false);

            TextView nameView = (TextView) rowView.findViewById(R.id.favourite_name);
            TextView addressView = (TextView) rowView.findViewById(R.id.favourite_address);

            final Pub pub = getItem(position);
        
            nameView.setText(pub.getName());
            addressView.setText(pub.getVicinity());
            
            Button removeButton = (Button) rowView.findViewById(R.id.remove_button);
            
            removeButton.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    dao.removeFromFavourites(pub);
                    FavouritesAdapter.this.remove(pub);
                    FavouritesAdapter.this.notifyDataSetChanged();
                }
            });

            return rowView;
    }
    }
}
