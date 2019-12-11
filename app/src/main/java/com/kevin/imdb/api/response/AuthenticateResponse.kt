package com.kevin.imdb.api.response

import com.squareup.moshi.Json

class AuthenticateResponse(@Json(name = "success") var success : Boolean,
                           @Json(name = "expires_at") var expires_at : String,
                           @Json(name = "guest_session_id") var guest_session_id : String)