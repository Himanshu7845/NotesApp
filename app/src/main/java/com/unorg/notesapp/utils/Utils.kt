package com.unorg.notesapp.utils

import android.app.Activity
import android.util.Log
import android.widget.Toast


fun Activity.showToast(msg:String){
    Toast.makeText(this,msg,Toast.LENGTH_LONG).show()

}fun Activity.showLogs(tag:String,msg:String){
    Log.d(tag, "showLogs: $msg")
}


fun getColorList():ArrayList<String>{
    val list = ArrayList<String>()
    list.add("#FFA500")
    list.add("#8000ff")
    list.add("#00ffbf")
    list.add("#ff0040")
    list.add("#B833FF")
    list.add("#FFE133")
    list.add("#33E9FF")
    return list
}