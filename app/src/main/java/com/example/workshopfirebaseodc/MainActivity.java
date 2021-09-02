package com.example.workshopfirebaseodc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.workshopfirebaseodc.Adapter.TaskAdapter;
import com.example.workshopfirebaseodc.Models.TaskObject;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView taskRecyclerView;
    private TaskAdapter taskAdapter;
    private FloatingActionButton addButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = findViewById(R.id.floating_button_add);
        // Get a handle to the RecyclerView.
        taskRecyclerView = findViewById(R.id.taskRecyclerview);
        // Create an adapter and supply the data to be displayed.
        taskAdapter = new TaskAdapter(this);
        // Connect the adapter with the RecyclerView.
        taskRecyclerView.setAdapter(taskAdapter);
        // Give the RecyclerView a default layout manager.
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Use the Builder class for convenient dialog construction
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                View inflater = getLayoutInflater().inflate(R.layout.add_task_dialog, null);
                builder.setView(inflater);
                builder.setPositiveButton(R.string.ajouter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                        EditText nom = inflater.findViewById(R.id.nom_tache);
                        EditText description = inflater.findViewById(R.id.taskDescription);

                        TaskObject taskObject = new TaskObject(nom.getText().toString(), description.getText().toString() );
                        FireBaseUtils.addTask(taskObject);


                    }
                })
                        .setNegativeButton(R.string.annuler, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                // Create the AlertDialog object and return it
                builder.create();
                builder.show();
            }
        });

    }
}