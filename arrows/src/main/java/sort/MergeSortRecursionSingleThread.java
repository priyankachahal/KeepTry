//  Copyright 2016 The Sawdust Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//

package sort;

import common_lib.Common;
import common_lib.Merger;

import java.util.List;

import static common_lib.Common.mergeInsort;

/**
 * @see java.util.Arrays#sort(int[])
 * @see java.util.Collections#sort(List)
 */
public class MergeSortRecursionSingleThread {
    static private Merger merger = new Common();

    /**
     * Merge sort array arr's scope [l, r].
     *
     * @param array array
     * @param l     left bound index, included.
     * @param r     right bound index, included.
     * @param tmp   with it, do not need new tmp array every time when mergeInsort and care its length
     */
    private static <T extends Comparable<T>> void call(T[] array, int l, int r, T[] tmp) {
        if (l == r) {
            // stop recursion when there is only element.
            return;
        }

        int mid = (l + r) / 2;

        call(array, l, mid, tmp);
        call(array, mid + 1, r, tmp);

        merger.mergeInsort(array, l, mid, r, tmp);
    }

    /**
     * Merge sort an array with one thread using recursion
     * Idea:
     * 1>  Divide into 2 halves  [l, mid] and [mid+1, r]
     * 2>  Recursion on each halves. if l == r, stop recursion, return to wait mergeInsort
     * 3>  Merge them into one.
     */
    public static void mergeSortRecursion(Comparable[] arr) {
        // Input check
        if (arr == null || arr.length <= 1) { // note: arr may be empty array: {}
            return;
        }
        call(arr, 0, arr.length - 1, // note: The index is included
                new Comparable[arr.length]);
    }
}
