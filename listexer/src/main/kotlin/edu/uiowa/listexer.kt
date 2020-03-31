package edu.uiowa

// important note: in all cases, you may assume that all
// lists of integers in test cases are only non-negative integers

// this is just a handy absolute value function
fun abs(expr:Int): Int = if (expr < 0) (-expr) else expr

// Given a list of integers V, the function smallestGap(V) returns the
// smallest absolute difference between successive integers in V. For
// example, V = [10,5,12,15,10,7] then the smallest difference between
// consecutive numbers is 3, found in two cases, from 12 to 15 and from
// 10 to 7. Notice that if V is empty or contains just one item, then 
// there is no smallest difference, so let the function return -1 for
// that special case 
fun smallestGap(V:List<Int>): Int {
    when {
        V.size == 1 -> return -1
        V.isEmpty() -> return -1
    }
    var smallDiff = abs(V[0] - V[1])
    for(i in 1..V.size - 2) {
        if (abs(V[i] - V[i+1]) < smallDiff) {
            smallDiff = abs(V[i]-V[i+1])
        }
    }
    return smallDiff
}

// Given a list of integers, a "hill" is the situation where an item in the 
// list is greater than the previous item and also greater than the next item.
// The function counthills(V) returns the number of hills in list V.  There  
// are some edge cases for V. If V is an empty list, then return 0;  if 
// V is a sorted list, then also return 0 because there are no hills; there 
// are other edge cases, but these are similar cases for returning 0.  
fun countHills(V:List<Int>): Int {
    when {
        V.isEmpty() -> return 0
        V.sorted() == V -> return 0
        V.sortedDescending() == V -> return 0
    }
    var hills = 0
    for (i in 1..V.size-2) {
        if (V[i] > V[i-1] && V[i] > V[i+1]) {
            hills++
        }
    }
    return hills
}

// GCD(V) returns the largest positive integer which divides evenly into
// all the integers in list V. The edge case for V is when V is empty, so 
// return 0 if V is empty.
fun GCD(V:List<Int>): Int {
    when {
        V.isEmpty() -> return 0
    }
    fun GCDFinder(A:Int, B:Int):Int {
        if(A == 0)
            return B
        return GCDFinder(B % A, A)
    }
    fun GCDHelper(P:List<Int>, n:Int):Int {
        var result = P[0]
        for(i in 1..n-1)
            result = GCDFinder(P[i],result)
        return result
    }
    return GCDHelper(V,V.size)
}

// Given two lists of integers V and W, return the element of V which 
// is found most often in W. For instance, if V is [2,4,4,8] and W is 
// [8,7,6,2,8,4] then mostCommon(V,W) returns 8. Interesting is that 
// mostCommon(W,V) returns 4 by the same logic. For all edge cases,
// and if no item from the first list is found in the second, return
// -1
fun mostCommon(V:List<Int>, W:List<Int>): Int {
    if(V.isEmpty() || W.isEmpty()) {
        return -1
    }
    if (V.intersect(W).isEmpty()) {
        return -1
    }
    var frequency = 0
    var number = 0
    var list = V.intersect(W).toList()
    for (e in list) {
        var tempFreq = W.count{ it == e }
        if (tempFreq > frequency) {
            frequency = tempFreq
            number = e
        }
    }
    return number
}
