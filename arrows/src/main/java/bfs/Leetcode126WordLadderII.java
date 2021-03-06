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

package bfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <pre>
 *  126. Word Ladder II
 * Difficulty: Hard
 * Given two words (beginWord and endWord), and a dictionary's word list,
 * find all shortest transformation sequence(s) from beginWord to endWord, such that:
 *
 * <strong>>Only one letter</strong can be changed at a time
 * Each intermediate word must exist in the word list
 * For example,
 *
 * Given:
 *       beginWord = "hit"
 *       endWord = "cog"
 *
 *      wordList = ["hot","dot","dog","lot","log"]
 * Return
 *      [
 *          ["hit","hot","dot","dog","cog"],
 *          ["hit","hot","lot","log","cog"]
 *      ]
 * Note:
 *      All words have the <strong> same length</strong>.
 *      All words contain <strong>only lowercase</strong> alphabetic characters.
 *
 * Tags
 *      Array
 *      Backtracking
 *      Breadth-first Search
 *      String
 *  =======================================================================================
 *                      p a r i s
 *                     /          \
 *                     -  ?      -   p a r k s
 *                     \          /
 *                      m a r k s
 *
 *       begin   ...      ...         ...       ...    end
 *
 *   or
 *      red -> rex -> tex
 *      red -> ted -> tex
 *
 *      Note:
 *         1   do not move from pool too early before current level is done.
 *         2   need handle the one like 'parks' who have more than 1 pres, N:1
 *         3   once found on some level then stop continue to next level.
 *
 *    Test case:
 *      String s = "magic";
 *      String e = "pearl";
 *      String[] arr = new String[]{
 *      "flail", "halon", "lexus", "joint", "pears", "slabs", "lorie", "lapse", "wroth", "yalow", "swear", "cavil", "piety",
 *      "yogis", "dhaka", "laxer", "tatum", "provo", "truss", "tends", "deana", "dried", "hutch", "basho", "flyby",
 *      "miler", "fries", "floes", "lingo", "wider", "scary", "marks", "perry", "igloo", "melts", "lanny", "satan",
 *      "foamy", "perks", "denim", "plugs", "cloak", "cyril", "women", "issue", "rocky", "marry", "trash", "merry",
 *      "topic", "hicks", "dicky", "prado", "casio", "lapel", "diane", "serer", "paige", "parry", "elope", "balds",
 *      "dated", "copra", "earth", "marty", "slake", "balms", "daryl", "loves", "civet", "sweat", "daley", "touch",
 *      "maria", "dacca", "muggy", "chore", "felix", "ogled", "acids", "terse", "cults", "darla", "snubs", "boats",
 *      "recta", "cohan", "purse", "joist", "grosz", "sheri", "steam", "manic", "luisa", "gluts", "spits", "boxer",
 *      "abner", "cooke", "scowl", "kenya", "hasps", "roger", "edwin", "black", "terns", "folks", "demur", "dingo",
 *      "party", "brian", "numbs", "forgo", "gunny", "waled", "bucks", "titan", "ruffs", "pizza", "ravel", "poole",
 *      "suits", "stoic", "segre", "white", "lemur", "belts", "scums", "parks", "gusts", "ozark", "umped", "heard",
 *      "lorna", "emile", "orbit", "onset", "cruet", "amiss", "fumed", "gelds", "italy", "rakes", "loxed", "kilts",
 *      "mania", "tombs", "gaped", "merge", "molar", "smith", "tangs", "misty", "wefts", "yawns", "smile", "scuff",
 *      "width", "paris", "coded", "sodom", "shits", "benny", "pudgy", "mayer", "peary", "curve", "tulsa", "ramos",
 *      "thick", "dogie", "gourd", "strop", "ahmad", "clove", "tract", "calyx", "maris", "wants", "lipid", "pearl",
 *      "maybe", "banjo", "south", "blend", "diana", "lanai", "waged", "shari", "magic", "duchy", "decca", "wried",
 *      "maine", "nutty", "turns", "satyr", "holds", "finks", "twits", "peaks", "teems", "peace", "melon", "czars",
 *      "robby", "tabby", "shove", "minty", "marta", "dregs", "lacks", "casts", "aruba", "stall", "nurse", "jewry", "knuth"
 *      };
 *
 *      expected answer:
 *        [["magic", "manic", "mania", "maria", "maris", "paris", "parks", "perks", "peaks", "pears", "pearl"],
 *                                                                  |
 *         ["magic", "manic", "mania", "maria", "marta", "marty", "party", "parry", "perry", "peary", "pearl"],
 *         ["magic", "manic", "mania", "maria", "marta", "marty", "marry", "merry", "perry", "peary", "pearl"],
 *         ["magic", "manic", "mania", "maria", "marta", "marty", "marry", "parry", "perry", "peary", "pearl"],
 *         ["magic", "manic", "mania", "maria", "maris", "marks", "parks", "perks", "peaks", "pears", "pearl"]]
 *                                                                  |
 *      wrong answer:
 *        [["magic","manic","mania","maria","maris","paris","parks","perks","peaks","pears","pearl"],
 *        ["magic","manic","mania","maria","marta","marty","marry","parry","perry","peary","pearl"],
 *        ["magic","manic","mania","maria","marta","marty","marry","merry","perry","peary","pearl"],
 *        ["magic","manic","mania","maria","marta","marty","party","parry","perry","peary","pearl"]]
 *
 *      s = "a";
 *      e = "c";
 *      arr = new String[]{"a", "b", "c"};
 *
 *      expected: [['a','c']]
 */
public class Leetcode126WordLadderII {

    private static void connect(List<List<String>> rs, List<String[]> toStarts, List<String[]> toEnds) {
        String[] tmp = new String[toStarts.iterator().next().length + toEnds.iterator().next().length];
        for (String[] toStart : toStarts) {
            int size = 0;
            for (int i = toStart.length - 1; i >= 0; i--) {
                tmp[size++] = toStart[i];
            }
            for (String[] toEnd : toEnds) {
                int position = size;
                for (int i = 0; i <= toEnd.length - 1; i++) {
                    tmp[position++] = toEnd[i];
                }
                rs.add(Arrays.asList(tmp.clone()));
            }
        }
    }

    private static void tracesToArrays(String cutoff, String sta, String end,
                                       Map<String, List<String>> pres,
                                       List<String[]> half1s, String[] half, int index) {
        if (cutoff.equals(sta) || cutoff.equals(end)) { //
            half1s.add(Arrays.copyOf(half, index));
            return;
        }
        List<String> froms = pres.get(cutoff);
        for (String from : froms) {
            half[index] = from;
            tracesToArrays(from, sta, end, pres, half1s, half, index + 1);
        }
    }

    private static void pickUp(String cur, String t,
                               List<List<String>> rs, Map<String, List<String>> pres, String sta, String end, int maxSeriesSize) {
        List<String[]> half1s = new ArrayList<>();
        List<String[]> half2s = new ArrayList<>();

        String[] tmp = new String[maxSeriesSize];

        tmp[0] = cur;
        tracesToArrays(cur, sta, end, pres, half1s, tmp, 1);

        tmp[0] = t;
        tracesToArrays(t, sta, end, pres, half2s, tmp, 1);

        String[] checker = half1s.iterator().next();
        if (checker[checker.length - 1].equals(sta)) {
            connect(rs, half1s, half2s);
        } else {
            connect(rs, half2s, half1s);
        }
    }

    private static void meet(Set<String> starts, Set<String> aims, Set<String> pool,
                             List r, Map<String, List<String>> pres, String s, String e, int maxSeriesSize) {
        if (starts.size() > aims.size()) {
            meet(aims, starts, pool, r, pres, s, e, maxSeriesSize);
            return;
        }
        // starts
        Set<String> nexts = new HashSet();
        boolean connected = false;
        for (String cur : starts) {
            char[] cs = cur.toCharArray();
            if (cur.equals("maris")) {
                pres.toString();
            }
            for (int i = 0; i < cs.length; i++) {
                char ci = cs[i];
                for (char c = 'a'; c <= 'z'; c++) {
                    if (c != ci) {
                        cs[i] = c;
                        String connectTo = new String(cs);// transformed neighbor;
                        if (aims.contains(connectTo)) {
                            pickUp(cur, connectTo, r, pres, s, e, maxSeriesSize);
                            connected = true;
                            continue;
                            // do not break here as cur may connect to more ways.
                        }

                        if (pool.contains(connectTo)) {
                            nexts.add(connectTo);
                            //pool.remove(nei);

                            List<String> froms = pres.get(connectTo);
                            if (froms == null) {
                                froms = new ArrayList<>();
                            }
                            froms.add(cur);
                            pres.put(connectTo, froms);
                        }
                    }
                }
                cs[i] = ci;
            }
        }

        // stop
        if (!connected && !starts.isEmpty()) {
            pool.removeAll(nexts);
            meet(nexts, aims, pool, r, pres, s, e, maxSeriesSize);
        }
    }

    public static List<List<String>> findLadders(String beginWord, String endWord, Set<String> wordList) {
        //18:11
        Map<String, List<String>> pres = new HashMap<>();

        Set<String> starts = new HashSet();
        Set<String> aims = new HashSet();

        starts.add(beginWord);
        aims.add(endWord);

        wordList.remove(beginWord);
        wordList.remove(endWord);

        List r = new ArrayList();
        meet(starts, aims, wordList, r, pres, beginWord, endWord, wordList.size() + 2);
        return r;
    }
}
