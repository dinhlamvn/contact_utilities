package android.vn.lcd.activity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.lcd.vn.capnhatdanhba.R;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.vn.lcd.sql.ContactHelper;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txtContacts;
    Button btnLoadContact;
    ContactHelper contactHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactHelper = new ContactHelper(getApplicationContext());
        btnLoadContact = (Button) findViewById(R.id.load_contact);
        txtContacts = (TextView) findViewById(R.id.text_contact_list);

        btnLoadContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadContactList();
            }
        });
    }

    void loadContactList() {

        StringBuilder sb = new StringBuilder();
        ContentResolver contentResolver = getContentResolver();
        Cursor contactCursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if (contactCursor != null && contactCursor.moveToFirst()) {
            do {
                String id = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.RawContacts._ID));
                String name = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                int hasPhoneNumber = Integer.parseInt(contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));

                if (hasPhoneNumber > 0) {
                    Cursor phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                            ,null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[] {id}, null);

                    if (phoneCursor != null && phoneCursor.moveToFirst()) {

                        do {
                            String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            String phoneType = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                            sb.append("Contact: ").append(name).append(", Phone number: ").append(phoneNumber).append("\n\n");

                            if (name.equals("Vy 14QVP")) {
                                String newPhoneNumber = phoneNumber + "00000";
                                contactHelper.updateContactPhoneNumber(getContentResolver(), Long.valueOf(id), Integer.valueOf(phoneType), newPhoneNumber);
                            }
                        } while (phoneCursor.moveToNext());
                    }
                }
            } while (contactCursor.moveToNext());


            contactCursor.close();
        }

        txtContacts.setText(sb.toString());
    }
}
