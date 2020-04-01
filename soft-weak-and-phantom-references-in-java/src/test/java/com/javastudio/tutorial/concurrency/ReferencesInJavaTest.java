package com.javastudio.tutorial.concurrency;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.ref.WeakReference;

class ReferencesInJavaTest {

    @Test
    void testStrongReference() {
        Foo firstReference = new Foo();
        Foo secondReference = firstReference;

        firstReference = null;
        secondReference = null;
        //Now the GC can collect an instance of the Foo, which created in the first line.
    }

    @Test
    void testWeakReference() {
        // Arrange
        String instance = new String("123");
        WeakReference<String> reference = new WeakReference<>(instance);

        // Act
        instance = null;
        System.gc();

        // Asserts
        Assertions.assertThat(reference.get()).isNull();
    }

}
