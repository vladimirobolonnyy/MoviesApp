package ru.padawans.domain.di

interface MainDependencyProvider {
    val getDataProvider: DataProvider
}