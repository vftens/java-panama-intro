package com.example;

import java.foreign.Library;
import java.foreign.Libraries;
import java.foreign.NativeTypes;
import java.foreign.Scope;
import java.foreign.annotations.NativeHeader;
import java.foreign.annotations.NativeFunction;
import java.foreign.memory.Array;
import java.foreign.memory.Pointer;
import java.lang.invoke.MethodHandles;

public class App {
    @NativeHeader
    interface Totalizer {
        @NativeFunction("(u64:i32 i32)u64")
        long sum(Pointer<Integer> vals, int size);
    }

    public static void main(String[] args) {
        Library lib = Libraries.loadLibrary(MethodHandles.lookup(), "totalizer");
        Totalizer totalizer = Libraries.bind(Totalizer.class, lib);

        try (Scope scope = Scope.newNativeScope()) {
            Array<Integer> array = scope.allocateArray(NativeTypes.INT, new int[] { 23, 15, 4, 16, 42, 8 });
            System.out.println(totalizer.sum(array.elementPointer(), 3));
        }
    }
}
