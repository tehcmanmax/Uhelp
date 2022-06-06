package com.tehcman.services.build_keyboards.bahaviors;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

public interface IBuildKeyboardBehavior {
    List<KeyboardRow> build();
}
