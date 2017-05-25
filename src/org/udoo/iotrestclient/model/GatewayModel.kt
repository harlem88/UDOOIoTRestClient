package org.udoo.iotrestclient.model


/**
 * Created by harlem88 on 23/05/17.
 */
data class GatewayModel(val _id: String, val gateway_id: String, val __v: String,
                   val board_id: String, val company:String, val displayName:String,
                   val productType: String, val nodes: Array<NodeModel>, val connected: Boolean)