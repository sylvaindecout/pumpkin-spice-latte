package io.shodo.pumpkin.monolith

import io.cucumber.core.backend.ObjectFactory
import io.cucumber.picocontainer.PicoFactory
import io.shodo.pumpkin.monolith.stepdefs.TestContext


class CustomPicoFactory : ObjectFactory {
    init {
        addClass(TestContext::class.java)
    }

    private val delegate = PicoFactory()

    fun Injector() {
        addClass(TestContext::class.java)
    }

    override fun start() {
        delegate.start()
    }

    override fun stop() {
        delegate.stop()
    }

    override fun addClass(aClass: Class<*>?): Boolean {
        return delegate.addClass(aClass)
    }

    override fun <T> getInstance(aClass: Class<T>?): T {
        return delegate.getInstance(aClass)
    }
}

