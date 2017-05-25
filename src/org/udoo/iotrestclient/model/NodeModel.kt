package org.udoo.iotrestclient.model

/**
 * Created by harlem88 on 23/05/17.
 */
class NodeModel(val interval_time: Long, val address: String, val product_type: String,
                val id: String, val displayName:String, val _id: String,
                val actuators: Array<GatewayModel>, val sensors: Array<GatewayModel>)