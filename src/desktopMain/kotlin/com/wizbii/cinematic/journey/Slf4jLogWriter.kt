package com.wizbii.cinematic.journey

import co.touchlab.kermit.LogWriter
import co.touchlab.kermit.Severity
import co.touchlab.kermit.Severity.Assert
import co.touchlab.kermit.Severity.Debug
import co.touchlab.kermit.Severity.Error
import co.touchlab.kermit.Severity.Info
import co.touchlab.kermit.Severity.Verbose
import co.touchlab.kermit.Severity.Warn
import org.slf4j.LoggerFactory

class Slf4jLogWriter : LogWriter() {

    override fun isLoggable(tag: String, severity: Severity): Boolean =
        LoggerFactory.getLogger(tag).run {
            when (severity) {
                Verbose       -> isTraceEnabled
                Debug         -> isDebugEnabled
                Info          -> isInfoEnabled
                Warn          -> isWarnEnabled
                Error, Assert -> isErrorEnabled
            }
        }

    override fun log(
        severity: Severity,
        message: String,
        tag: String,
        throwable: Throwable?,
    ) {
        LoggerFactory.getLogger(tag).run {
            when (severity) {
                Verbose       -> trace(message, throwable)
                Debug         -> debug(message, throwable)
                Info          -> info(message, throwable)
                Warn          -> warn(message, throwable)
                Error, Assert -> error(message, throwable)
            }
        }
    }

}
