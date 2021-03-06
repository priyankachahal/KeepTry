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

package stack.intervals;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.IntStream;

/**
 * <pre>
 *   1>  Add interval -- add new interval and merge it.
 *   2> Merge Overlapping Intervals
 *      Given a set of time intervals in any order, merge all overlapping intervals into one
 *      and output the result which should have only mutually exclusive intervals.
 *
 *      Let the intervals be represented as pairs of integers for simplicity.
 *
 *      For example, let the given set of intervals be {{1,3}, {2,4}, {5,7}, {6,8} }.
 *      The intervals {1,3} and {2,4} overlap with each other,
 *      so they should be merged and become {1, 4}.
 *      Similarly {5, 7} and {6, 8} should be merged and become {5, 8}
 *
 *      ======================================================================================
 *
 *   Time O(nLogn) for sorting. merging takes linear time.
 *   O(n) extra space for stack, doing merge operations in-place ->O(1) Extra Space
 *
 * @see <a href ="http://www.geeksforgeeks.org/merging-intervals/">geeksforgeeks</a>
 */
public class Intervals {
    static class Interval {
        int start;
        int end;

        public Interval(Integer s, Integer e) {
            start = s;
            end = e;
        }
    }

    public static int merge(Interval[] arr) {
        // todo input check
        Arrays.sort(arr, new Comparator<Interval>() {
                    @Override
                    public int compare(Interval o1, Interval o2) {
                        return (o1.start < o2.start) ? -1 : ((o1.start == o2.start) ? 0 : 1);
                    }

                    @Override
                    public boolean equals(Object obj) {
                        // In real life, do not do anything here or just return false;
                        if (obj == null) {
                            return false;
                        }
                        Type genericType = obj.getClass().getGenericInterfaces()[0];
                        Type type = ((ParameterizedType) genericType).getActualTypeArguments()[0];
                        if (((Class<?>) type).getName().equals(Interval.class.getName())) {
                            // Just see how to implement "sgn(comp1.compare(o1,o2))==sgn(comp2.compare(o1, o2))
                            // for every object reference o1 and o2."
                            Comparator<Interval> thatCompa = (Comparator<Interval>) obj;

/*                            for (int i = Integer.MIN_VALUE; i <= Integer.MAX_VALUE; i++) {
                                for (int j = Integer.MIN_VALUE; j <= Integer.MAX_VALUE; j++) {
                                    Interval intervala = new Interval(i, 0);
                                    Interval intervalb = new Interval(j, 0);
                                    if (Integer.signum(thatCompa.compare(intervala, intervalb))
                                            != Integer.signum(compare(intervala, intervalb))) {
                                        return false;
                                    }
                                }
                            }
                            return true; */

                            // or use aggregate operations and parallel streams to implement parallelism
                            return IntStream.rangeClosed(Integer.MIN_VALUE, Integer.MAX_VALUE)
                                    .unordered()
                                    .parallel()
                                    .mapToObj(outInt -> new Interval(outInt, 0))
                                    .allMatch(outInterval -> IntStream.rangeClosed(Integer.MIN_VALUE, Integer.MAX_VALUE)
                                            .mapToObj(value -> new Interval(value, 0))
                                            .allMatch(innerInteval -> {
                                                        try {
                                                            // If true then continue going through all cases
                                                            // Else return false and stop.
                                                            return Integer.signum(thatCompa.compare(innerInteval, outInterval))
                                                                    == Integer.signum(compare(innerInteval, outInterval));
                                                        } catch (ClassCastException ex) {
                                                            return false;
                                                        }
                                                    }
                                            )
                                    );
                        }
                        return false;
                    }
                }
        );

        Stack<Interval> r = new Stack();
        r.push(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            Interval cur = arr[i];
            Interval top = r.peek();
            if (top.end < cur.start) {
                r.push(cur);
                continue;
            }
            if (cur.start <= top.end && top.end < cur.end) { //
                top.end = arr[i].end;
            }
        }
        return r.size();
    }

    public static void main(String[] args) {
        System.out.println(merge(new Interval[]{new Interval(1, 3), new Interval(2, 4), new Interval(5, 7), new Interval(6, 8)}));
    }

    // O(nlogn)
    public List<Interval> merge(List<Interval> intervals) {
        List<Interval> result = new ArrayList();
        if (intervals == null || intervals.size() == 0) {
            return result;
        }
        int len = intervals.size();
        int[] start = new int[len];
        int[] end = new int[len];
        for (int i = 0; i < len; i++) {
            start[i] = intervals.get(i).start;
            end[i] = intervals.get(i).end;
        }
        Arrays.sort(start);
        Arrays.sort(end);

        int pos = 0;
        for (int i = 0; i < len; i++) {
            if (i == len - 1 || start[i + 1] > end[i]) {
                result.add(new Interval(start[pos], end[i]));
                pos = i + 1;
            }
        }
        return result;
    }
}
