package com.example.benchmark

import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WriteBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun write() = benchmarkRule.measureRepeated(
        packageName = "com.hsmnzaydn.chatgptv2",
        metrics = listOf(FrameTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.HOT
    ) {
        startActivityAndWait()
        device.wait(Until.hasObject(By.text("Please type something or click microphone")),5000)
        val edt = device.findObject(By.text("Please type something or click microphone"))
        edt.click()
        edt.text = "Selam canim"
        val sendButton = device.findObject(By.res("$packageName:id/materialCardView"))
        sendButton.click()
        device.waitForIdle()
    }
}