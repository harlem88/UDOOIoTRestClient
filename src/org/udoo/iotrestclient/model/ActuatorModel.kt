package org.udoo.iotrestclient.model

/**
 * Created by harlem88 on 23/05/17.
 */
data class ActuatorModel(val _id: String, val actuator_id: String, val actuator_type: String,
                    val  gateway: String, val node: String, val displayName:String)