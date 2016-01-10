package com.ferdikt.arduinodriver;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter BA;
    private Set<BluetoothDevice> pairedDevices;
    ListView lv;
    String address;
    ArrayList<Car> carList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BA = BluetoothAdapter.getDefaultAdapter();
        if (!BA.isEnabled()) {
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, 0);
            Toast.makeText(getApplicationContext(),"Turned on",Toast.LENGTH_LONG).show();
        }

        BA = BluetoothAdapter.getDefaultAdapter();
        lv = (ListView)findViewById(R.id.listView);

        pairedDevices = BA.getBondedDevices();
        carList = new ArrayList();
        for(BluetoothDevice bt : pairedDevices) {
            carList.add(new Car(bt.getName(),bt.getAddress()));
        }

        lv.setAdapter(new CarsAdapter(getApplicationContext(),carList));
        lv.setOnItemClickListener(new listviewClickListener());

        Button connectToCar = (Button)findViewById(R.id.connect_to_car);

        connectToCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (address!= null){
                    Intent i = new Intent(MainActivity.this,ConnectToCar.class);
                    i.putExtra("address",address);
                    startActivity(i);
                }
            }
        });

        Button refresh = (Button)findViewById(R.id.refresh);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (address!= null){
                    lv.setOnItemClickListener(new listviewClickListener());
                }
            }
        });

    }

    public class listviewClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (carList != null) {
                address = carList.get(position).address;
            }
        }
    }

    public class CarsAdapter extends ArrayAdapter<Car> {
        public CarsAdapter(Context context, ArrayList<Car> cars) {
            super(context, 0, cars);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Car car = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_car, parent, false);
            }
            // Lookup view for data population
            TextView carName = (TextView) convertView.findViewById(R.id.name);
            TextView carAddress = (TextView) convertView.findViewById(R.id.address);
            // Populate the data into the template view using the data object
            carName.setText(car.name);
            carAddress.setText(car.address);
            // Return the completed view to render on screen
            return convertView;
        }
    }

}

