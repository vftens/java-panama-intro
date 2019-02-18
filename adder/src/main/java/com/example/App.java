package com.example;

import java.foreign.Library;
import java.foreign.Libraries;
import java.foreign.annotations.NativeHeader;
import java.foreign.annotations.NativeFunction;
import java.lang.invoke.MethodHandles;

public class App {
    @NativeHeader
    interface Adder {
        @NativeFunction("(i32 i32)i32")
        int add(int a, int b);
    }

    public static void main(String[] args) {
        Library lib = Libraries.loadLibrary(MethodHandles.lookup(), "adder");
        Adder adder = Libraries.bind(Adder.class, lib);

        System.out.println(adder.add(3, 5));
    }
}
