package com.sbpm.documentscanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import io.card.payment.CardIOActivity;
import static io.card.payment.CardIOActivity.RESULT_SCAN_SUPPRESSED;
public class MainActivity extends AppCompatActivity {

    private static final int MY_SCAN_REQUEST_CODE = 3;
    ImageView img ;
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        img = findViewById(R.id.img);
        btn= findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onScanPress(v);

            }
        });


    }

    // Metodo que llama card.io
    public void onScanPress(View v) {

        Intent scanIntent = new Intent(this, CardIOActivity.class);
        scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_SCAN,true); // supmit cuando termine de reconocer el documento
        scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY,true); // esconder teclado
        scanIntent.putExtra(CardIOActivity.EXTRA_USE_CARDIO_LOGO,true); // cambiar logo de paypal por el de card.io
        scanIntent.putExtra(CardIOActivity.EXTRA_RETURN_CARD_IMAGE,true); // capture img
        scanIntent.putExtra(CardIOActivity.EXTRA_CAPTURED_CARD_IMAGE,true); // capturar img

        // laszar activity
        startActivityForResult(scanIntent, MY_SCAN_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

          if (requestCode == MY_SCAN_REQUEST_CODE || requestCode== RESULT_SCAN_SUPPRESSED) {
            // RESULT_SCAN_SUPPRESSED
            // se lanza cuando se reconoce la img pero no se procesan datos.

            // tomar img
            // la img no queda almacenada en el dispositivo
            Bitmap card = CardIOActivity.getCapturedCardImage(data);
            img.setImageBitmap(card);

        }
    }


}
