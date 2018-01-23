package com.canoo.dp.impl.platform.core.ansi;

import com.canoo.dp.impl.platform.core.PlatformVersion;

import java.io.IOException;
import java.util.Optional;

/**
 * Created by hendrikebbers on 23.01.18.
 */
public class PlatformLogo {

    private static String getVersionString() {
        final PlatformVersion version = new PlatformVersion();
        try {
            return version.getVersion();
        } catch (IOException e) {
            return null;
        }
    }

    public static void printLogo() {
        final String version = getVersionString();
        final String versionString = Optional.of(version).map(v -> "Version " + version + " | ").orElse("");
        final String versionEndSuffix = "                                      ".substring(versionString.length());

        final String strokeColor = AnsiOut.ANSI_BLUE;
        final String textColor = AnsiOut.ANSI_RED;
        final String borderColor = AnsiOut.ANSI_GREEN;


        final String borderStart = AnsiOut.ANSI_BOLD + borderColor;
        final String borderEnd = AnsiOut.ANSI_RESET;
        final String borderPipe = borderStart + "|" + borderEnd;
        final String logoStart = AnsiOut.ANSI_BOLD + strokeColor;
        final String textStart = textColor;
        final String textEnd = AnsiOut.ANSI_RESET;
        final String boldTextStart = AnsiOut.ANSI_BOLD + textColor;
        final String boldTextEnd = AnsiOut.ANSI_RESET;

        System.out.println("");
        System.out.println("  " + borderStart + "_____________________________________________________________________________________" + borderEnd);
        System.out.println("  " + borderPipe + logoStart + "   _____        _       _     _       _____  _       _    __                       " + borderPipe);
        System.out.println("  " + borderPipe + logoStart + "  |  __ \\      | |     | |   (_)     |  __ \\| |     | |  / _|                      " + borderPipe);
        System.out.println("  " + borderPipe + logoStart + "  | |  | | ___ | |_ __ | |__  _ _ __ | |__) | | __ _| |_| |_ ___  _ __ _ __ ___    " + borderPipe);
        System.out.println("  " + borderPipe + logoStart + "  | |  | |/ _ \\| | '_ \\| '_ \\| | '_ \\|  ___/| |/ _` | __|  _/ _ \\| '__| '_ ` _ \\   " + borderPipe);
        System.out.println("  " + borderPipe + logoStart + "  | |  | | (_) | | |_) | | | | | | | | |    | | (_| | |_| || (_) | |  | | | | | |  " + borderPipe);
        System.out.println("  " + borderPipe + logoStart + "  | |__| |\\___/|_| .__/|_| |_|_|_| |_|_|    |_|\\__,_|\\__|_| \\___/|_|  |_| |_| | |  " + borderPipe);
        System.out.println("  " + borderPipe + logoStart + "  |_____/        | |                                                          |_|  " + borderPipe);
        System.out.println("  " + borderPipe + logoStart + "                 |_|   " + textStart + versionString + "supported by " + textEnd + boldTextStart + "canoo.com" + boldTextEnd + versionEndSuffix + borderPipe);
        System.out.println("  " + borderPipe + borderStart + "___________________________________________________________________________________" + borderEnd + borderPipe);
        System.out.println("");
        System.out.println("");
    }
}
