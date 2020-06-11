package com.fdmt.walmart.domain.stores.entity

import com.fdmt.walmart.domain.base.Response
import com.google.gson.annotations.SerializedName

data class StoresResponse(

    @field:SerializedName("codeMessage")
    val codeMessage: Int,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("responseArray")
    val responseArray: List<Store>

    ) : Response