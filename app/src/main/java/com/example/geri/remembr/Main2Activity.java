package com.example.geri.remembr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    String newString;
    TextView textv;
    Button editB;
    EditText editke;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textv=(TextView)findViewById(R.id.textView);
        editB=(Button)findViewById(R.id.editButton);
        editke=(EditText)findViewById(R.id.editText2);
        editke.setVisibility(View.INVISIBLE);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("CLICKED_ITEM");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("CLICKED_ITEM");
        }
        textv.setText(newString);
    }
    public void editButtonClick(View view){
        //Intent inti = new Intent(Main2Activity.this,MainActivity.class);
        //inti.putExtra("EDIT_ITEM",)
        if(editB.getHint().equals("Edit")){
            editke.setVisibility(View.VISIBLE);
            editB.setHint("Enter");
        }
        else if(editB.getHint().equals("Enter")) {
            editke.setVisibility(View.INVISIBLE);
            editB.setHint("Edit");
            Intent inti = new Intent(Main2Activity.this,MainActivity.class);
            inti.putExtra("EDITABLE_ITEM",textv.getText());
            inti.putExtra("EDIT_ITEM",editke.getText());
            startActivity(inti);
        }
    }
    public void delButtonClick(View view){
        Intent inti = new Intent(Main2Activity.this,MainActivity.class);
        inti.putExtra("TORLENDO_ITEM",textv.getText());
        startActivity(inti);
    }
}
