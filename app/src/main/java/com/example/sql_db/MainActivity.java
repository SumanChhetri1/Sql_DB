package com.example.sql_db;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText txtFname,txtLname,txtMarks, txt_id;
    Button btnInsert,btnShow,btnUpdate,btnDelete;
    DatabaseHelper mdb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        mdb=new DatabaseHelper(this);
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean inserted= mdb.insertData(txtFname.getText().toString().trim(),
                        txtLname.getText().toString().trim(),
                        txtMarks.getText().toString().trim());
                if(inserted=true){
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Data not Inserted!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cur =mdb.getAllData();
                if(cur.getCount()==0){
                    Toast.makeText(MainActivity.this, "Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (cur.moveToNext()) {
                    buffer.append("id" + cur.getString(0) + "\n");
                    buffer.append("firstname" + cur.getString(1) + "\n");
                    buffer.append("lastname" + cur.getString(2) + "\n");
                    buffer.append("marks" + cur.getString(3) + "\n");
                }
                show("Data",buffer.toString());

            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdate=mdb.UpdateData(txt_id.getText().toString(),
                        txtFname.getText().toString(),txtLname.getText().toString(),
                        txtMarks.getText().toString());
                if(isUpdate==true){
                    Toast.makeText(MainActivity.this, "Data Update",
                            Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Data Not Update",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer deleterow= mdb.deleteData(txt_id.getText().toString());
                if (deleterow>0){
                    Toast.makeText(MainActivity.this, "Data Delete", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(MainActivity.this, "Data Not Delete", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void init(){
        txtFname=findViewById(R.id.et_firstname);
        txtLname=findViewById(R.id.et_lastname);
        txt_id=findViewById(R.id.et_id);
        txtMarks=findViewById(R.id.et_marks);
        btnInsert=findViewById(R.id.btn_insert);
        btnShow=findViewById(R.id.btn_view);
        btnUpdate=findViewById(R.id.btn_update);
        btnDelete=findViewById(R.id.btn_delete);

    }
    public void show(String title , String Message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}