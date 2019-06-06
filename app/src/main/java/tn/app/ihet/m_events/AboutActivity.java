package tn.app.ihet.m_events;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import tn.app.ihet.m_events.R;

public class AboutActivity extends AppCompatActivity {

    private TextView content ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_about);

        content = findViewById(R.id.content);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        content.setText("L\'Institut des Hautes Etudes Touristiques (IHET) de Sidi Dhrif est un établissement d\'enseignement supérieur et de recherche considéré comme un établissement public à caractère administratif.\n" +
                "Il jouit de la personnalité civile et de l\'autonomie financière. Il est soumis à la co-tutelle du Ministère du Tourisme et du ministère de l\'enseignement supérieur et de la recherche scientifique et de la technologie.\n" +
                "L\'Institut des Hautes Etudes Touristiques de Sidi Dhrif (IHET) est un établissement d\'enseignement et de recherche créé par le décret n°2005-2325 du 22 Août 2005.\n" +
                "Vous pouvez nous contacter pour plus d\'informations\n" +
                "Adresse : Sidi Dhrif - 2026 Sidi Bou Saïd\nTel: (+216) 71.741.466 - 71.741.184\nFax: (+216) 71.775.616\nEmail : ihet@email.ati.tn\nSite Web : www.ihet.rnu.tn\n");


    }
}
