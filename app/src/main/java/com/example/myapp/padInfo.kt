package com.example.myapp



data class padInfo(val id: Int?,val manufacturer:String?,val name:String?,val image:String?)
data class padlist(val id: Int?,val manufacturer:String?,val name:String?,val image:String?,val ingredients:List<Ingredient>)
data class Ingredient(val id: Int?,val name:String?,val enName:String?,val average:Float?,val max:Float?,val min:Float?,val sideEffect:String?)