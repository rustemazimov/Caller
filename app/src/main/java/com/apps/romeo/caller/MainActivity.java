package com.apps.romeo.caller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    private ToggleButton onOffToggleButton;
    private EditText inputEditText;
    private Button callButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Request permision for Calling someone
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.CALL_PHONE},
                1);

        //Initialize GUI companents
        initCompanents();

        //When on-off button's state was changed
        onOffToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onOffToggleButton.isChecked())
                {
                    //Show all companents
                    specifyVisibilityCompanents(true);
                }
                else
                {
                    //Hide all companents
                    specifyVisibilityCompanents(false);
                }
            }
        });

        //Handle call button's action
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call(inputEditText.getText().toString());
            }
        });
    }
    private void initCompanents(){
        //Initialize fields for using user input when user wants to perform the call
        onOffToggleButton = (ToggleButton) findViewById(R.id.on_off_togglebutton);
        inputEditText = (EditText) findViewById(R.id.input_edittext);
        callButton = (Button) findViewById(R.id.call_button);
    }
    /*
     * Shows or Hides components depend on flag's value
     * @param flag
     *        specifies whether the mehod will show or hide components
     */
    private void specifyVisibilityCompanents(boolean flag){
        int state = flag ? Button.VISIBLE : Button.INVISIBLE;
        inputEditText.setVisibility(state);
        callButton.setVisibility(state);
    }
    /*
     * Calls a phone number
     * @param number
     *        specifies which number will be called
     */
    private void call(String number){
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            showMessage("Permission must be given for calling someone");
            return;
        }
        startActivity(callIntent);

    }
    /*
     * Shows some txt at the bottom of the screen
     * for a little time
     * @param txt
     *        specifies that which text will be shown
     */
    private void showMessage(String txt){
        Toast.makeText(getApplicationContext(), txt, Toast.LENGTH_LONG).show();
    }
    /*
     * Are executed when the program launched
     * and user answers to permission request
     * Finally this method checks user accepted or denied to permission
     * @param permissions
     *        set of user permissions that are requested
     * @param grantResults
     *        set of all user permissions' result states
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    showMessage("Permission denied to call someone");
                }
            }
            // permissions this app might request
        }
    }
}
