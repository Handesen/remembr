package com.example.geri.remembr;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    String finalString;
    String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/RemembR";
    String gotBackString = null;
    String gotBackEditable = null;
    String gotBackEdit = null;
    EditText edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=(ListView)findViewById(R.id.listi);
        edit = (EditText)findViewById(R.id.editText);

        File dir = new File(path);
        File dirTwo=new File(path+"/savedFile.txt");
        dir.mkdirs();
        if(!dirTwo.exists()){
            StarterSaveOn();
        }
        LoadOn();

        populateListView();
        clickCallBack();
        /**
         * Megkapom a másik Activity-ről átküldött adatot
         */
        try{
            if (savedInstanceState == null) {

                Bundle extras = getIntent().getExtras();
                if(extras == null) {
                    gotBackString= null;
                    gotBackEdit = null;
                    gotBackEditable=null;
                } else {
                        gotBackString= extras.getString("TORLENDO_ITEM");
                        //gotBackEdit = extras.getString("EDIT_ITEM");
                        //gotBackEditable=extras.getString("EDITABLE_ITEM");
                }
            } else {
                gotBackString= (String) savedInstanceState.getSerializable("TORLENDO_ITEM");
                //gotBackEdit = (String)savedInstanceState.getSerializable("EDIT_ITEM");
                //gotBackEditable = (String)savedInstanceState.getSerializable("EDITABLE_ITEM");
            }
        }
        catch (Exception e){

        }
        if(gotBackString != null){
            torles();
        }
        if(gotBackEditable != null){
            modositas();
        }
    }

    private void populateListView() {
        //String[] myItems ={"Blue","Green","Yellow"};
        String[] myItems = finalString.split("\n");

        ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,R.layout.da_item,myItems);

        listView.setAdapter(adapter);
    }

    private void clickCallBack() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                TextView textView = (TextView) viewClicked;
                Intent uj = new Intent(MainActivity.this, Main2Activity.class);
                uj.putExtra("CLICKED_ITEM", textView.getText());
                startActivity(uj);
            }
        });
    }

    public static void Save(File file, String[] data){
        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(file);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();}
        try
        {
            try
            {
                for (int i = 0; i<data.length; i++)
                {
                    fos.write(data[i].getBytes());
                    if (i < data.length-1)
                    {
                        fos.write("\n".getBytes());
                    }
                }
            }
            catch (IOException e) {e.printStackTrace();}
        }
        finally
        {
            try
            {
                fos.close();
            }
            catch (IOException e) {e.printStackTrace();}
        }
    }
    public void StarterSaveOn(){
        File file = new File(path+"/savedFile.txt");
        String[] saveText = {"Konditerem hétfő délután","Vizsga: Hálózati architektúrák, kedd","Beadandó, opkut","Szakdolgozati szeminárium szerda"};
        Save(file, saveText);
    }
    public void SaveOn(){
        File file = new File(path+"/savedFile.txt");
        String saveAble="";
        if(edit.getText() == null) {
            saveAble = finalString;
        }
        else{
            saveAble = finalString+edit.getText();
        }
        String[] saveText = String.valueOf(saveAble).split(System.getProperty("line.separator"));
        edit.setText("");
        Save(file, saveText);
    }
    public void LoadOn(){
        File file = new File(path+"/savedFile.txt");
        if(!file.exists())
        {
            file.mkdirs();
        }
        String[] loadText = Load(file);
        finalString="";
        for (int i=0;i<loadText.length;i++){
            finalString+=loadText[i]+System.getProperty("line.separator");
        }
    }
    public static String[] Load(File file){
        FileInputStream fis = null;
        try
        {
            fis = new FileInputStream(file);
        }
        catch (FileNotFoundException e) {e.printStackTrace();}
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        String test;
        int seged=0;
        try
        {
            while ((test=br.readLine()) != null)
            {
                seged++;
            }
        }
        catch (IOException e) {e.printStackTrace();}

        try
        {
            fis.getChannel().position(0);
        }
        catch (IOException e) {e.printStackTrace();}
        String[] array = new String[seged];
        String line;
        int i = 0;
        try
        {
            while((line=br.readLine())!=null)
            {
                array[i] = line;
                i++;
            }
        }
        catch (IOException e) {e.printStackTrace();}
        return array;
    }
    public void add(View view){
        SaveOn();
        LoadOn();
        populateListView();
    }
    public void torles() {
        String[] asd = finalString.split("\n");
        String newFinalString="";
        for(int i = 0;i<asd.length;i++){
            if(!asd[i].equals(gotBackString)){
                newFinalString+=asd[i]+"\n";

            }
        }
        finalString = newFinalString;
        SaveOn();
        LoadOn();
        populateListView();
    }
    public void modositas(){
        String[] asd = finalString.split("\n");
        String newFinalString="";
        for(int i = 0;i<asd.length;i++){
            if(asd[i].equals(gotBackEditable)){
                asd[i] = gotBackEdit;
                Toast.makeText(MainActivity.this, gotBackEditable,
                        Toast.LENGTH_LONG).show();
            }
            newFinalString+=asd[i]+"\n";
        }
        finalString = newFinalString;
        SaveOn();
        LoadOn();
        populateListView();
    }
}
