package com.example.myapp


data class reviewInfo(val pad:Int?,
                      val star1:Float?,val star2:Float?,val star3:Float?,val star4:Float?,
                      val content:String?, val created:String?)
data class reviewList(val id:Int?,val reviews:ArrayList<reviewInfo>)
data class p_reviewInfo(val pad:Int?,val star1:Int?,val star2:Int?,val star3:Int?,val star4:Int?,
                        val content:String?)