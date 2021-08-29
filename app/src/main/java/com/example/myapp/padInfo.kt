package com.example.myapp



data class padInfo(val id: Int?,val manufacturer:String?,val name:String?,val image:String?)
data class padlist(val id: Int?,val manufacturer:String?,val name:String?,val image:String?,val ingredients:ArrayList<Ingredient>,val rank:Int?,val safeScore:Int?)
data class Ingredient(val id: Integer?,val name:String?,val enName:String?,val average:Double?,val max:Double?,val min:Double?,val sideEffect:String?)
data class detectioninfo(val pad:Int?, val ingredient:Integer?, val detection: Double?)


data class parent(val image:String?, val name:String?, val average: Double?, val detection: Double?)