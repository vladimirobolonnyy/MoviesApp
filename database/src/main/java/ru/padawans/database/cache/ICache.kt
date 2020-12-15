package ru.padawans.database.cache

interface ICache {
    fun set(key: String, value: Any, lifetimeSeconds: Long)
    fun get(key: String): Any?
    fun clear(key: String)
}