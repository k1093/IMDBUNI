package com.kevin.imdb.api.response

import com.squareup.moshi.Json

class ErrorResponse(@Json(name = "status_code") var status_code : Int,
                    @Json(name = "status_message") var status_message : String)