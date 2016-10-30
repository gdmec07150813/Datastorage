package com.example.mifanjun.datastoragedemo;

import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.TooManyListenersException;

public class MainActivity extends AppCompatActivity {
    private EditText edt1,edt2;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edt1 = (EditText) findViewById(R.id.username);
        edt2 = (EditText) findViewById(R.id.password);
        tv = (TextView) findViewById(R.id.show);
    }
    public void SPwrite(View v){
        SharedPreferences sp = getSharedPreferences("user",0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("name",edt1.getText().toString());
        editor.putString("password",edt2.getText().toString());
        editor.commit();
        Toast.makeText(this,"SPwrite",Toast.LENGTH_LONG).show();
    }
    public void SPread(View v){
        SharedPreferences sp = getSharedPreferences("user",0);
        String name=sp.getString("name","");
        String password=sp.getString("password","");
        tv.setText("name:  "+name+"   password:"+password);
        Toast.makeText(this,"SPread",Toast.LENGTH_LONG).show();
    }
    public  void ROMwrite(View v){
        String name = edt1.getText().toString();
        String password = edt2.getText().toString();
        try {
            FileOutputStream fop = openFileOutput("user.txt", MODE_APPEND);
            OutputStreamWriter ops = new OutputStreamWriter(fop);
            BufferedWriter bw = new BufferedWriter(ops);
            bw.write("name "+name+"  password "+password);
            bw.flush();
            Toast.makeText(this,"ROMwrite",Toast.LENGTH_LONG).show();
            fop.close();
            ops.close();
            bw.close();
        }catch (Exception e){

        }
    }
    public void ROMread(View v){
        String name = "";
        String password = "";
        try {
            FileInputStream fip = openFileInput("user.txt");
            InputStreamReader isr = new InputStreamReader(fip);
            BufferedReader br = new BufferedReader(isr);
            StringBuffer sb = new StringBuffer();
            String s;
            while((s=br.readLine())!=null){
                sb.append(s);
            }
            tv.setText(sb);
            fip.close();
            Toast.makeText(this,"ROMread",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.printStackTrace();

        }
    }
    public void SDwrite(View v){
        String str = edt1.getText().toString()+"  "+edt2.getText().toString();
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        String fileName = path+"/test.txt";
        File file = new File(fileName);
        try {
            FileOutputStream fop = new FileOutputStream(file);
            fop.write(str.getBytes());
            fop.flush();
            fop.close();
            Toast.makeText(this,"sdwrite", Toast.LENGTH_LONG).show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void SDread(View v){
        String path  = Environment.getExternalStorageDirectory().getAbsolutePath();
        String fileName = path+"/test.txt";
        File file = new File(fileName);
        int length = (int)file.length();
        byte[] b = new byte[length];
        try{
        FileInputStream fip = new FileInputStream(file);
        fip.read(b,0,length);
            fip.close();
            tv.setText(new String(b));
            Toast.makeText(this,"sdread",Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
