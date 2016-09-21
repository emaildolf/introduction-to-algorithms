import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode(includes=['x','y'])
class Point implements Comparable<Point>{
    int x,y

    public Point(int x, int y){
        this.x = x
        this.y = y
    }

    @Override
    int compareTo(Point p) {

        if(x <=> p.x == 0){
            return y <=> p.y
        }

        return x <=> p.x
    }

    @Override
    String toString() {
        return "(${x},${y})"
    }
}

int instersection(List a, List b){

    if(a.size() == 0 || b.size() == 0){
        return 0
    }

    a.sort(true)
    b.sort(true)

    int equals = 0
    int j = 0
    for(int i=0; i<a.size(); i++) {

        Point pa = a[i]
        Point pb = b[j]

        while(pb.compareTo(pa) < 0){
            j++
            if(j == b.size()){
                return equals
            }

            pb = b[j]
        }

        if(pb.compareTo(pa) == 0){
            equals++
        }
    }

    return equals
}

void test() {

    Random random = new Random()
    int n = random.nextInt(100)

    List a = []
    List b = []
    int expectedEquals = 0

    n.times {

        Point p = new Point(random.nextInt(100), random.nextInt(100))

        double prob = random.nextDouble()
        if(!a.contains(p) && !b.contains(p)){
            if(prob < 0.4){
                a << p
            }else if (prob >= 0.4 && prob < 0.8){
                b << p
            }else{
                a << p
                b << p
                expectedEquals++
            }
        }
    }

    int numEquals = instersection(a, b)
    if(expectedEquals != numEquals){
        println "ERROR"
        println n
        println a.sort()
        println b.sort()
        println "expected: $expectedEquals"
        println "numEquals: $numEquals"
    }
}

1000.times{
    test()
}

println "ok"


