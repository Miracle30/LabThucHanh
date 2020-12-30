package a1711062183.lab3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.TabHost;
import android.app.TabActivity;
import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import android.widget.AdapterView;

import a1711062183.Restaurant.Restaurant;



public class MainActivity extends TabActivity{

//    private Restaurant r = new Restaurant();
    private List<Restaurant> resList = new ArrayList<Restaurant>();
//    private ArrayAdapter<Restaurant> adapter = null;
    RestaurantAdapter adapter = null;

    private AdapterView.OnItemClickListener onListClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Restaurant r = resList.get(position);
            edtName = findViewById(R.id.edtName);
            edtAdd = findViewById(R.id.edtAdd);
            rgType = findViewById(R.id.rgType);

            edtName.setText(r.getName());
            edtAdd.setText(r.getAddress());
            if(r.getType().equals(("Sit down"))){
                rgType.check(R.id.rdbSitDown);
            }
            else if(r.getType().equals(("Take out"))){
                rgType.check(R.id.rdbTakeOut);
            }
            else if(r.getType().equals(("Delivery"))){
                rgType.check(R.id.rdbDelivery);
            }

            //chuyển view về tab details ben phải
            getTabHost().setCurrentTab(1);
        }
    };

    Button btnSave;
    EditText edtName, edtAdd;
    RadioGroup rgType;
    ListView lvRes;

    public class RestaurantAdapter extends ArrayAdapter<Restaurant> {
        public RestaurantAdapter(@NonNull Context context, int resource) {
            super(context, resource);
        }
        public RestaurantAdapter(){
            super(MainActivity.this, android.R.layout.simple_list_item_1, resList);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View row = convertView;
            if(row == null){
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(R.layout.row, null);

            }
            Restaurant r = resList.get(position);
            ImageView icon = row.findViewById(R.id.icon);

            ((TextView)row.findViewById(R.id.title)).setText(r.getName());
            ((TextView)row.findViewById(R.id.address)).setText(r.getAddress());

            String type = r.getType();
            if (type.equals("Take out")){
                icon.setImageResource(R.drawable.takeout);
            }
            else if (type.equals("Sit down")){
                icon.setImageResource(R.drawable.sitdown);
            }
            else if (type.equals("Delivery")){
                icon.setImageResource(R.drawable.delivery);
            }

            return row;
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(onSave);
        //lab5
        lvRes = findViewById(R.id.lvRes);
        //lab7
        lvRes.setOnItemClickListener(onListClick);

//        adapter = new ArrayAdapter<Restaurant>(this, android.R.layout.simple_list_item_1, resList);
        adapter = new RestaurantAdapter();
        lvRes.setAdapter(adapter);

        //thêm cho tab host
        TabHost.TabSpec spec = getTabHost().newTabSpec("tag1");
        spec.setContent(R.id.lvRes);
        spec.setIndicator("List", getResources().getDrawable(R.drawable.ic_baseline_list_24));
        getTabHost().addTab(spec);

        spec = getTabHost().newTabSpec("tag2");
        spec.setContent(R.id.details);
        spec.setIndicator("Details", getResources().getDrawable(R.drawable.ic_baseline_restaurant_24));
        getTabHost().addTab(spec);

        getTabHost().setCurrentTab(0);

    }

    private View.OnClickListener onSave = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Restaurant r = new Restaurant();
            edtName = findViewById(R.id.edtName);
            edtAdd = findViewById(R.id.edtAdd);
            rgType = findViewById(R.id.rgType);

            r.setName(edtName.getText().toString());
            r.setAddress(edtAdd.getText().toString());

            switch (rgType.getCheckedRadioButtonId()){
                case R.id.rdbTakeOut:
                    r.setType("Take out");
                    break;
                case R.id.rdbSitDown:
                    r.setType("Sit down");
                    break;
                case R.id.rdbDelivery:
                    r.setType("Delivery");
                    break;
            }
//                    Log.d(r.getName(), "Tên");
//                    Log.d(r.getAddress(), "Địa chỉ");
//                    Log.d(r.getType(), "Loại của checkbox");
//            Toast.makeText(getApplicationContext(),r.getName()+" "+ r.getAddress()+" "+ r.getType() ,
//                    Toast.LENGTH_LONG).show();

            resList.add(r);
            edtName.getText().clear();
            edtAdd.getText().clear();
            rgType.clearCheck();
            edtName.requestFocus();
            getTabHost().setCurrentTab(0);
            //Log.d(r.toString(),"mảng");
        }
    };
}