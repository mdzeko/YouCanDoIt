package foi.youcandoit;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GlavnaAktivnost extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.glavna_aktivnost);
        Button gmbPlaniraj = (Button) findViewById(R.id.gmbPlaniraj);
        Button gmbPrati = (Button) findViewById(R.id.gmbPrati);
        Button gmbPovijest = (Button) findViewById(R.id.gmbPovijest);
        gmbPlaniraj.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				Intent prikaziMapu = new Intent(getApplicationContext(), AktivnostPlaniraj.class);
				startActivity(prikaziMapu);
			}
		});
        gmbPrati.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				Intent prikaziMapu = new Intent(getApplicationContext(), AktivnostPrati.class);
				startActivity(prikaziMapu);
			}
		});
        gmbPovijest.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				Intent prikaziMapu = new Intent(getApplicationContext(), AktivnostPovijest.class);
				startActivity(prikaziMapu);
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.glavna_aktivnost, menu);
        return true;
    }
    
}
