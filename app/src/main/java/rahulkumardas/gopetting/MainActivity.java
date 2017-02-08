package rahulkumardas.gopetting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import rahulkumardas.gopetting.adapter.ItemAdapter;
import rahulkumardas.gopetting.models.Item;
import rahulkumardas.gopetting.network.ApiInterface;
import rahulkumardas.gopetting.network.RequestServerAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static rahulkumardas.gopetting.GoApplication.mGoogleApiClient;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<Item> list;
    private ListView listView;
    private ItemAdapter adapter;
    private ProgressBar bar;
    private CircleImageView personPhoto;
    private TextView personName, personEmail;
    private TextView label;
    private RelativeLayout rlCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        bar = (ProgressBar) findViewById(R.id.progressBar);

        list = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("name", list.get(i).name);
                intent.putExtra("image", list.get(i).image);
                intent.putExtra("type", list.get(i).type);
                intent.putExtra("startDate", list.get(i).startDate);
                intent.putExtra("date", list.get(i).date);
                intent.putExtra("from", "home");
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                return true;
            }
        });


        label = (TextView) toolbar.findViewById(R.id.label);
        rlCart = (RelativeLayout)toolbar.findViewById(R.id.rlCart);
        rlCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
            }
        });

        View headerLayout = navigationView.getHeaderView(0);
        personPhoto = (CircleImageView) headerLayout.findViewById(R.id.imageView);
        personName = (TextView) headerLayout.findViewById(R.id.personName);
        personEmail = (TextView) headerLayout.findViewById(R.id.email);
        personName.setText(getIntent().getStringExtra("name"));
        personEmail.setText(getIntent().getStringExtra("email"));
        Glide.with(this).load(getIntent().getStringExtra("url"))
                .thumbnail(0.5f)
                .crossFade()
                .placeholder(R.drawable.ic_default)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(personPhoto);
        loadData();
        adapter = new ItemAdapter(this, list);

    }

    @Override
    protected void onResume() {
        if (GoApplication.label == 0) {
            label.setVisibility(View.INVISIBLE);
        } else {
            label.setVisibility(View.VISIBLE);
            label.setText(GoApplication.label + "");
        }
        super.onResume();
    }

    private void loadData() {
        ApiInterface apiService =
                RequestServerAPI.getClient().create(ApiInterface.class);

        Call<JsonObject> call = apiService.getData();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonArray arr = response.body().getAsJsonArray("data");
                String name, image, date, type, startDate;
                for (int i = 0; i < arr.size(); i++) {
                    JsonObject item = arr.get(i).getAsJsonObject();
                    name = item.get("name").getAsString();
                    image = item.get("icon").getAsString();
                    date = item.get("endDate").getAsString();
                    type = item.get("objType").getAsString();
                    startDate = item.get("startDate").getAsString();
                    list.add(new Item(name, image, date, startDate, type));
                }
                bar.setVisibility(View.GONE);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            startActivity(new Intent(this, CartActivity.class));
        } else if (id == R.id.nav_send) {

            while (!mGoogleApiClient.isConnected()) {

            }
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(Status status) {
                            if (status.isSuccess()) {
                                finish();
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            }
                        }
                    });

        } else
            Toast.makeText(this, "Feature not Available!", Toast.LENGTH_SHORT).show();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
