package com.example.firestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private TextView quoteView;
    private TextView authorView;
    private TextView mQuoteTextView;
    private static final String QUOTE_KEY = "quote" ;
    private static final String AUTHOR_KEY = "author";
    private DocumentReference mDocRef = FirebaseFirestore.getInstance().document("general pool (to be city)/ specific pool (to be combined latitude and longitude ");











@Override
    protected void onStart()
    {
        super.onStart();
        mDocRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists())
                {
                    String quoteText = documentSnapshot.getString(QUOTE_KEY);
                    String authorText=documentSnapshot.getString(AUTHOR_KEY);
                    mQuoteTextView.setText("\""+quoteText+"\""+authorText);
                }else if(e!=null)
                {
                //    Log.w(TAG,"exception!");
                }
            }
        });

    }




    public void fetchQuote(View view)

    {
        mDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    String quotetText = documentSnapshot.getString(QUOTE_KEY);
                    String authorText= documentSnapshot.getString(AUTHOR_KEY);
                    mQuoteTextView.setText("\""+quotetText+"\""+authorText);
                }
            }
        });
    }









    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       mQuoteTextView = findViewById(R.id.textViewInspiring);
    }






















    public void saveQuote(View view)
    {

          quoteView =(EditText) findViewById(R.id.editTextQuote);
          authorView = (EditText) findViewById(R.id.editTextAuthor);
        String quoteText = quoteView.getText().toString();
        String authorText=authorView.getText().toString();
        if(quoteText.isEmpty()||authorText.isEmpty())
        {
            return;

        }

        ArrayList<Double> dataToSave = new ArrayList<>();


      dataToSave.put(AUTHOR_KEY,authorText);
      dataToSave.put(QUOTE_KEY,quoteText);






























        mDocRef.update(dataToSave).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG","SUCCESS");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
              Log.w("TAG","FAILED");
            }
        });
    }
}
