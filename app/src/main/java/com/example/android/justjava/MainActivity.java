package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    int quantity = 2;

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //Figure out if the user wants whipped Cream
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkBox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        //Figure Out If User Needs Chocolate Topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkBox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        //Getting input name from user
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();
        Log.d(TAG , "Name:  " + name);

        int price = calculatePrice(hasWhippedCream , hasChocolate);

        String priceMessage = createOrderSummary(price , hasWhippedCream ,hasChocolate ,name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT,"Just Java Order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private String createOrderSummary(int price, boolean addWhippedCream , boolean addChocolate , String name){
        String priceMessage = "\nName: " + name;
        priceMessage += "\nAdd whipped cream? " + addWhippedCream;
        priceMessage += "\nAdd chocolate? " + addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: Rs" + price * quantity;
        priceMessage += "\nThank you!";
        return priceMessage;
    }


    /**
     * Calculates the price of the order.
     * @param addChocolate is whether or not user wants chocolate topping
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     */
    private int calculatePrice(boolean addWhippedCream , boolean addChocolate) {
        int basePrice = 50;
        if (addWhippedCream){
            basePrice += 10;
        }
        if (addChocolate){
            basePrice += 20;
        }
        return basePrice;
    }

    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this,"You cannot have more than 100 coffees",Toast.LENGTH_SHORT).show();
            return ;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }
    public void decrement(View view) {
        if(quantity == 1){
            Toast.makeText(this,"You cannot have less than 1 coffees",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);

    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}