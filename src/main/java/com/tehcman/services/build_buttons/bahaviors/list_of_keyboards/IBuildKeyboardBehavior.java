package com.tehcman.services.build_buttons.bahaviors.list_of_keyboards;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

public interface IBuildKeyboardBehavior {
    List<KeyboardRow> build();
}
