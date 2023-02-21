package com.example.benchmark

import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScrollBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun scroll() = benchmarkRule.measureRepeated(
        packageName = "com.hsmnzaydn.chatgptv2",
        metrics = listOf(FrameTimingMetric()),
        iterations = 1,
        startupMode = StartupMode.HOT
    ) {
        startActivityAndWait()
        device.wait(Until.hasObject(By.text("Please type something or click microphone")),5000)
        val list  = device.findObject(By.res("$packageName:id/rv"))
        list.fling(Direction.UP)
        device.wait(Until.hasObject(By.text("hi")),5000)
        device.waitForIdle()
    }
}