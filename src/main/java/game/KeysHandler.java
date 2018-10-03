package game;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class KeysHandler {
    private Set<KeyCode> input;
    private Set<KeyCode> validCodes;

    public KeysHandler(KeyCode ... codes) {
        input = new HashSet<>();
        validCodes = new HashSet<>();
        Collections.addAll(validCodes, codes);
    }

    public EventHandler<? super KeyEvent> pressedHandler() {
        return (EventHandler<KeyEvent>) event -> {
            KeyCode code = event.getCode();

            if(validCodes.contains(code) && !input.contains(code)) {
                input.add(code);
            }
        };
    }

    public EventHandler<? super KeyEvent> releasedHandler() {
        return (EventHandler<KeyEvent>) event -> input.remove(event.getCode());
    }

    public void reset() {
        input.clear();
    }

    public boolean isPressed(KeyCode keyCode) {
        return input.contains(keyCode);
    }
}
