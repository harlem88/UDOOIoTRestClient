package org.udoo.iotrestclient

import okhttp3.OkHttpClient
import org.udoo.iotrestclient.model.IoTApiResponse
import org.udoo.iotrestclient.model.LoginModelRequest
import org.udoo.iotrestclient.model.NetworkModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection


/**
 * Created by harlem88 on 24/05/17.
 */

class IoTServiceManger(val username: String, val password: String, val url: String) {
    var token: String = ""
    var iotService: IoTService?
    var network: NetworkModel? = null

    init {
        val client = OkHttpClient().newBuilder()
                .addInterceptor({ chain ->
                    val request = chain?.request()?.newBuilder()?.addHeader("Authorization", "JWT " + token)?.build()
                    chain?.proceed(request)
                })
//                .addInterceptor(HttpLoggingInterceptor()
//                        .apply { level = HttpLoggingInterceptor.Level.BODY })
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl("http://$url")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        iotService = retrofit.create(IoTService::class.java)
    }

    fun launch() {
        println("-------- Login $username $url -----------------")
        val loginCall = iotService?.login(LoginModelRequest(username, password))
        val login = loginCall?.execute()?.body()
        println("Login  -> ${login?.status}")
        if (login?.status == true) {
            token = login.token
            getNetwork()
            startInput()
        }

    }

    fun getNetwork() {
        println("Get Network")
        val networkCall = iotService?.network()
        network = networkCall?.execute()?.body()
        network?.companyGW?.forEach({
            print("\nCompany ${it.company}|")
            it.gateways.forEach {
                print("Gateway ${it.gateway_id}|")
            }
        })
    }

    fun startInput(){
        while(true) {
            println("\nUsage: READ(0)|WRITE(1) DIGITAL(0)|ANALOG(1)|SERVO(2)|UPLOAD(3) PIN VALUE NODE(ttyMCC) GATEWAYID")
            val args = readLine()?.split(" ")
            val strClip = StringSelection(args.toString().replace("[", "").replace("]", "").replace(",", ""))
            Toolkit.getDefaultToolkit().systemClipboard.setContents(strClip, strClip)
            val op = args?.get(0)
            var sensorId :String ="Digital"
            when(args?.get(1)){
                "0" -> sensorId = "digital"
                "1" -> sensorId = "analog"
                "2" -> sensorId = "servo"
                "3" -> sensorId = "upload"
            }
            val pin = args?.get(2)
            val value = args?.get(3)
            val nodeId = args?.get(4)
            val gatewayId = args?.get(5)
            var iotCall: Call<IoTApiResponse>? = null
            println("inserted op $op")
            when (op) {
                "0" -> iotCall = iotService?.readArduino(gatewayId!!, nodeId!!, sensorId, pin!!)
                "1" -> iotCall = iotService?.writeArduino(gatewayId!!, nodeId!!, sensorId, pin!!, value!!)
            }
            var iotRes = iotCall?.execute()?.body()
            println("Resp Api -> status: ${iotRes?.status} sensor: ${iotRes?.sensor} id: ${iotRes?.id}")
        }
    }

}
