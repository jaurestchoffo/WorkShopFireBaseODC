package com.example.workshopfirebaseodc;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.workshopfirebaseodc.Models.TaskObject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;

public class FireBaseUtils {

     static CollectionReference refCollection;
    public static final String TASK_COLLECTION = "Task";

    public static CollectionReference getFireBaseReference(String collectionName){

        if(refCollection == null)
        {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            refCollection = db.collection(collectionName);
            return refCollection;
        }
        return refCollection;
    }

    public static void addTask(TaskObject taskObject){
        FireBaseUtils.getFireBaseReference(TASK_COLLECTION)
                .add(taskObject)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("SUCCESS", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("FAILD", "Error adding document", e);
                    }
                });
    }

}
