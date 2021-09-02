package com.example.workshopfirebaseodc.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workshopfirebaseodc.FireBaseUtils;
import com.example.workshopfirebaseodc.Models.TaskObject;
import com.example.workshopfirebaseodc.R;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.LinkedList;

public class TaskAdapter  extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {


    private Context context;
    private ArrayList<TaskObject> taskObjectList = new ArrayList<TaskObject>();

    // Constructor
    public TaskAdapter(Context context) {
        this.context = context;
        FireBaseUtils.getFireBaseReference(FireBaseUtils.TASK_COLLECTION)
//                .whereEqualTo("status", true)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot snapshots,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("GETERROR", "listen:error", e);
                            return;
                        }

                        for (DocumentChange dc : snapshots.getDocumentChanges()) {
                            switch (dc.getType()) {
                                case ADDED:
                                    TaskObject taskObjectAdd = new TaskObject(
                                            dc.getDocument().getId(),
                                            dc.getDocument().getString("name"),
                                            dc.getDocument().getString("description"),
                                            dc.getDocument().getBoolean("status")
                                    );
                                    taskObjectList.add(0,taskObjectAdd);
                                    TaskAdapter.this.notifyItemInserted(0);
                                    break;

                                case MODIFIED:
                                    for (int i = 0; i < taskObjectList.size(); i++) {
                                        if(taskObjectList.get(i).getId().equals(dc.getDocument().getId() )){
                                            taskObjectList.get(i).setDescription(dc.getDocument().getString("description"));
                                            taskObjectList.get(i).setName(dc.getDocument().getString("name"));
                                            taskObjectList.get(i).setStatus(dc.getDocument().getBoolean("status"));
                                            TaskAdapter.this.notifyItemChanged(i);
                                        }
                                    }
                                    break;

                                case REMOVED:
                                    for (int i = 0; i < taskObjectList.size(); i++) {
                                        if (taskObjectList.get(i).getId() == dc.getDocument().getId()) {
                                            taskObjectList.remove(i);
                                            TaskAdapter.this.notifyItemRemoved(i);
                                        }
                                    }
                                    break;
                            }
                        }

                    }
                });
    }

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, int position) {


        TaskObject taskObject = taskObjectList.get(position);
        holder.taskName.setText(taskObject.getName());
        holder.description.setText(taskObject.getDescription());
        holder.done.setChecked(taskObject.isStatus());
    }

    @Override
    public int getItemCount() {
        return taskObjectList.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private TextView taskName, description;
        private CheckBox done;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.idTaskName);
            description = itemView.findViewById(R.id.idTaskDescription);
            done = itemView.findViewById(R.id.idTaskDone);
        }
    }
}
