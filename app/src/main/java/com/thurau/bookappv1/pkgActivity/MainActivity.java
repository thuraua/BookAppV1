package com.thurau.bookappv1.pkgActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.thurau.bookappv1.R;
import com.thurau.bookappv1.pkgData.Book;
import com.thurau.bookappv1.pkgData.Database;

public class MainActivity extends AppCompatActivity {

    private EditText txtAddress;
    private EditText txtId;
    private EditText txtTitle;
    private EditText txtAuthor;
    private Spinner spBooks;
    private ArrayAdapter<Book> adapterBooks;
    private TextView txtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getAllViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Database db = Database.newInstance(txtAddress.getText().toString());
        int bookId = Integer.parseInt(txtId.getText().toString());
        String title = txtTitle.getText().toString();
        String author = txtAuthor.getText().toString();
        try {
            switch (id) {
                case R.id.mitem_get:

                    Book book = db.getBook(bookId);
                    txtMessage.setText(book.toString());
                    break;
                case R.id.mitem_list:

                    break;
                case R.id.mitem_insert:
                    Book book1 = new Book(bookId, title, author);
                    db.insertBook(book1);
                    break;
                case R.id.mitem_update:
                    Book book2 = new Book(bookId, title, author);
                    db.insertBook(book2);
                    break;
                case R.id.mitem_delete:

                    break;
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Exception in main activity: " + ex.getMessage(), Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getAllViews() {
        txtAddress = findViewById(R.id.txtAddress);
        txtId = findViewById(R.id.txtId);
        txtTitle = findViewById(R.id.txtTitle);
        txtAuthor = findViewById(R.id.txtAuthor);
        spBooks = findViewById(R.id.spBooks);
        txtMessage = findViewById(R.id.txtMessage);
        adapterBooks = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
    }
}