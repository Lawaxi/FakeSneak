package com.example.examplemod;

import net.minecraft.client.settings.KeyBinding;

public class SwitchKeyBiding extends KeyBinding {

    public SwitchKeyBiding(String description, int keyCode, String category) {
        super(description, keyCode, category);
    }

    public boolean on = false;
}
