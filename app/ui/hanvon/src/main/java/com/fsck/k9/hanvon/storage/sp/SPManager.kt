package com.fsck.k9.hanvon.storage.sp

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.text.TextUtils
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter

class SPManager(context: Context, spName: String) {
    private val sharedPreferences: SharedPreferences
    private val editor: Editor
    private val moshi: Moshi

    companion object {
        private const val TAG = "SPManager"
        private const val DEFAULT_SP_NAME = "default"
        private val spManagerMap = mutableMapOf<String, SPManager>()

        private fun getSPHelper(context: Context, spName: String): SPManager {
            val name = if (!TextUtils.isEmpty(spName)) spName else DEFAULT_SP_NAME
            val result = spManagerMap[name]
            return result ?: SPManager(context, name).apply {
                spManagerMap[name] = this
            }
        }

        @Synchronized
        fun init(context: Context, isCreateDefaultSP: Boolean, names: Array<String>?) {
            if (isCreateDefaultSP) {
                getSPHelper(context, DEFAULT_SP_NAME)
            }
            if (names != null) {
                for (name in names) {
                    getSPHelper(context, name)
                }
            }
        }

        @Synchronized
        fun getInstance(name: String = DEFAULT_SP_NAME): SPManager? {
            return spManagerMap[name]
                ?: throw IllegalStateException("The default share preference is not initialized before. You have to initialize it first by calling init(Context, boolean, String...) function")
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun syncPutValue(key: String, value: Any?) = apply {
        when (value) {
            is Int -> {
                editor.putInt(key, (value as? Int)!!)
            }

            is Boolean -> {
                editor.putBoolean(key, (value as? Boolean)!!)
            }

            is Float -> {
                editor.putFloat(key, (value as? Float)!!)
            }

            is String -> {
                editor.putString(key, value as? String)
            }

            is Long -> {
                editor.putLong(key, (value as? Long)!!)
            }

            else -> {
                val json = moshi.adapter<Any>().toJson(value)
                editor.putString(key, json)
            }
        }
        editor.commit()
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun asyncPutValue(key: String, value: Any?) = apply {
        when (value) {
            is Int -> {
                editor.putInt(key, (value as? Int)!!)
            }

            is Boolean -> {
                editor.putBoolean(key, (value as? Boolean)!!)
            }

            is Float -> {
                editor.putFloat(key, (value as? Float)!!)
            }

            is String -> {
                editor.putString(key, value as? String)
            }

            is Long -> {
                editor.putLong(key, (value as? Long)!!)
            }

            else -> {
                val json = moshi.adapter<Any>().toJson(value)
                editor.putString(key, json)
            }
        }
        editor.apply()
    }

    fun <T> getValue(key: String, type: Class<T>): T? {
        return when (type) {
            Int::class.java -> {
                val value: Int = sharedPreferences.getInt(key, 0)
                value as T
            }

            Boolean::class.java -> {
                val value: Boolean = sharedPreferences.getBoolean(key, false)
                value as T
            }

            Float::class.java -> {
                val value: Float = sharedPreferences.getFloat(key, 0F)
                value as T
            }

            String::class.java -> {
                val value: String = sharedPreferences.getString(key, "")!!
                value as T
            }

            Long::class.java -> {
                val value: Long = sharedPreferences.getLong(key, 0)
                value as T
            }

            else -> {
                val json: String = sharedPreferences.getString(key, "")!!
                if (TextUtils.isEmpty(json)) {
                    null
                } else {
                    moshi.adapter(type).fromJson(json)
                }
            }
        }
    }

    fun <T> getValue(key: String, type: Class<T>, defaultValue: T): T? {
        return if (sharedPreferences.contains(key)) {
            getValue(key, type)
        } else {
            defaultValue
        }
    }

//    fun <T> getValues(key: String, clazz: Class<Array<T>>): List<T>? {
//        val json: String? = sharedPreferences.getString(key, "")
//        if (!TextUtils.isEmpty(json)) {
//            val values = moshi.adapter(clazz).fromJson(json!!)
//            val list = mutableListOf<T>()
//            list.addAll(mutableListOf(values))
//            return list
//        }
//        return null
//    }

    fun <T> getValues(key: String, clazz: Class<Array<T>>): List<T> {
        val json: String? = sharedPreferences.getString(key, null) // Use null as default

        if (json.isNullOrEmpty()) {
            return emptyList() // Return empty list if no value found
        }

        try {
            val list = mutableListOf<T>()
            val array = moshi.adapter(clazz).fromJson(json)
            array?.let {
                for (t in it) {
                    list.add(t)
                }
            }
            return list
        } catch (e: Exception) {
            // Handle exceptions (e.g., logging or returning default value)
            return emptyList()
        }
    }

    fun getAllKeys(): List<String> {
        val list = mutableListOf<String>()
        return list.apply {
            sharedPreferences.getAll().forEach {
                this.add(it.key)
            }
        }
    }

    fun contains(key: String): Boolean {
        return sharedPreferences.contains(key)
    }

    fun remove(key: String) {
        editor.remove(key).commit()
    }

    fun clear() {
        editor.clear().commit()
    }

    init {
        sharedPreferences = context.applicationContext.getSharedPreferences(
            spName, Context.MODE_PRIVATE,
        )
        editor = sharedPreferences.edit()
        moshi = Moshi.Builder().build()
    }
}
