package org.udoo.iotrestclient

/**
 * Created by harlem88 on 23/05/17.
 */

fun main(args: Array<String>) {
    val user = args[0]
    val pass = args[1]
    val url = args[2]
    var iotManager = IoTServiceManger(user, pass, url)
    iotManager.launch()
}



