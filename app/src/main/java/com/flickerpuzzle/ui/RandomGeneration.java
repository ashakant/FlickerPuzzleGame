package com.flickerpuzzle.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * (c) Ashakant 2014
 *
 * @author Ashakant <ashakant.kumar@gmail.com>
 * @since 14:03:59 - 18.11.2014
 */
public class RandomGeneration {
	private List<Integer> mRandomNumList;  
    private int mIndex;

    /**  
     * Create a random permutation the sequence of numbers 0, 1, ... , n - 1.
     * @param-n Range specifier, must be positive
     * @return-RandomGeneration
     */
    public RandomGeneration(int a_n) {
        if (a_n <= 1) {
            throw new IllegalArgumentException(
                    "Positive number expected, got: " + a_n);
        }
        mRandomNumList = new ArrayList<Integer>();
        newList(a_n);
    }

    /**  
     * Creates a new list
     * @param-int
     * @return-void
     */
    public void newList(int a_n) {
        mIndex = -1;
        mRandomNumList.clear();
        for (int i = 0; i < a_n; i++) {
            mRandomNumList.add(i);
        }
        Collections.shuffle(mRandomNumList);
    }

    /**  
     * Retrieve the next integer in sequence. 
     * @param-void
     * @return-int
     */
    public int next() {
        mIndex = (mIndex + 1) % mRandomNumList.size();
        return mRandomNumList.get(mIndex);
    }

    /**  
     * get the random number as list 
     * @param-void
     * @return-List<Integer>
     */
	public List<Integer> getList() {
		return mRandomNumList;
	}

	public void setList(List<Integer> list) {
		this.mRandomNumList = list;
	}
}
