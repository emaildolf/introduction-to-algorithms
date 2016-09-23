import edu.princeton.cs.algs4.StdRandom

int[] createArray(int N){
    int[] array = new int[N]
    for(int i=0; i<N; i++) {
        array[i] = N - i
    }

    StdRandom.shuffle(array)

    return array
}

int countInversionsMerge(int[] array) {
    return countInversionsMerge(array, new int[array.length], 0, array.length-1)
}

int countInversionsMerge(int[] array, int[] aux, int lo, int hi) {

    if(hi <= lo){
        return 0
    }
    int mid = lo + (hi - lo)/2

    int count = 0

    count += countInversionsMerge(array, aux, lo, mid)
    count += countInversionsMerge(array, aux, mid+1, hi)

    //merge
    for (int k = lo; k <= hi; k++)
        aux[k] = array[k];
    int i = lo, j = mid+1;

    for (int k = lo; k <= hi; k++)
    {
        if (i > mid) array[k] = aux[j++]
        else if (j > hi) array[k] = aux[i++]
        else if (aux[j] < aux[i]) {
            array[k] = aux[j++]
            count += mid + 1 - i
        }
        else array[k] = aux[i++];
    }

    return count
}

int coutInversionsBruteForce(int[] array) {

    int count = 0
    for(int i=0; i<array.length; i++){

        for(int j=i; j<array.length; j++){

            if(array[i] > array[j]){
                count++
            }
        }
    }

    return count
}

void test() {

    Random r = new Random()
    100.times {
        int N = r.nextInt(1000)
        int[] array = createArray(N)

        int countBrute = coutInversionsBruteForce(array)
        int countMerge = countInversionsMerge(array)

        assert countMerge == countBrute
    }
}

test()
println "ok!"

