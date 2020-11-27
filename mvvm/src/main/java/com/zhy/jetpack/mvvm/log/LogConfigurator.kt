package com.zhy.jetpack.mvvm.log

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.joran.JoranConfigurator
import ch.qos.logback.core.joran.spi.JoranException
import ch.qos.logback.core.util.StatusPrinter
import com.zhy.jetpack.mvvm.BuildConfig
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

private val log = LoggerFactory.getLogger(LogConfigurator::class.java)
private const val LOG_DIR = "LOG_DIR"
private val DEFAULT_LEVEL = if (BuildConfig.DEBUG) Level.DEBUG else Level.INFO


class LogConfigurator {


    companion object {
        const val LOGBACK_FILE_NAME = "logback.xml"
        @JvmStatic
        fun configure(logRootDir: String, logFilePath: String) {
            val logFile = File(logFilePath)
            if (!logFile.canRead()) {
                log.warn("Using default log configuration, logbackFile can not read.")
                return
            }
            configure(logRootDir, FileInputStream(logFile))
        }

        @JvmStatic
        fun configure(logRootDir: String, logbackInputStream: InputStream) {
            System.setProperty(LOG_DIR, logRootDir)
            val loggerContext = LoggerFactory.getILoggerFactory() as LoggerContext
            try {
                loggerContext.reset()
                val configurator = JoranConfigurator()
                configurator.context = loggerContext
                configurator.doConfigure(logbackInputStream)
            } catch (e: JoranException) {
                log.warn("config logger conf error.", e)
            }
            StatusPrinter.printInCaseOfErrorsOrWarnings(loggerContext)
            log.info("Successfully configured application logging.")
        }

        @JvmStatic
        fun resetLevel() {
            setLevel(DEFAULT_LEVEL)
        }

        @JvmStatic
        fun setLevel(level: Level) {
            val root = LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME) as Logger
            root.level = level
        }
    }
}