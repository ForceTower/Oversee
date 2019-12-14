package dev.forcetower.oversee.module

import com.google.gson.Gson
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.util.concurrent.Executor

abstract class Module<T> (private val executor: Executor? = null) {
    private val _result: PublishSubject<List<T>> = PublishSubject.create()
    val result: Subject<List<T>>
        get() = _result

    protected val gson: Gson = Gson()

    lateinit var finishedResult: List<T>
        protected set

    var isSuccess: Boolean = false
        protected set

    var exception: Throwable? = null
        private set

    init { run() }

    private fun run() {
        if (executor != null) {
            executor.execute { this.provideWrapper() }
        } else {
            this.provideWrapper()
        }
    }

    private fun provideWrapper() {
        try {
            val completed = this.provide()
            publishProgress(completed)
        } catch (error: Throwable) {
            isSuccess = false
            exception = error
        }
    }

    /**
     * This method will be called immediately after the construction of this object
     */
    abstract fun provide(): List<T>

    fun publishProgress(value: List<T>) {
        finishedResult = value
        result.onNext(value)
    }
}