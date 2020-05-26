package com.example.piinterface;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class MainActivity extends AppCompatActivity implements SettingsFragment.Settings_Dialog_Listener {
// Variablen ----------------------------------------------------
    static String ipadress = "";
    static int portnumber = 0;
    ImageView connStat;
    android.support.v7.widget.Toolbar toolbar;
    boolean connected;
    Switch grapSwitch;
    // own classes
    SharedPrefs sharedPreferences;
    Connection connection;
    //--------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        sharedPreferences = new SharedPrefs(this);
        setContentView(R.layout.activity_main);
        connStat = findViewById(R.id.CONNSTST);
        toolbar = findViewById(R.id.my_toolbar);
        grapSwitch = findViewById(R.id.grapSwitch);
        setSupportActionBar(toolbar);
        connected = false;
        Load();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SettingsFragment settingsFragment = new SettingsFragment();
        settingsFragment.show(getSupportFragmentManager(), "My Dialog");

        return super.onOptionsItemSelected(item);
    }
//---------------------------------------------------------------------

    // Interface Dialog
    @Override
    public void applySettings(String ip, String port) {
        ipadress = ip;
        if (port.equals("")) {
            portnumber = 0;
        } else {
            portnumber = Integer.parseInt(port);
        }
        Toast.makeText(this, "Data Saved " + " IP: " + ipadress + " | Port: " + portnumber, Toast.LENGTH_SHORT).show();
    }

    public void Load() {
        ipadress = sharedPreferences.getIP();
        portnumber = sharedPreferences.getPort();
        Toast.makeText(this, "ip: " + ipadress + " port: " + portnumber, Toast.LENGTH_SHORT).show();
    }

    public void initConnection(View v) {
        connection = new Connection(ipadress, portnumber);

        if (connected == false) {   //connection not opend

            if(connection.open()) {  //establish the connection. if successfull return true
                connected = true;
                connStat.setBackgroundResource(R.drawable.ic_connected_24dp);
            }
            else{
                System.out.println(Toast.makeText(this, "Server nicht erreichbar", Toast.LENGTH_SHORT));
            }
        }
        else {
            try {
                connection.close();
                connected = false;
                connStat.setBackgroundResource(R.drawable.ic_disconnected_24dp);

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // Buttons OnClick ----------------------------------------------------
    public void OnClickUp(View v) {
        if (connected) {
            connection.send(1);
        }
        else{
            Toast.makeText(this,"Server nicht verbunden", Toast.LENGTH_SHORT).show();
            grapSwitch.setChecked(false);
        }
    }

    public void OnClickTowards(View v) {
        if (connected) {
            connection.send(2);
        }
        else{
            Toast.makeText(this,"Server nicht verbunden", Toast.LENGTH_SHORT).show();
            grapSwitch.setChecked(false);
        }
    }

    public void OnClickAway(View v) {
        if (connected) {
            connection.send(3);
        }
        else{
            Toast.makeText(this,"Server nicht verbunden", Toast.LENGTH_SHORT).show();
            grapSwitch.setChecked(false);
        }
    }

    public void OnClickDown(View v) {
        if (connected) {
            connection.send(4);
        }
        else{
            Toast.makeText(this,"Server nicht verbunden", Toast.LENGTH_SHORT).show();
            grapSwitch.setChecked(false);
        }
    }

    public void GrapMethod(View view) {

        if(grapSwitch.isChecked()==false){
            if (connected) {
                connection.send(6);
            }
            else{
                Toast.makeText(this,"Server nicht verbunden", Toast.LENGTH_SHORT).show();
                grapSwitch.setChecked(false);
            }
        }
        else{
            if (connected) {
                connection.send(5);
            }
            else{
                Toast.makeText(this,"Server nicht verbunden", Toast.LENGTH_SHORT).show();
                grapSwitch.setChecked(false);
            }
        }

    }

    public void OnClickCirlceLeft(View v) {
        if (connected) {
            connection.send(7);
        }
        else{
            Toast.makeText(this,"Server nicht verbunden", Toast.LENGTH_SHORT).show();
            grapSwitch.setChecked(false);
        }
    }

    public void OnClickCirlceRight(View v) {
        if (connected) {
            connection.send(8);
        }
        else{
            Toast.makeText(this,"Server nicht verbunden", Toast.LENGTH_SHORT).show();
            grapSwitch.setChecked(false);
        }
    }
// ----------------------------------------------------------------------

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // socket wieder freigeben
        connection.close();
    }


}
