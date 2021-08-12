package com.example.myapp

import android.media.Image

data class reviewInfo(val img: Image?, val id:String?,val date:String?,
                      val r1:Float?,val r2:Float?,val r3:Float?,val r4:Float?,val review:String?)
//사진 어떤 형식으로 가져오는지 보고 수정해야함
//별점 이름도 보고 수정
