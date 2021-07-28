package com.example.founddog.model

data class PostDTO (var imgUrl: String? = null,
                    var title : String? = null,
                    var content : String? = null,
                    var uid : String? = null,
                    var timestamp : Long? = null,
                    var label : Int = 0
        )