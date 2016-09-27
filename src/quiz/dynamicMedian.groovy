package quiz

import edu.princeton.cs.algs4.MaxPQ
import edu.princeton.cs.algs4.MinPQ

class DynamicMedian {

    Integer median;
    MinPQ<Integer> right
    MaxPQ<Integer> left

    public DynamicMedian() {
        right = new MinPQ<>()
        left = new MaxPQ<>()
    }

    void insert(int val) {

        if (median == null) {
            median = val
        } else if (val > median) {

            right.insert(val)

            if (right.size() > left.size() + 1) {
                rotateLeft()
            }
        } else {

            left.insert(val)

            if (left.size() > right.size() + 1) {
                rotateRight()
            }
        }
    }

    private void rotateLeft() {
        left.insert(median)
        median = right.delMin()
    }

    private void rotateRight() {
        right.insert(median)
        median = left.delMax()
    }

    int peek() {
        median
    }

    int delMedian() {

        int tmp = median
        if (left.size() > right.size()) {
            median = left.delMax()
        } else {
            median = right.delMin()
        }

        return tmp
    }
}

void test() {

    DynamicMedian dm = new DynamicMedian()

    [1, 2, 3, 4, 8, 0, -1].each {
        dm.insert(it)
    }

    assert dm.peek() == 2
    assert dm.delMedian() == 2
    assert [1, 3].contains(dm.peek())
    dm.delMedian()

    assert dm.peek() == 1

    [2, 3, 9, -2].each {
        dm.insert(it)
    }

    assert dm.peek() == 2

}

test()

