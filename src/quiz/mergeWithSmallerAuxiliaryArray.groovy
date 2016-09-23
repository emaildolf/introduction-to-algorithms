int[] createArray(int N) {

    Random r = new Random()
    int[] array = new int[2 * N]
    for (int i = 0; i < 2 * N; i++) {
        array[i] = r.nextInt(N);
    }

    Arrays.sort(array, 0, N)
    Arrays.sort(array, N, 2 * N)

    return array
}

boolean isSorted(int[] array) {

    for (int i = 0; i < array.length - 1; i++) {
        if (array[i] > array[i + 1]) {
            return false
        }
    }
    return true
}

void merge(int[] array, int[] aux, int N) {

    //copy lower array to aux
    for(int n=0; n<N; n++){
        aux[n] = array[n]
    }

    int n = 0
    int lo = 0
    int hi = N
    while(n < 2*N){

        if( lo < N && (hi >= 2*N || aux[lo] <= array[hi])){
            array[n] = aux[lo]
            lo++
        }else {
            array[n] = array[hi]
            hi++
        }
        n++
    }
}

void test() {

    Random r = new Random()
    1000.times {
        int N = r.nextInt(1000)
        int[] aux = new int[N]
        int[] array = createArray(N)

        merge(array, aux, N)
        assert isSorted(array)
    }

    println "ok!"
}

test()
