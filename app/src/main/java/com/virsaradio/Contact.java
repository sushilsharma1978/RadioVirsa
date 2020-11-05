package com.virsaradio;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by khushdeep-android on 10/4/18.
 */

public class Contact extends AppCompatActivity implements View.OnClickListener
{
    TextView tv_email;
    ImageView iv_back_arrow;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us);
        Init();
        ClickListener();
    }

    private void ClickListener()
    {
        tv_email.setOnClickListener(this);
        iv_back_arrow.setOnClickListener(this);
    }

    private void Init() {
        tv_email=(TextView)findViewById(R.id.tv_email);
        iv_back_arrow=(ImageView)findViewById(R.id.iv_back_arrow);
    }

    @Override
    public void onClick(View view) {
        if(view==tv_email)
        {

            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "radiovirsanz@gmail.com", null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
            startActivity(Intent.createChooser(emailIntent, null));
        }
        if(view==iv_back_arrow)
        {
            Intent intent=new Intent(Contact.this,MainActivity.class);
            startActivity(intent);
        }

    }
}
