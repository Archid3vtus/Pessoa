package com.nada.yuri.pessoa;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    static Pessoa pessoa;
    static boolean canSendWhatsapp = false;
    static boolean canSendEmail = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonClick((Button) findViewById(R.id.buttonSubmit), new Pessoa());


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(canSendWhatsapp){
            sendWhatsapp();
            canSendWhatsapp = false;
        }

        if(canSendEmail){
            sendEmail();
            canSendEmail = false;
        }
    }

    private void buttonClick(Button b, final Pessoa p){
        b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                storePessoa(p);
            }
        });
    }

    private void storePessoa(Pessoa p){

        EditText editNome = (EditText) findViewById(R.id.editName);
        EditText editPhone = (EditText) findViewById(R.id.editPhone);
        EditText editEmail = (EditText) findViewById(R.id.editEmail);
        EditText editAddress = (EditText) findViewById(R.id.editAddress);

        p.setNome(editNome.getText().toString());
        p.setTelefone(editPhone.getText().toString());
        p.setEmail(editEmail.getText().toString());
        p.setEndereco(editAddress.getText().toString());




        if(TextUtils.isEmpty(p.getNome()) || TextUtils.isEmpty(p.getTelefone()) || TextUtils.isEmpty(p.getEmail()) || TextUtils.isEmpty(p.getEndereco())){
            Context context = getApplicationContext();
            CharSequence text = "Há campos em branco!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }else{
            this.pessoa = p;
            startContactsIntent();
        }

    }

    private void startContactsIntent(){
        // Creates a new Intent to insert a contact
        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
        // Sets the MIME type to match the Contacts Provider
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);

        intent.putExtra(ContactsContract.Intents.Insert.NAME, pessoa.getNome());
        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, pessoa.getEmail());
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, pessoa.getTelefone());
        intent.putExtra(ContactsContract.Intents.Insert.POSTAL, pessoa.getEndereco());

        startActivity(intent);

        canSendWhatsapp = true;
    }


    public void sendWhatsapp(){
        try {
            String text = "Você foi adicionado com sucesso à lista de contatos de Yuri Braga";// Replace with your message.

            String toNumber = pessoa.getTelefone(); // Replace with mobile phone number without +Sign or leading zeros.


            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+toNumber +"&text="+text));
            startActivity(intent);
            canSendEmail = true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendEmail(){
        Intent i = new Intent(Intent.ACTION_SENDTO);
        i.setType("message/rfc822");
        i.setData(Uri.parse("mailto:"));
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{pessoa.getEmail()});
        i.putExtra(Intent.EXTRA_SUBJECT, "Adicionado com sucesso");
        i.putExtra(Intent.EXTRA_TEXT   , "Você foi adicionado com sucesso à lista de contatos de Yuri Braga");
        startActivity(Intent.createChooser(i, "Send mail..."));
    }
}
