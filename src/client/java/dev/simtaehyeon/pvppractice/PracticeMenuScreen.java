package dev.simtaehyeon.pvppractice;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class PracticeMenuScreen extends Screen {
    private static final String[] MODES = {"VS AI", "1v1", "2v2", "3v3", "4v4"};
    private static final String[] WEAPONS = {"검", "도끼", "활", "랜덤"};
    private static final String[] MATERIALS = {"나무", "돌", "철", "금", "다이아몬드", "네더라이트"};
    private static final String[] DIFFICULTIES = {"쉬움", "보통", "어려움"};

    private int selectedMode = 0;
    private int selectedWeapon = 0;
    private int selectedMaterial = 2;
    private int selectedDifficulty = 1;
    private EditBox customWeaponField;
    private boolean showNotReady;

    public PracticeMenuScreen() {
        super(Component.translatable("screen.pvp_practice.title"));
    }

    @Override
    protected void init() {
        int center = this.width / 2;

        // Every row is positioned from the screen center to avoid left/right drift.
        int modeWidth = 62;
        int modeGap = 5;
        int modeStart = center - (MODES.length * modeWidth + (MODES.length - 1) * modeGap) / 2;
        for (int i = 0; i < MODES.length; i++) {
            final int index = i;
            addRenderableWidget(Button.builder(modeLabel(i), b -> selectMode(index))
                    .bounds(modeStart + i * (modeWidth + modeGap), 78, modeWidth, 24).build());
        }

        int weaponWidth = 78;
        int weaponGap = 8;
        int weaponStart = center - (WEAPONS.length * weaponWidth + (WEAPONS.length - 1) * weaponGap) / 2;
        for (int i = 0; i < WEAPONS.length; i++) {
            final int index = i;
            addRenderableWidget(Button.builder(weaponLabel(i), b -> selectWeapon(index))
                    .bounds(weaponStart + i * (weaponWidth + weaponGap), 135, weaponWidth, 24).build());
        }

        int materialWidth = 86;
        int materialGap = 5;
        int materialStart = center - (MATERIALS.length * materialWidth + (MATERIALS.length - 1) * materialGap) / 2;
        for (int i = 0; i < MATERIALS.length; i++) {
            final int index = i;
            addRenderableWidget(Button.builder(materialLabel(i), b -> selectMaterial(index))
                    .bounds(materialStart + i * (materialWidth + materialGap), 192, materialWidth, 24).build());
        }

        customWeaponField = new EditBox(font, center - 150, 250, 300, 20,
                Component.translatable("screen.pvp_practice.custom_weapon"));
        customWeaponField.setMaxLength(32);
        addRenderableWidget(customWeaponField);

        for (int i = 0; i < DIFFICULTIES.length; i++) {
            final int index = i;
            addRenderableWidget(Button.builder(difficultyLabel(i), b -> selectDifficulty(index))
                    .bounds(center - 150 + i * 102, 292, 96, 24).build());
        }

        addRenderableWidget(Button.builder(Component.translatable("screen.pvp_practice.start"),
                        b -> showNotReady = true)
                .bounds(center - 100, 350, 200, 28).build());
        addRenderableWidget(Button.builder(Component.translatable("gui.back"), b -> onClose())
                .bounds(center - 100, 386, 200, 20).build());
    }

    private Component modeLabel(int index) {
        return Component.literal((selectedMode == index ? "✓ " : "") + MODES[index]);
    }

    private Component weaponLabel(int index) {
        return Component.literal((selectedWeapon == index ? "✓ " : "") + WEAPONS[index]);
    }

    private Component materialLabel(int index) {
        return Component.literal((selectedMaterial == index ? "✓ " : "") + MATERIALS[index]);
    }

    private Component difficultyLabel(int index) {
        return Component.literal((selectedDifficulty == index ? "✓ " : "") + DIFFICULTIES[index]);
    }

    private void selectMode(int index) {
        selectedMode = index;
        rebuildWidgets();
    }

    private void selectWeapon(int index) {
        selectedWeapon = index;
        rebuildWidgets();
    }

    private void selectMaterial(int index) {
        selectedMaterial = index;
        rebuildWidgets();
    }

    private void selectDifficulty(int index) {
        selectedDifficulty = index;
        rebuildWidgets();
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);

        int center = this.width / 2;
        graphics.centeredText(font, title, center, 30, 0xFFFFFFFF);
        graphics.centeredText(font, Component.translatable("screen.pvp_practice.mode"), center, 58, 0xFFE0E0E0);
        graphics.centeredText(font, Component.translatable("screen.pvp_practice.weapon"), center, 115, 0xFFE0E0E0);
        graphics.centeredText(font, Component.translatable("screen.pvp_practice.material"), center, 172, 0xFFE0E0E0);
        graphics.centeredText(font, Component.translatable("screen.pvp_practice.custom_weapon_label"),
                center, 235, 0xFFE0E0E0);
        graphics.centeredText(font, Component.translatable("screen.pvp_practice.difficulty"), center, 272, 0xFFE0E0E0);
        graphics.centeredText(font,
                Component.translatable("screen.pvp_practice.selection",
                        MODES[selectedMode], WEAPONS[selectedWeapon] + " / " + MATERIALS[selectedMaterial],
                        DIFFICULTIES[selectedDifficulty]),
                center, 326, 0xFFA0A0A0);
        if (showNotReady) {
            graphics.centeredText(font, Component.translatable("screen.pvp_practice.not_ready"),
                    center, 420, 0xFFFF8080);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
