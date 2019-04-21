package com.amazonaws.youruserpoolspropert;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amazonaws.youruserpoolspropert.CognitoYourUserPoolsDemo.R;

public class TransferActivity extends AppCompatActivity {

    private EditText AssetID;
    private EditText Recipientname;
    private EditText RecipientID;
    private TextView resultTextView;

    private Button Transfer;
    private String assetIDString,RecipientIDString,RecipientNameString;
    private int statusCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        resultTextView=(TextView)findViewById(R.id.resultTextView);
        resultTextView.setVisibility(View.VISIBLE);
        resultTextView.setText("");

        AssetID=(EditText)findViewById(R.id.editTextLandId);
        AssetID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length() == 0) {
                    TextView label = (TextView) findViewById(R.id.textViewRegUserIdLabel);
                    label.setText(AssetID.getHint());
                    AssetID.setBackground(getDrawable(R.drawable.text_border_selector));
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    TextView label = (TextView) findViewById(R.id.textViewRegUserIdLabel);
                    label.setText("");
                }
            }
        });


        Recipientname=(EditText)findViewById(R.id.editTextRegUserPassword);
        Recipientname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.length() == 0) {
                    TextView label = (TextView) findViewById(R.id.textViewRegUserPasswordLabel);
                    label.setText(Recipientname.getHint());
                    Recipientname.setBackground(getDrawable(R.drawable.text_border_selector));
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 0) {
                    TextView label = (TextView) findViewById(R.id.textViewRegUserPasswordLabel);
                    label.setText("");
                }
            }
        });

        RecipientID = (EditText) findViewById(R.id.editTextRegGivenName);
        RecipientID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length() == 0) {
                    TextView label = (TextView) findViewById(R.id.textViewRegGivenNameLabel);
                    label.setText(RecipientID.getHint());
                    RecipientID.setBackground(getDrawable(R.drawable.text_border_selector));
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    TextView label = (TextView) findViewById(R.id.textViewRegGivenNameLabel);
                    label.setText("");
                }
            }
        });

        Transfer=(Button)findViewById(R.id.transfer);
        Transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assetIDString=AssetID.getText().toString();
                RecipientIDString=RecipientID.getText().toString();
                RecipientNameString=Recipientname.getText().toString();
                resultTextView.setText("Land with Id "+AssetID.getText()+" successfully transfered to "+Recipientname.getText()+" having ID:"+RecipientID.getText());


                statusCode = HttpUtils.TransferOwnership(assetIDString,RecipientIDString,RecipientNameString);
                if(statusCode!=200) {
                    resultTextView.setText("fail");

                } else {
                    resultTextView.setText("Land with Id "+AssetID.getText()+
                            " successfully transfered to "+Recipientname.getText()+" having ID:"+RecipientID.getText());
                }
            }
        });


    }
}
