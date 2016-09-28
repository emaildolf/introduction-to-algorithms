package quiz

import edu.princeton.cs.algs4.MaxPQ

List version1(int n) {

    MaxPQ<Integer> heap = new MaxPQ<>()
    for(int a=0; a<=n; a++){
        for(int b=a+1; b<=n; b++){
            heap.insert(a**3 + b**3)
        }
    }

    int last = heap.delMax()
    List taxicabNumbers = [-1]
    while(heap.size() > 0){
        int current = heap.delMax()
        if(current == last && current != taxicabNumbers.last()){
            taxicabNumbers << current
        }
        last = current
    }

    taxicabNumbers.remove(0)
    return taxicabNumbers
}

println version1(100)