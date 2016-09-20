String color(List array, int i) {
    return array[i]
}

void swap(List array, int i, int j) {
    String tmp = array[i]
    array[i] = array[j]
    array[j] = tmp
}

def sort(List array) {

    //order: red, white, blue
    int redIndex = 0
    int blueIndex = array.size()-1

    int i = 0;
    while(i <= blueIndex){

        String color = color(array, i)

        if(color == "red"){
            swap(array, i, redIndex)
            redIndex++
            i++
        }
        else if(color == "blue"){
            swap(array, i, blueIndex)
            blueIndex--
        }else{
            i++
        }
    }
}

void test(){
    Random random = new Random()
    int n = random.nextInt(100)

    List colors = ["red", "white", "blue"]

    List array = (1..n).collect{
        colors[random.nextInt(colors.size())]
    }

    List sortedArray = array.sort(false){
        colors.indexOf(it)
    }
    sort(array)

    assert sortedArray == array
    assert !sortedArray.is(array)
}

100.times{
    test()
}

println "ok"
