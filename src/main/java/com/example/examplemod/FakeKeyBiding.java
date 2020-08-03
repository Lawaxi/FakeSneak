package com.example.examplemod;

import net.minecraft.client.settings.KeyBinding;

public class FakeKeyBiding extends KeyBinding {

    public FakeKeyBiding(String description, int keyCode, String category) {
        super(description, keyCode, category);
    }

    public boolean pressed_permanent = false;

    @Override
    public boolean isPressed() {
        return pressed_permanent || super.isPressed();
    }

    @Override
    public boolean isKeyDown() {
        return pressed_permanent || super.isKeyDown();
    }
}
