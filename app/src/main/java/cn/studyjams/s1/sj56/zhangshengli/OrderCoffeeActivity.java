package cn.studyjams.s1.sj56.zhangshengli;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Vic on 2016/4/21 0021.
 */
public class OrderCoffeeActivity extends BaseActivity {
    private String STATE_QUANTITY = "quantity";
    private String STATE_ORDER = "order";
    @Bind(R.id.et)
    EditText et;
    @Bind(R.id.check1)
    CheckBox check1;
    @Bind(R.id.check2)
    CheckBox check2;
    @Bind(R.id.tvQuantity)
    TextView tvQuantity;
    @Bind(R.id.tvPrice)
    TextView tvPrice;
    private int price = 5;
    private int creamPrice = 1;
    private int chocolatePrice = 2;
    private int quantity = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        reload(savedInstanceState);
    }

    private void reload(Bundle state) {
        if (state == null)
            return;
        tvQuantity.setText(String.valueOf(quantity = state.getInt(STATE_QUANTITY)));
        tvPrice.setText(state.getString(STATE_ORDER));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coffee;
    }

    public void increase(View view) {
        if (quantity < 100)
            quantity++;
        else
            Toast.makeText(OrderCoffeeActivity.this, R.string.moreTip, Toast.LENGTH_SHORT).show();
        tvQuantity.setText(String.valueOf(quantity));
    }

    public void decrease(View view) {
        if (quantity > 1)
            quantity--;
        else
            return;
        tvQuantity.setText(String.valueOf(quantity));
    }

    public void order(View view) {
        int tmpTotal = 0;
        if (check1.isChecked())
            tmpTotal += creamPrice * quantity;
        if (check2.isChecked())
            tmpTotal += chocolatePrice * quantity;
        tmpTotal += price * quantity;
        String total = NumberFormat.getCurrencyInstance().format(tmpTotal);
        StringBuilder sb = new StringBuilder();
        sb.append(getString(R.string.javaName)).append(et.getText().toString());
        sb.append("\n").append(getString(R.string.addCream)).append(check1.isChecked() ? getString(R.string.yes) : getString(R
                .string.no));
        sb.append("\n").append(getString(R.string.addChocolate)).append(check2.isChecked() ? getString(R.string.yes) :
                getString(R.string.no));
        sb.append("\n").append(getString(R.string.javaQuantity)).append(quantity);
        sb.append("\n").append(getString(R.string.total)).append(total);
        sb.append("\n").append(getString(R.string.thank));
        tvPrice.setText(sb.toString());
        String[] array = {"order@qq.com"};
        composeEmail(array, getString(R.string.coffeeOrder), sb.toString());
    }

    public void composeEmail(String[] addresses, String subject, String text) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_QUANTITY, quantity);
        outState.putString(STATE_ORDER, tvPrice.getText().toString());
        super.onSaveInstanceState(outState);
    }
}