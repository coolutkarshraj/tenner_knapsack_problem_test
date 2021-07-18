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
