package com.javastudio.tutorial.concurrency;

import com.antkorwin.commonutils.gc.GcUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

class ReferencesInJavaTest {

    @Test
    void testStrong() {
        Foo firstReference = new Foo();
        Foo secondReference = firstReference;

        firstReference = null;
        secondReference = null;
        //Now the GC can collect an instance of the Foo, which created in the first line.
    }

    @Test
    void testWeakAfterGC() {
        // Arrange
        String instance = new String("123");
        WeakReference<String> reference = new WeakReference<>(instance);

        // Act
        instance = null;
        System.gc();

        // Asserts
        Assertions.assertThat(reference.get()).isNull();
    }

    @Test
    void testWeakMap() throws InterruptedException {
        // Arrange
        WeakHashMap<String, Boolean> map = new WeakHashMap<>();
        String instance = new String("123");
        map.put(instance, true);

        // Act
        instance = null;
        GcUtils.fullFinalization();

        // Asserts
        Assertions.assertThat(map).isEmpty();
    }

    @Test
    void testSoftAfterTryingToAllocateAllAvailableMemory() {
        // Arrange
        String instance = new String("Sample");
        SoftReference<String> softReference = new SoftReference<>(instance);
        instance = null;
        Assertions.assertThat(softReference).isNotNull();
        Assertions.assertThat(softReference.get()).isNotNull();

        // Act
        GcUtils.tryToAllocateAllAvailableMemory();

        // Asserts
        Assertions.assertThat(softReference.get()).isNull();
    }
}
