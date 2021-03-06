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

package list;

import list.DoublyLinkedList2;
import org.junit.Assert;
import org.junit.Test;

public class R38Test<E> {

    @Test(timeout = 30L, expected = Test.None.class)
    public void test() {
        DoublyLinkedList2 test = new DoublyLinkedList2();
        Assert.assertSame(test.getMiddle(), null);
        Assert.assertSame(test.getMiddle2(), null);
        test.addBeforeHead("5");
        Assert.assertSame(test.getMiddle(), "5");
        Assert.assertSame(test.getMiddle2(), "5");
        test.addBeforeHead("4");
        Assert.assertSame(test.getMiddle(), "4");
        Assert.assertSame(test.getMiddle2(), "4");
        test.addBeforeHead("3");
        Assert.assertSame(test.getMiddle(), "4");
        Assert.assertSame(test.getMiddle2(), "4");
        test.addBeforeHead("2");
        Assert.assertSame(test.getMiddle(), "3");
        Assert.assertSame(test.getMiddle2(), "3");
        test.addBeforeHead("1");
        Assert.assertSame(test.getMiddle(), "3");
        Assert.assertSame(test.getMiddle2(), "3");

    }
}
