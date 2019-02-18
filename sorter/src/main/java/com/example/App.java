package com.example;

import java.foreign.Library;
import java.foreign.Libraries;
import java.foreign.NativeTypes;
import java.foreign.Scope;
import java.foreign.annotations.NativeHeader;
import java.foreign.annotations.NativeFunction;
import java.foreign.annotations.NativeCallback;
import java.foreign.memory.Array;
import java.foreign.memory.Callback;
import java.foreign.memory.Pointer;
import java.lang.invoke.MethodHandles;

public class App {
    @NativeHeader
    interface StdLib {
        @NativeFunction("(u64:[0i32] i32 i32 u64:(u64:i32 u64:i32) i32)v")
        void qsort(Pointer<Integer> base, int nitems, int size, Callback<QComparator> comparator);

        @NativeCallback("(u64:i32 u64:i32)i32")
        interface QComparator {
            int compare(Pointer<Integer> p1, Pointer<Integer> p2);
        }
    }

    public static void main(String[] args) {
        Library lib = Libraries.loadLibrary(MethodHandles.lookup(), "msvcr120");
        StdLib stdLib = Libraries.bind(StdLib.class, lib);

        try (Scope scope = Scope.newNativeScope()) {
            Array<Integer> array = scope.allocateArray(NativeTypes.INT, new int[] { 23, 15, 4, 16, 42, 8 });
            Callback<StdLib.QComparator> cb = scope.allocateCallback((p1, p2) -> p1.get() - p2.get());
            stdLib.qsort(array.elementPointer(), (int) array.length(), Integer.BYTES, cb);
            for (int i = 0; i < array.length(); i++) {
                System.out.printf("%d ", array.get(i));
            }
            System.out.println();
        }
    }
}
