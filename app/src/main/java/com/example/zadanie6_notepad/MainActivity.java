package com.example.zadanie6_notepad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    ImageButton btn_save;
    ImageButton btn_load;
    ImageButton btn_delete;
    EditText txt_file_name;
    EditText txt_title;
    EditText txt_content;

    String local_path = Environment.getExternalStorageDirectory().getAbsolutePath();

    File path = new File(local_path);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_save = (ImageButton) findViewById(R.id.btn_save);
        btn_load = (ImageButton) findViewById(R.id.btn_load);
        btn_delete = (ImageButton) findViewById(R.id.btn_delete);
        txt_file_name = (EditText) findViewById(R.id.txt_file_name);
        txt_title = (EditText) findViewById(R.id.txt_title);
        txt_content = (EditText) findViewById(R.id.txt_content);
    }

    public void saveText(View view) {
        String fileName = txt_file_name.getText().toString()+".txt";
        String title_to_file = txt_title.getText().toString();
        String content_to_file = txt_content.getText().toString();

        if (fileName.matches(".txt")) {
            Toast.makeText(this, "Wprowadź nazwę pliku", Toast.LENGTH_SHORT).show();
            return;
        } else if (title_to_file.matches("")) {
            Toast.makeText(this, "Wprowadź tytuł notatki", Toast.LENGTH_SHORT).show();
            return;
        } else if (content_to_file.matches("")) {
            Toast.makeText(this, "Wprowadź treść notatki", Toast.LENGTH_SHORT).show();
            return;
        }

        String data_to_file = title_to_file + "\n" + content_to_file;

        try {
            File save_to_file = new File(path, fileName);
            FileWriter writer = new FileWriter(save_to_file);
            writer.append(data_to_file);
            writer.flush();
            writer.close();
            Toast.makeText(this, "Zapisano w: " + local_path + fileName, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Błąd: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void loadFile(View view) {
        txt_title.getText().clear();
        txt_content.getText().clear();

        String fileName = txt_file_name.getText().toString()+".txt";
        if (fileName.matches(".txt")) {
            Toast.makeText(this, "Wprowadź nazwę pliku", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            File file_to_load = new File(path, fileName);
            if(!file_to_load.exists()){
                Toast.makeText(this,"Plik nie istnieje.",Toast.LENGTH_LONG).show();
            }
            else {
                FileReader reader = new FileReader(file_to_load);
                BufferedReader bufReader = new BufferedReader(reader);
                StringBuilder sb = new StringBuilder();
                String title, line;

                title = bufReader.readLine();

                txt_title.setText(title);

                while ((line = bufReader.readLine()) != null)
                {
                    sb.append(line).append("\n");
                }

                txt_content.setText(sb.toString());

                bufReader.close();

                Toast.makeText(this, "Wczytano " + fileName, Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Błąd: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void deleteFile(View view) {
        String fileName = txt_file_name.getText().toString()+".txt";
        if (fileName.matches(".txt")) {
            Toast.makeText(this, "Wprowadź nazwę pliku", Toast.LENGTH_SHORT).show();
            return;
        }

        File file_to_delete = new File(path, fileName);
        if(!file_to_delete.exists()){
            Toast.makeText(this,"Plik nie istnieje.",Toast.LENGTH_LONG).show();
        }
        else {
            file_to_delete.delete();
            Toast.makeText(this, "Usunięto " + fileName, Toast.LENGTH_SHORT).show();
        }
    }
}
