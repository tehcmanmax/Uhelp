package com.tehcman.services.build_keyboards.client;

import com.tehcman.services.build_keyboards.bahaviors.IBuildKeyboardBehavior;
import com.tehcman.services.build_markup.IMarkup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

@Service
public abstract class BuildKeyboardClientService {
    private IBuildKeyboardBehavior iBuildKeyboardBehavior;
    private IMarkup mainMarkup;

    public void setiBuildKeyboardBehavior(IBuildKeyboardBehavior iBuildKeyboardBehavior) {
        this.iBuildKeyboardBehavior = iBuildKeyboardBehavior;
    }

    @Autowired
    public void setMainMarkup(IMarkup mainMarkup) { //encapsulate
        this.mainMarkup = mainMarkup;
    }

    public List<KeyboardRow> outputKeyboard(){
        return this.iBuildKeyboardBehavior.build();
    }
}
