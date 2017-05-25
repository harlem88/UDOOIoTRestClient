package org.udoo.iotrestclient


import org.udoo.iotrestclient.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by harlem88 on 23/05/17.
 */

interface IoTService {

    @POST("/token")
    fun login(@Body loginRequest: LoginModelRequest): Call<LoginModelResponse>

    @GET("/ext/debug")
    fun debug(): Call<IoTResponse>

    @GET("/ext/network")
    fun network(): Call<NetworkModel>

    /**
     *  sensorId analog|digital
     */
    @GET("/ext/sensors/read/{gatewayId}/{nodeId}/{sensorId}/{pin}")
    fun readArduino(@Path("gatewayId") gatewayId : String, @Path("nodeId") nodeId : String,
                    @Path("sensorId") sensorId : String , @Path("pin") pin :String): Call<IoTApiResponse>

    /**
     *  sensorId servo|analog|digital
     */
    @GET("/ext/sensors/write/{gatewayId}/{nodeId}/{sensorId}/{pin}/{value}")
    fun writeArduino(@Path("gatewayId") gatewayId : String, @Path("nodeId") nodeId : String,
                     @Path("sensorId") sensorId : String , @Path("pin") pin :String,
                     @Path("value") value :String): Call<IoTApiResponse>
}