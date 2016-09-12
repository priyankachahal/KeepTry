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

import array.Common;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import static array.Common.divide;

public class MergeSortRecursionMultiThreads3 {
    /**
     * <pre>
     * Should use RecursiveTask.
     * --But as the input is array and only its content is changed in the precess of fork.
     *   So use RecursiveAction to make it simple.
     *
     * --Must join() to wait else the result will be unexpected
     *
     * invokeAll(l,r); // same as l.fork();  r.fork(); l.join();  r.join();
     */
    private static class DivideMergeInSortAction extends RecursiveAction {
        private Comparable[] arr;

        @Override
        protected void compute() {
            // Input check, threshold
            if (arr == null || arr.length <= 1) {
                return;
            }

            final Comparable[][] halves = divide(arr);

            invokeAll(new DivideMergeInSortAction(halves[0]),
                    new DivideMergeInSortAction(halves[1]));

            Common.mergeInsort(halves[0], halves[1], arr);
        }

        public DivideMergeInSortAction(Comparable[] arr) {
            this.arr = arr;
        }
    }

    public static <T extends Comparable<T>> void mergeSort(T[] arr) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        forkJoinPool.invoke(new DivideMergeInSortAction(arr));
    }
}