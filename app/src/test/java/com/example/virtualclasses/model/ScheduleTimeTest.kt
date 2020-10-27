package com.example.virtualclasses.model

import org.junit.Test

import org.junit.Assert.*

class ScheduleTimeTest {

    @Test
    fun isBeforeTest1() {
        val s1 = ScheduleTime("02", "10", MidDay.AM)
        val s2 = ScheduleTime("02", "09", MidDay.PM)
        val result = s1.isBefore(s2)
        assertTrue(result)
    }
    @Test
    fun isBeforeTest2() {
        val s1 = ScheduleTime("11", "10", MidDay.AM)
        val s2 = ScheduleTime("12", "09", MidDay.AM)
        val result = s1.isBefore(s2)
        assertTrue(result)
    }
    @Test
    fun isBeforeTest3() {
        val s1 = ScheduleTime("01", "08", MidDay.AM)
        val s2 = ScheduleTime("01", "09", MidDay.AM)
        val result = s1.isBefore(s2)
        assertTrue(result)
    }
    @Test
    fun isBeforeTest4() {
        val s1 = ScheduleTime("02", "08", MidDay.AM)
        val s2 = ScheduleTime("01", "09", MidDay.AM)
        val result = s1.isBefore(s2)
        assertFalse(result)
    }

    @Test
    fun isBeforeOrEqualTest1(){
        val s1 = ScheduleTime("01", "08", MidDay.AM)
        val s2 = ScheduleTime("01", "08", MidDay.AM)
        val result = s1.isBeforeOrEqual(s2)
        assertTrue(result)
    }

    @Test
    fun equalsTest1(){
        val s1 = ScheduleTime("01", "08", MidDay.AM)
        val s2 = null
        val result = s1.equals(s2)
        assertFalse(result)
    }
    @Test
    fun equalsTest2(){
        val s1 = ScheduleTime("01", "08", MidDay.AM)
        val s2 = "BAC"
        val result = s1.equals(s2)
        assertFalse(result)
    }
}