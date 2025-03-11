package application;

import custom.frame.MainFrame;
import custom.panel.MainPanel;
import custom.screen.MainScreen;

import javax.swing.*;
import java.awt.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class UIMain {

    public static void main(String[] args) {
        final var jogoConfig = Stream.of(args).collect(toMap(
                k -> k.split(",")[0],
                v -> v.split(",")[1],
                (v1, v2) -> v1 + ";" + v2
        ));
        var mainScreen = new MainScreen(jogoConfig);
        mainScreen.buildMainScreen();
    }
}
