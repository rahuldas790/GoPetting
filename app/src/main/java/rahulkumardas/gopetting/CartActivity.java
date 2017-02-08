package rahulkumardas.gopetting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rahulkumardas.gopetting.adapter.CartAdapter;
import rahulkumardas.gopetting.models.Item;

public class CartActivity extends AppCompatActivity {

    public static List<Item> list;
    private ListView listView;
    public static TextView count, noItem;
    public static CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        noItem = (TextView)findViewById(R.id.noItem);
        if(list==null){
            noItem.setVisibility(View.VISIBLE);
            list = new ArrayList<Item>();
        }else if (list.size()==0){
            noItem.setVisibility(View.VISIBLE);
        }else{
            noItem.setVisibility(View.GONE);
        }
        listView = (ListView) findViewById(R.id.list);
        count = (TextView) findViewById(R.id.total);
        count.setText(list.size()+"");
        adapter = new CartAdapter(this, list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(CartActivity.this, DetailsActivity.class);
                intent.putExtra("from", "cart");
                intent.putExtra("pos", i);
                intent.putExtra("name", list.get(i).name);
                intent.putExtra("image", list.get(i).image);
                intent.putExtra("type", list.get(i).type);
                intent.putExtra("startDate", list.get(i).startDate);
                intent.putExtra("date", list.get(i).date);
                startActivity(intent);
            }
        });
    }
}
