package quiz

class Node {
    int val
    Node next
}

Node createList(int N){

    Node head = new Node(val: 0)
    Node current = head
    for(int i=1; i<N; i++){
        current.next = new Node(val: i)
        current = current.next
    }
    return head
}

Node shuffle(Node head, int size, Random r){

    if(size == 1){
        return head
    }

    int half = size/2
    Node prev = head
    Node mid = head.next
    for(int i=1; i< half; i++){
        prev = mid
        mid = mid.next
    }
    prev.next = null

    Node lower = shuffle(head, half, r)
    Node upper = shuffle(mid, half, r)

    //merge
    Node newHead
    if(r.nextBoolean()){
        newHead = lower
        lower = lower.next
    }else{
        newHead = upper
        upper = upper.next
    }

    current = newHead
    while(lower != null || upper != null){

        boolean b = r.nextBoolean()

        if(b && lower != null){
            current.next = lower
            current = current.next
            lower = lower.next
        }

        if(!b && upper != null){
            current.next = upper
            current = current.next
            upper = upper.next
        }
    }

    return newHead
}

void printList(Node head) {
    Node current = head
    while(current != null){
        print current.val + " -> "
        current = current.next
    }
    println "null"
}

void test() {

    int N = 20
    Node head = createList(N)
    printList(head)

    Node newHead = shuffle(head, N, new Random())

    printList(newHead)
}

test()