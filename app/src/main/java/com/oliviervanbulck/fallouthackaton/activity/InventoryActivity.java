package com.oliviervanbulck.fallouthackaton.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.oliviervanbulck.fallouthackaton.R;
import com.oliviervanbulck.fallouthackaton.application.FalloutApplication;
import com.oliviervanbulck.fallouthackaton.model.Item;
import com.oliviervanbulck.fallouthackaton.service.FalloutService;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class InventoryActivity extends AppCompatActivity {

    @Bind(R.id.listview) ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        setTitle(R.string.inventory);
        setTitle(getTitle() + " (" + ((FalloutApplication)getApplication()).getCoins() + " munten)");
        ButterKnife.bind(this);

        FalloutService service = ((FalloutApplication) getApplication()).getService();
        String auth = ((FalloutApplication) getApplication()).getSession();

        Call<List<Item>> call = service.getInventory(auth);
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Response<List<Item>> response, Retrofit retrofit) {
                if(response.code() == 200) {
                    List<Item> items = response.body();
                    final StableArrayAdapter adapter = new StableArrayAdapter(InventoryActivity.this,
                            R.layout.list_item, items);
                    listView.setAdapter(adapter);
                }
                else {
                    Context context = getApplicationContext();
                    CharSequence text = "Er ging iets fout tijdens het laden...";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_inventory, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        private List<Item> items;
        private Context context;

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<Item> objects) {
            super(context, -1, new String[objects.size()]);
            items = objects;
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.list_item, parent, false);
            TextView nameView = (TextView) rowView.findViewById(R.id.name);
            TextView priceView = (TextView) rowView.findViewById(R.id.price);
            TextView countView = (TextView) rowView.findViewById(R.id.count);

            nameView.setText(items.get(position).getName());
            priceView.setText("Prijs: " + items.get(position).getValue());
            countView.setText("Aantal: " + items.get(position).getCount());

            return rowView;
        }

    }

}
