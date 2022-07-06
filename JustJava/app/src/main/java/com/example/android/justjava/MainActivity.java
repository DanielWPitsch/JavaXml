package com.example.android.justjava;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.text.NumberFormat;


// This app displays an order form to order coffe.

public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    int total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //this method is called when the order button is clicked

    public void SubmitOrder(View view){
        EditText nameEditText = (EditText) findViewById(R.id.name_edit_text);
        String nameInput = nameEditText.getText().toString();
        CheckBox creamCheckBox = (CheckBox) findViewById(R.id.cream_checkbox);
        boolean hasCream = creamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        //Log.v("Teste", "has it cream?" +hasCream);
        String order = createOrderSummary(hasCream, hasChocolate, nameInput);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        //intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order to: "+nameInput);
        intent.putExtra(Intent.EXTRA_TEXT, order);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    public String createOrderSummary (boolean addCream, boolean addChocolate, String inputName){
        total = calculatePrice(addCream, addChocolate);
        String orderSummary = (getString(R.string.name)+": "+ inputName);
        orderSummary += ("\n"+getString(R.string.whipped_cream)+ " ? " +addCream);
        orderSummary += ("\n"+getString(R.string.chocolate)+ " ? " + addChocolate);
        orderSummary += ("\n"+getString(R.string.quantity)+ ": "+ quantity);
        orderSummary += ("\n Total: $"+ total);
        orderSummary += ("\n"+ getString(R.string.thank_you));
        return orderSummary;
    }

    /**
     * Calculates the price of the order.
     *
     * @return price
     */
    private int calculatePrice(boolean addCream, boolean addChocolate) {
        if (addCream){
            total += (1 * quantity);
        }
        if (addChocolate){
            total += (2 * quantity);
        }
        total = total + (quantity * 5);
        return total;
    }

    //this method increment the quantity when the button plus is clicked

    public void increment(View view){
        quantity += 1;
        displayQuantity(quantity);
    }

    //this method decrement the quantity when the button minus   is clicked

    public void decrement(View view){
        quantity -= 1;
        if (quantity < 0){
            Toast.makeText(this,"The quantity can't be lesser than 1",Toast.LENGTH_SHORT).show();
            quantity = 1;
        }
        else {
            displayQuantity(quantity);
        }
    }

    //This method displays the given quantity value on the screen.

    @SuppressLint("SetTextI18n")
    private void displayQuantity(int number){
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}