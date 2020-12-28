package com.example.demowork1.litepal.model

import org.litepal.crud.LitePalSupport

class DataEntity1(var id: Int = 0, var name: String ="default") : LitePalSupport() {

    override fun toString():String{
        return "name=$name   id=$id"
    }
}
class DataEntity2(name: String = "ssass"):LitePalSupport(){

}
class DataEntity3(name: String = "ssass"):LitePalSupport(){

}