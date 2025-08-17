package com.scheduler.enums;
 public enum Week{
     SUNDAY(1),MONDAY(2),TUESDAY(3),WEDNESDAY(4),THURSDAY(5),FRIDAY(6),SATURDAY(7);


     private final int day;

      Week(int day) {
        this.day=day;
     }
     public int  getValue(){
         return this.day;
     }
 }
