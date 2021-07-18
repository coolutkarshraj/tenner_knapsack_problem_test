package com.io.knapsack_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.io.knapsack_test.MaxValByDynamicProg.getMaxValByDynamicProg;
import static com.io.knapsack_test.MaxValueByRecursion.maxValByRecursion;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.etKnapsackWeight)
    EditText etKnapsackWeight;
    @BindView(R.id.etObjectWeight)
    EditText etObjectWeight;
    @BindView(R.id.etObjectVal)
    EditText etObjectVal;
    @BindView(R.id.tvKnapSackValue)
    TextView tvKnapSackValue;
    @BindView(R.id.toolbarTop)
    Toolbar toolbarTop;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    String message = "";
    long timeElapsed =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    /*
     * Method For Initializing
     * Toolbar,And Our View .
     * Initializing ButterKnife Library
     * Setting text to toolbar
     * */
    private void initView() {
        Window window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        // finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.notification_bar));
        ButterKnife.bind(this);
        toolbar_title.setText(R.string.toolbar_text);
    }

    /*
     * All click event are performed here
     * Click for get Max Value by Dynamic Programming
     * Click for getting Max Value by recursion.
     * All events handle inside this method.
     * */
    @OnClick({R.id.btnKnapSackZeroOne, R.id.btnKnapSackGreedy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnKnapSackZeroOne:
                if(checkValidation()){
                    formatDataForResult(false);
                }else {
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.btnKnapSackGreedy:
                if(checkValidation()){
                    formatDataForResult(true);
                }else {
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    private boolean checkValidation() {
        boolean isValid = true;
        if(etKnapsackWeight.getText().toString().trim().length() < 1){
            isValid = false;
            message = getResources().getString(R.string.enter_bag_weight);

        }else if(etObjectWeight.getText().toString().trim().length() < 1){
            message = getResources().getString(R.string.enter_multiple_weight);
            isValid = false;
        }
        else if(etObjectVal.getText().toString().trim().length() < 1){
            message = getResources().getString(R.string.multiple_val);
            isValid = false;
        }else {
            isValid = true;
        }
       return isValid;
    }


    private void formatDataForResult(boolean isRecursion) {
        timeElapsed = 0;
        long startTime = System.nanoTime();
        String knapsackWeight = etKnapsackWeight.getText().toString().trim().replace(" ","");
        String listObject = etObjectWeight.getText().toString().trim().replace(" ","");
        String listObjectVal = etObjectVal.getText().toString().trim().replace(" ","");
        String[] strArr = listObject.split(",");
        int[] intArr = new int[strArr.length];
        for (int i = 0; i < strArr.length; i++) {

            try {
                String num = strArr[i];
                intArr[i] = Integer.parseInt(num);
            }catch (Exception e){
                Toast.makeText(this, "Please enter valid number don't give input in decimal format.", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        String[] strArr1 = listObjectVal.split(",");

        int[] intArr1 = new int[strArr1.length];
        for (int i = 0; i < strArr1.length; i++) {
            try {
                String num = strArr1[i];
                intArr1[i] = Integer.parseInt(num);
            }catch (Exception e){
                Toast.makeText(this, "Please enter valid number don't give input in decimal format.", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if(strArr.length == strArr1.length){
         try {
             long endTime = System.nanoTime();
             timeElapsed = endTime - startTime;
             if(isRecursion){
                 tvKnapSackValue.setText("Max Value: "+String.valueOf(maxValByRecursion(Integer.
                         valueOf(knapsackWeight), intArr, intArr1, intArr1.length))+ "Time taken to " +
                         "execute program : " + timeElapsed + "Nano sec");
             }else {
                 tvKnapSackValue.setText("Max Value: "+String.valueOf(getMaxValByDynamicProg(Integer.
                         valueOf(knapsackWeight), intArr, intArr1, intArr1.length))+ "Time taken to " +
                         "execute program : " + timeElapsed + "Nano sec");
             }
         }catch (Exception e){
             e.printStackTrace();
             Toast.makeText(this, "Please enter valid number don't give input in decimal format.", Toast.LENGTH_SHORT).show();
         }
        }else{
            Toast.makeText(this, getResources().getString(R.string.weight_value_wrong_input), Toast.LENGTH_SHORT).show();
        }

    }


}