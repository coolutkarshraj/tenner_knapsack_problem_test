# Sample android app
Sample app demonstrating video playback using the vdocipher android sdk

### Add dependency

```
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
    long timeElapsed = 0;

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
                if (checkValidation()) {
                    formatDataForResult(false);
                } else {
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.btnKnapSackGreedy:
                if (checkValidation()) {
                    formatDataForResult(true);
                } else {
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    /*
     * Method to check Validation of all
     * the input text value
     * whether it is empty or not
     * if empty any field return false
     * otherwise true
     * */
    private boolean checkValidation() {
        boolean isValid = true;
        if (etKnapsackWeight.getText().toString().trim().length() < 1) {
            isValid = false;
            message = getResources().getString(R.string.enter_bag_weight);

        } else if (etObjectWeight.getText().toString().trim().length() < 1) {
            message = getResources().getString(R.string.enter_multiple_weight);
            isValid = false;
        } else if (etObjectVal.getText().toString().trim().length() < 1) {
            message = getResources().getString(R.string.multiple_val);
            isValid = false;
        } else {
            isValid = true;
        }
        return isValid;
    }

    /*
    *  Calculate time lapsed to execute program 
     * Common Method to format the
     * input in knapsack Problem
     * convert object weight in array
     * convert object value in array
     * then calculating max profit class call
     * showing result in textview
     * */
    private void formatDataForResult(boolean isRecursion) {
        timeElapsed = 0;
        long startTime = System.nanoTime();
        //getting value from edittext
        String knapsackWeight = etKnapsackWeight.getText().toString().trim().replace(" ", "");
        String listObject = etObjectWeight.getText().toString().trim().replace(" ", "");
        String listObjectVal = etObjectVal.getText().toString().trim().replace(" ", "");
        //Converting weight object to String array and inserting to
        // integer weight array
        String[] objWeightArr = listObject.split(",");
        int[] intWeightArr = new int[objWeightArr.length];
        for (int i = 0; i < objWeightArr.length; i++) {

            try {
                String num = objWeightArr[i];
                intWeightArr[i] = Integer.parseInt(num);
            } catch (Exception e) {
                Toast.makeText(this, "Please enter valid number don't give input in decimal format.", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        //Converting Value object to String array and inserting to
        // integer Value array
        String[] objValArr = listObjectVal.split(",");

        int[] intValArr = new int[objValArr.length];
        for (int i = 0; i < objValArr.length; i++) {
            try {
                String num = objValArr[i];
                intValArr[i] = Integer.parseInt(num);
            } catch (Exception e) {
                Toast.makeText(this, "Please enter valid number don't give input in decimal format.", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        //Condition to check number of object weight has must some value there
        //are no weight with value or viceversa
        if (objWeightArr.length == objValArr.length) {
            try {
                long endTime = System.nanoTime();
                timeElapsed = endTime - startTime;
                //Setting value to Textview on the condition whether it is solved from
                //recursion or through dynamic problem
                //set Best value
                // Set program execution time
                //set time complexity to textview
                if (isRecursion) {
                    tvKnapSackValue.setText("Max Value: " + String.valueOf(maxValByRecursion(Integer.
                            valueOf(knapsackWeight), intWeightArr, intValArr, intValArr.length)) + "Time taken to " +
                            "execute program : " + timeElapsed + "Nano sec");
                } else {
                    tvKnapSackValue.setText("Max Value: " + String.valueOf(getMaxValByDynamicProg(Integer.
                            valueOf(knapsackWeight), intWeightArr, intValArr, intValArr.length)) + "Time taken to " +
                            "execute program : " + timeElapsed + "Nano sec");
                }
            } catch (Exception e) {
                // Exception handling for wrong type input given by user
                e.printStackTrace();
                Toast.makeText(this, R.string.valid, Toast.LENGTH_SHORT).show();
            }
        } else {
            //Message to show number of object weight has must some value there
            //are no weight with value or viceversa
            Toast.makeText(this, getResources().getString(R.string.weight_value_wrong_input), Toast.LENGTH_SHORT).show();
        }

    }


}
```

### Add cast plugin dependency

If you also need Google Cast integration for your app, add a dependency to the cast plugin as well.

Add the dependency in your cast app module's `build.gradle` file.

```
package com.io.knapsack_test;

import static com.io.knapsack_test.MaxValueByRecursion.max;

public class MaxValByDynamicProg {

    // Returns the maximum value that can
    // be put in a knapsack bag of capacity W
    //w = weight of knapsack bag
    // wt[] = weight of different object {10,20,30}
    //val[] = Valye of different object with theier corresponding weight {10,20,30}
    public static int getMaxValByDynamicProg(int W, int wt[],
                        int val[], int n) {
        int i, w;
        int K[][] = new int[n + 1][W + 1];

        // Build table K[][] in bottom up manner
        for (i = 0; i <= n; i++) {
            for (w = 0; w <= W; w++) {
                if (i == 0 || w == 0)
                    K[i][w] = 0;
                else if (wt[i - 1] <= w)
                    K[i][w]
                            = max(val[i - 1]
                                    + K[i - 1][w - wt[i - 1]],
                            K[i - 1][w]);
                else
                    K[i][w] = K[i - 1][w];
            }
        }

        return K[n][W];
    }
}

```

```
package com.io.knapsack_test;

public class MaxValueByRecursion {
    // A utility function that returns
    // maximum of two integers
    public static int max(int a, int b)
    {
        return (a > b) ? a : b;
    }

    // Returns the maximum value that can
    // be put in a knapsack bag of capacity W
    //w = weight of knapsack bag
    // wt[] = weight of different object {10,20,30}
    //val[] = Valye of different object with theier corresponding weight {10,20,30}

    public static int maxValByRecursion(int W, int wt[], int val[], int n)
    {
        // Base Case
        if (n == 0 || W == 0)
            return 0;

        // If weight of the nth item is
        // more than Knapsack capacity W,
        // then this item cannot be included
        // in the optimal solution
        if (wt[n - 1] > W)
            return maxValByRecursion(W, wt, val, n - 1);

            // Return the maximum of two cases:
            // (1) nth item included
            // (2) not included
        else
            return max(val[n - 1]
                            + maxValByRecursion(W - wt[n - 1], wt,
                    val, n - 1),
                    maxValByRecursion(W, wt, val, n - 1));
    }
}

```


```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <androidx.appcompat.widget.Toolbar
        android:id="@+id/toolbarTop"
        android:layout_height="wrap_content"
        android:layout_width="match_parent"
        android:minHeight="?android:attr/actionBarSize"
        android:background="@color/recursion_btn_bg"
        >


        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Toolbar Title"
            android:fontFamily="@font/open_sans_bold_italic"
            android:layout_gravity="center"
            android:id="@+id/toolbar_title" />


    </androidx.appcompat.widget.Toolbar>

    <EditText
        android:id="@+id/etKnapsackWeight"
        android:layout_width="match_parent"
        android:layout_below="@+id/toolbarTop"
        android:layout_height="@dimen/fifty_five"
        android:layout_marginLeft="@dimen/ten"
        android:layout_marginTop="@dimen/fifteen"
        android:layout_marginRight="@dimen/ten"
        android:inputType="number"
        android:background="@drawable/etbackground"
        android:fontFamily="@font/open_sans_regular"
        android:hint="@string/enter_bag_weight"
        android:padding="@dimen/fifteen"
        android:textColor="@color/black"
        android:textSize="@dimen/fourteen_sp" />

    <EditText
        android:id="@+id/etObjectWeight"
        android:layout_width="match_parent"
        android:layout_height="@dimen/fifty_five"
        android:layout_below="@+id/etKnapsackWeight"
        android:layout_marginLeft="@dimen/ten"
        android:layout_marginTop="@dimen/fifteen"
        android:layout_marginRight="@dimen/ten"
        android:background="@drawable/etbackground"
        android:fontFamily="@font/open_sans_regular"
        android:hint="@string/enter_object_weight"
        android:padding="@dimen/fifteen"
        android:textColor="@color/black"
        android:textSize="@dimen/fourteen_sp" />

    <TextView
        android:id="@+id/tvHintText"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_below="@+id/etObjectWeight"
        android:layout_marginLeft="@dimen/twenty"
        android:layout_marginRight="@dimen/twenty"
        android:fontFamily="@font/open_sans_bold_italic"
        android:hint="@string/enter_multiple_weight"
        android:textStyle="italic" />

    <EditText
        android:id="@+id/etObjectVal"
        android:layout_width="match_parent"
        android:layout_height="@dimen/fifty_five"
        android:layout_below="@+id/tvHintText"
        android:layout_marginLeft="@dimen/ten"
        android:layout_marginTop="@dimen/fifteen"
        android:layout_marginRight="@dimen/ten"
        android:background="@drawable/etbackground"
        android:fontFamily="@font/open_sans_regular"
        android:hint="@string/enter_object_value"
        android:padding="@dimen/fifteen"
        android:textColor="@color/black"
        android:textSize="@dimen/fourteen_sp" />

    <TextView
        android:id="@+id/tvMultipleObjValHint"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_below="@+id/etObjectVal"
        android:layout_marginLeft="@dimen/twenty"
        android:layout_marginRight="@dimen/twenty"
        android:fontFamily="@font/open_sans_bold_italic"
        android:hint="@string/multiple_val"
        android:textStyle="italic" />

    <TextView
        android:id="@+id/tvKnapSackValue"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="50dp"
        android:layout_marginLeft="@dimen/fifteen"
        android:layout_marginRight="@dimen/fifteen"
        android:layout_below="@+id/tvMultipleObjValHint"
        android:fontFamily="@font/open_sans_regular"
        android:text="@string/dummy_text"
        android:textSize="@dimen/twenty"
        android:textStyle="bold" />

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:orientation="horizontal"
        android:weightSum="1">

        <Button
            android:id="@+id/btnKnapSackZeroOne"
            android:layout_width="@dimen/zero"
            android:layout_height="@dimen/fifty_five"
            android:layout_margin="@dimen/ten"
            android:layout_weight="0.5"
            android:fontFamily="@font/open_sans_bold_italic"
            android:text="Dynamic Programming"
            android:textSize="@dimen/ten_sp"
            app:backgroundTint="@color/dynamic_btn_bg" />

        <Button
            android:id="@+id/btnKnapSackGreedy"
            android:layout_width="@dimen/zero"
            android:layout_height="@dimen/fifty_five"
            android:layout_margin="@dimen/ten"
            android:layout_weight="0.5"
            android:fontFamily="@font/open_sans_bold_italic"
            android:text="@string/by_recursion"
            android:textSize="@dimen/ten_sp"
            app:backgroundTint="@color/recursion_btn_bg"
            tools:ignore="SmallSp" />
    </LinearLayout>


</RelativeLayout>
```

```
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="purple_200">#FFBB86FC</color>
    <color name="purple_500">#FF6200EE</color>
    <color name="purple_700">#FF3700B3</color>
    <color name="teal_200">#FF03DAC5</color>
    <color name="teal_700">#FF018786</color>
    <color name="black">#FF000000</color>
    <color name="white">#FFFFFFFF</color>
    <color name="recursion_btn_bg">#CFC913</color>
    <color name="notification_bar">#CFC913</color>
    <color name="dynamic_btn_bg">#CFAA13</color>
    <color name="stroke_bg">#CFAA13</color>
</resources>
```

```
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <dimen name="fifty_five">55dp</dimen>
    <dimen name="fifteen">15dp</dimen>
    <dimen name="ten">10dp</dimen>
    <dimen name="fourteen_sp">14sp</dimen>
    <dimen name="twenty">20dp</dimen>
    <dimen name="zero">0dp</dimen>
    <dimen name="ten_sp">10sp</dimen>
</resources>
```

```
<resources>
    <string name="app_name">Knapsack_test</string>
    <string name="enter_bag_weight">Enter bag weight.</string>
    <string name="enter_object_weight">Enter object weight.</string>
    <string name="enter_multiple_weight">Enter multiple Object weight using comma like 2,13,15</string>
    <string name="enter_object_value">Enter object value</string>
    <string name="multiple_val">Enter multiple Object value using comma like 2,13,15.</string>
    <string name="dummy_text">Value: 0</string>
    <string name="weight_value_wrong_input">Please enter same  number of object weight and value seprated with comma like (12,16,8)&amp; (14,18,6) </string>
    <string name="by_recursion">By Recursion</string>
    <string name="toolbar_text">Tenner Coding Challenge App</string>
    <string name="valid">Please enter valid number don\'t give input in decimal or special character as a input.</string>
</resources>
```
## Issues

Please send all issues and feedback to utkarshcpr@outlook.com
