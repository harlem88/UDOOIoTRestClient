package org.udoo.iotrestclient.model

/**
 * Created by harlem88 on 23/05/17.
 */
data class SensorModel(val _id: String, val sensor_id: String, val sensor_type: String,
                  val gateway: String, val node: String, val displayName:String)