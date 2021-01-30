package com.mif50.baseapp.helper.moshi

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

class MoshiJsonListAdapersFactory : JsonAdapter.Factory {
    override fun create(type: Type, annotations: MutableSet<out Annotation>, moshi: Moshi): JsonAdapter<*>? {
//        return when (type) {
//            Types.newParameterizedType(ArrayList::class.java, Restaurant::class.java) ->
//                moshi.adapter<ArrayList<Restaurant>>(type)
//            Types.newParameterizedType(ArrayList::class.java, Category::class.java) ->
//                moshi.adapter<ArrayList<Category>>(type)
//            Types.newParameterizedType(ArrayList::class.java, ItemCategory::class.java) ->
//                moshi.adapter<ArrayList<ItemCategory>>(type)
//            else -> null
//        }
        return null
    }
}