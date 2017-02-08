package rahulkumardas.gopetting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import rahulkumardas.gopetting.models.Item;

public class DetailsActivity extends AppCompatActivity {

    private ImageView image;
    private TextView name, type, startDate, endDate;
    private Button addButton;
    private String nameString, typeString, imageString, dateString, startString;
    private String from = "home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        name = (TextView) findViewById(R.id.name);
        type = (TextView) findViewById(R.id.type);
        startDate = (TextView) findViewById(R.id.startDate);
        endDate = (TextView) findViewById(R.id.endDate);
        image = (ImageView) findViewById(R.id.imageView);
        addButton = (Button) findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                if (from.equals("home")) {
                    if (CartActivity.list == null) {
                        CartActivity.list = new ArrayList<Item>();
                    }
                    ++GoApplication.label;
                    CartActivity.list.add(new Item(nameString, imageString, dateString, startString, typeString));
                    Toast.makeText(DetailsActivity.this, "Item Added to cart", Toast.LENGTH_SHORT).show();
                } else {
                    int pos = getIntent().getIntExtra("pos", 0);
                    --GoApplication.label;
                    CartActivity.list.remove(pos);
                    Toast.makeText(DetailsActivity.this, "1 Item Removed", Toast.LENGTH_SHORT).show();
                    CartActivity.adapter.notifyDataSetChanged();
                    CartActivity.count.setText(CartActivity.list.size()+"");
                }

            }
        });

        Intent i = getIntent();

        if (i.getStringExtra("from").equals("home")) {
            addButton.setText("Add to cart");
            from = "home";
        } else {
            addButton.setText("Remove from cart");
            from = "cart";
        }
        nameString = i.getStringExtra("name");
        typeString = i.getStringExtra("type");
        dateString = i.getStringExtra("date");
        imageString = i.getStringExtra("image");
        startString = i.getStringExtra("startDate");

        Log.i("Rahul", "name" + nameString);
        Log.i("Rahul", "type" + typeString);
        Log.i("Rahul", "date" + dateString);
        Log.i("Rahul", "start" + startString);

        name.setText(nameString);
        type.setText("Type : " + typeString);
        startDate.setText("Start Date : " + startString);
        endDate.setText("End  Date : " + dateString);
        Glide.with(this).load(imageString)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image);
    }
}
