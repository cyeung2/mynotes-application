package com.example.mynotesapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, View.OnLongClickListener{

    private static final int A_REQUEST_CODE = 1;
    private static final int B_REQUEST_CODE = 2;
    private final ArrayList<Note> notes = new ArrayList<>();
    private RecyclerView view1;
    private adapter nAdapter;
    private Note n;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view1 = findViewById(R.id.recyclerV);
        nAdapter = new adapter(this, notes);
        view1.setAdapter(nAdapter);
        view1.setLayoutManager(new LinearLayoutManager(this));
        notes.clear();
        notes.addAll(LoadFile());

        setTitle("Android Notes (" + notes.size() + ")");
    }

    @Override
    protected void onResume() {
        notes.clear();
        notes.addAll(LoadFile());
        setTitle("Android Notes (" + notes.size() + ")");
        super.onResume();


    }

    private ArrayList<Note> LoadFile() {
        ArrayList<Note> noteList= new ArrayList<>();
        try {
            InputStream is = getApplicationContext().openFileInput("notes.json");
            BufferedReader read = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = read.readLine()) != null) {
                sb.append(line);
            }

            JSONArray jsonArray = new JSONArray(sb.toString());
            for (int i=0;i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String title = jsonObject.getString("title");
                String desc = jsonObject.getString("description");
                String date = jsonObject.getString("date");
                Note note = new Note(title, desc, date);
                noteList.add(note);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setTitle("Android Notes (" + notes.size() + ")");
        return noteList;
    }

    private void saveNote() {
        try {
            FileOutputStream fos = getApplicationContext().openFileOutput(getString(R.string.file_name), Context.MODE_PRIVATE);
            PrintWriter pw = new PrintWriter(fos);
            pw.print(notes);
            pw.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opt_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menu) {
        if (menu.getItemId() == R.id.add) {
            Intent intent = new Intent(this, notes_activity.class);
            startActivityForResult(intent, B_REQUEST_CODE);
            return true;
        }
        else if (menu.getItemId() == R.id.info) {
            Intent intent = new Intent(this, info_activity.class);
            startActivity(intent);
            return true;
        }
        else
            return super.onOptionsItemSelected(menu);

    }

    @Override
    public void onPause() {
        saveNote();
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        int pos = view1.getChildLayoutPosition(v);
        Note n = notes.get(pos);
        position = pos;
        Intent intent = new Intent(this, notes_activity.class);
        intent.putExtra("cl", n);
        startActivityForResult(intent, A_REQUEST_CODE);
        notes.remove(pos);
        Toast.makeText(this, "clkd" + n.getTitle(), Toast.LENGTH_LONG).show();
    }


    @Override
    public boolean onLongClick(View v) {
        int pos = view1.getChildLayoutPosition(v);
        Note n = notes.get(pos);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Del Note '" + n.getTitle() + "'?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                notes.remove(pos);
                nAdapter.notifyDataSetChanged();
                saveNote();
                setTitle("Android Notes (" + notes.size() + ")");
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        Toast.makeText(this, "Deleted " + n.getTitle(), Toast.LENGTH_LONG).show();
        AlertDialog dialog = builder.create();
        dialog.show();
        return false;
    }


    @Override
    public void onBackPressed() {
        saveNote();
        super.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == A_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (data == null) {
                    return;
                }
                if (data != null) {
                    n = (Note) data.getSerializableExtra("note");
                    if (n != null) {
                        n.setDate();
                        notes.add(0, n);
                        nAdapter.notifyDataSetChanged();
                        saveNote();
                        setTitle("Android Notes (" + notes.size() + ")");
                    }
                }


            }
        } else if (requestCode == B_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (data == null) {
                    return;
                }
                if (data != null) {
                    n = (Note) data.getSerializableExtra("note");
                    if (n != null) {
                        notes.add(0, n);
                        nAdapter.notifyDataSetChanged();
                        setTitle("Android Notes (" + notes.size() + ")");
                        saveNote();
                        view1.getChildAt(position);
                        setTitle("Android Notes (" + notes.size() + ")");
                    }
                }
            }
        }
    }
}