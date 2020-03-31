package edu.uiowa

import org.junit.Test
import org.junit.Assert.*

class ListexerTest {
    @Test
    fun test1a() {  // edge case of smallestGap
       assertEquals(-1,smallestGap(listOf<Int>()))
       assertEquals(-1,smallestGap(listOf(99)))
       }
    @Test
    fun test1b() { // test normal case for smallestGap
       val V = listOf(10,5,12,15,10,7)
       assertEquals(3,smallestGap(V))
       assertEquals(1,smallestGap(listOf(1,2)))
       val W = listOf(10,35,21,64,55,47,8,19,38)
       assertEquals(8,smallestGap(W))
       val X = listOf(16,9,9,10,11)
       assertEquals(0,smallestGap(X))
       }
    @Test
    fun test2a() { // test edge cases of countHills
       assertEquals(0,countHills(listOf<Int>())) // empty list
       assertEquals(0,countHills(listOf(1,2)))
       assertEquals(0,countHills(listOf(8,7,6)))
       assertEquals(0,countHills(listOf(5,6,7,8,9,10)))
       }
    @Test
    fun test2b() { // test of normal cases for countHills
       assertEquals(1,countHills(listOf(1,5,2)))
       assertEquals(2,countHills(listOf(2,3,9,4,8,12,10)))
       assertEquals(4,countHills(listOf(0,1,0,1,0,1,0,1,0,1)))
       }

    @Test
    fun test3a() { // test of unusual GCD cases
       assertEquals(0,GCD(listOf<Int>())) // empty list test
       assertEquals(97,GCD(listOf(97)))   // single item test
       }
    @Test
    fun test3b() { // test of normal GCD cases
       assertEquals(12,GCD(listOf(24,36)))
       assertEquals(2,GCD(listOf(2,4,6,8)))
       assertEquals(1,GCD(listOf(5,13,2,18)))
       assertEquals(3,GCD(listOf(273,54,75)))
       }

    @Test
    fun test4a() { // test of edge cases for mostCommon
       val emptyList = listOf<Int>()
       val V = listOf(2,4,4,8)
       val W = listOf(8,7,6,2,8,4)
       assertEquals(-1,mostCommon(listOf<Int>(),listOf<Int>()))
       assertEquals(-1,mostCommon(V,emptyList))
       assertEquals(-1,mostCommon(emptyList,W))
       }
    @Test
    fun test4b() { // normal test cases
       val V = listOf(2,4,4,8)
       val W = listOf(8,7,6,2,8,4)
       assertEquals(8,mostCommon(V,W))
       assertEquals(4,mostCommon(W,V))
       val A = listOf(10,5,7,10,17,12,7,7,15,10,7)
       val B = listOf(10,10,10,10,7,17,15)
       assertEquals(10,mostCommon(A,B))
       assertEquals(7,mostCommon(B,A))
       }
    }
