/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * <p>
 * package com.example.android.justjava;
 */

package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    final int PRICE_PER_CUP = 5;
    final int PRICE_PER_WHIPPED_CREAM = 1;
    final int PRICE_PER_CHOCOLATE = 2;
    int quantity = 2;
    boolean hasWhippedCream = false;
    boolean hasChocolate = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(getApplicationContext(), getString(R.string.more_than_100), Toast.LENGTH_SHORT).show();
            return;
        }

        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(getApplicationContext(), getString(R.string.less_than_1), Toast.LENGTH_SHORT).show();
            return;
        }

        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        WhippedCreamCheckBox();
        ChocolateCheckBox();
        int price = calculatePrice();
        String summary = createOrderSummary(price);

        composeEmail(getString(R.string.justjava_order, customerName()), summary);
      //  displayMessage(summary);
    }


    /**
     * @return the price of the order.
     */
    private int calculatePrice() {
        int fullPricePerCup = PRICE_PER_CUP;
        if (hasWhippedCream) {
            fullPricePerCup += PRICE_PER_WHIPPED_CREAM;
        }
        if (hasChocolate) {
            fullPricePerCup += PRICE_PER_CHOCOLATE;
        }
        int fullPrice;
        fullPrice = fullPricePerCup * quantity;
        return fullPrice;
    }


    /**
     * @param totalPrice calculated total price of the order
     * @return order summary
     */

    private String createOrderSummary(int totalPrice) {

        String YesNoWhippedCream;
        String YesNoChocolate;

        if (hasWhippedCream) {
            YesNoWhippedCream = getString(R.string.yes);
        } else {
            YesNoWhippedCream = getString(R.string.no);
        }

        if (hasChocolate) {
            YesNoChocolate = getString(R.string.yes);
        } else {
            YesNoChocolate = getString(R.string.no);
        }


        String summaryText = getString(R.string.customer_name, customerName());
        summaryText += "\n" + getString(R.string.whipped_cream_check, YesNoWhippedCream);
        summaryText += "\n" + getString(R.string.chocolate_check, YesNoChocolate);
        summaryText += "\n" + getString(R.string.quantity_check, quantity);
        summaryText += "\n" + getString(R.string.total_check, totalPrice);
        summaryText += "\n" + getString(R.string.thank_you);
        return summaryText;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCups) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCups);
    }

    /**
     * This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }


    //This method checks if the checkbox for whipped cream is checked.

    private void WhippedCreamCheckBox() {
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox_whipped_cream);
        hasWhippedCream = checkBox.isChecked();

    }

    //This method checks if the checkbox for chocolate topping is checked.

    private void ChocolateCheckBox() {
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox_chocolate);
        hasChocolate = checkBox.isChecked();

    }

    //this method gets text from the EditText for name

    private String customerName() {
        EditText nameBar = (EditText) findViewById(R.id.edit_text_name);
        String name;
        name = nameBar.getText().toString();
        return name;

    }

    //creates intent for sending order info through email

    public void composeEmail(String subject, String messageText) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, messageText);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }



}