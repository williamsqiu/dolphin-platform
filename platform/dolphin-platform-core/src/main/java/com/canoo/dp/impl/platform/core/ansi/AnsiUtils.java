/*
 * Copyright 2015-2018 Canoo Engineering AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.canoo.dp.impl.platform.core.ansi;

import org.apiguardian.api.API;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.apiguardian.api.API.Status.INTERNAL;

@API(since = "0.x", status = INTERNAL)
public class AnsiUtils {

    public final static String PREFIX = "\u001B[";

    public final static String FOREGROUND_PREFIX = "3";

    public final static String BACKGROUND_PREFIX = "4";

    public final static String SUFFIX = "m";

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BOLD = "\u001B[1m";

    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";


    public static void printLinefeed() {
        System.out.println();
    }

    public static void reset() {
        print(ANSI_RESET);
    }

    public static void set(String... conf) {
        reset();
        Arrays.asList(conf).forEach(s -> print(s));
    }

    public static void print(String str) {
        System.out.print(str);
    }

    public static void printHorizontalLine(int length) {
        IntStream.range(0, length - 1).forEach(i -> System.out.print("_"));
    }

    public static void printVerticalLine() {
        System.out.print("|");
    }

    public static void printSpaces(int length) {
        IntStream.range(0, length - 1).forEach(i -> System.out.print(" "));
    }

}
