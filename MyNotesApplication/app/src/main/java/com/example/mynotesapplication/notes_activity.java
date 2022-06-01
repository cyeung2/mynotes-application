package com.example.mynotesapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.InputQueue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class notes_activity extends AppCompatActivity {

    EditText title, desc;
    private String title2, desc2;
    private Note n = new Note();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_activity);
        setTitle("Android Notes");
        title = findViewById(R.id.act_title);
        desc = findViewById(R.id.act_desc);

        Intent intent = getIntent();
        if(intent.hasExtra("cl")) {
            n = (Note) intent.getSerializableExtra("cl");
            if (n != null) {
                title.setText(n.getTitle());
                desc.setText(n.getDesc());
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menu) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("You cannot save Without A Title!" + '\n' + "Go back?");
        if (menu.getItemId() == R.id.save) {
            title2 = title.getText().toString();
            if (title2.equals("") || title2 == null) {

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                Intent data = new Intent();
                title2 = title.getText().toString();
                desc2 = desc.getText().toString();
                Note notes = new Note(title2, desc2, "");
                notes.setDate();
                data.putExtra("note", notes);
                setResult(RESULT_OK, data);
                finish();
            }

            return true;

        }
        else
            return super.onOptionsItemSelected(menu);

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("You cannot save Without A Title!" + '\n' + "Go back?");
        title2 = title.getText().toString();
        if (title2.equals("") || title2 == null) {
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
        }
        else {
            builder.setTitle("Your Note is Not Saved!" + '\n' + "Save Note '" + title2 + "'?");
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent data = new Intent();
                    title2 = title.getText().toString();
                    desc2 = desc.getText().toString();
                    Note notes = new Note(title2, desc2, "");
                    notes.setDate();
                    data.putExtra("Note", notes);
                    setResult(RESULT_OK, data);
                    finish();
                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
        }
        AlertDialog dialog = builder.create();
        dialog.show();

    }

}