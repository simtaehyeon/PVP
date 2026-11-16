package dev.simtaehyeon.pvppractice;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class PracticeMenuScreen extends Screen {
    private static final String[] MODES = {"VS AI", "1v1", "2v2", "3v3", "4v4"};
    private static final String[] WEAPONS = {"검", "도끼", "활", "랜덤"};
    private static final String[] DIFFICULTIES = {"쉬움", "보통", "어려움"};

    private int selectedMode = 0;
    private int selectedWeapon = 0;
    private int selectedDifficulty = 1;
    private boolean showNotReady;

    public PracticeMenuScreen() {
        super(Component.translatable("screen.pvp_practice.title"));
    }

    @Override
    protected void init() {
        int center = this.width / 2;
        int left = center - 150;

        addRenderableWidget(Button.builder(modeLabel(0), b -> selectMode(0))
                .bounds(left, 78, 116, 24).build());
        addRenderableWidget(Button.builder(modeLabel(1), b -> selectMode(1))
                .bounds(left + 120, 78, 56, 24).build());
        addRenderableWidget(Button.builder(modeLabel(2), b -> selectMode(2))
                .bounds(left + 180, 78, 56, 24).build());
        addRenderableWidget(Button.builder(modeLabel(3), b -> selectMode(3))
                .bounds(left + 240, 78, 56, 24).build());
        addRenderableWidget(Button.builder(modeLabel(4), b -> selectMode(4))
                .bounds(left + 300, 78, 56, 24).build());

        for (int i = 0; i < WEAPONS.length; i++) {
            final int index = i;
            addRenderableWidget(Button.builder(weaponLabel(i), b -> selectWeapon(index))
                    .bounds(left + i * 90, 135, 86, 24).build());
        }

        for (int i = 0; i < DIFFICULTIES.length; i++) {
            final int index = i;
            addRenderableWidget(Button.builder(difficultyLabel(i), b -> selectDifficulty(index))
                    .bounds(left + 35 + i * 100, 192, 96, 24).build());
        }

        addRenderableWidget(Button.builder(Component.translatable("screen.pvp_practice.start"),
                        b -> showNotReady = true)
                .bounds(center - 100, 258, 200, 28).build());
        addRenderableWidget(Button.builder(Component.translatable("gui.back"),
                        b -> onClose())
                .bounds(center - 100, 294, 200, 20).build());
    }

    private Component modeLabel(int index) {
        return Component.literal((selectedMode == index ? "✓ " : "") + MODES[index]);
    }

    private Component weaponLabel(int index) {
        return Component.literal((selectedWeapon == index ? "✓ " : "") + WEAPONS[index]);
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

    private void selectDifficulty(int index) {
        selectedDifficulty = index;
        rebuildWidgets();
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {
        super.extractRenderState(graphics, mouseX, mouseY, partialTick);

        int center = this.width / 2;
        graphics.centeredText(font, title, center, 30, 0xFFFFFFFF);
        graphics.centeredText(font, Component.translatable("screen.pvp_practice.mode"),
                center, 58, 0xFFE0E0E0);
        graphics.centeredText(font, Component.translatable("screen.pvp_practice.weapon"),
                center, 115, 0xFFE0E0E0);
        graphics.centeredText(font, Component.translatable("screen.pvp_practice.difficulty"),
                center, 172, 0xFFE0E0E0);
        graphics.centeredText(font,
                Component.translatable("screen.pvp_practice.selection",
                        MODES[selectedMode], WEAPONS[selectedWeapon], DIFFICULTIES[selectedDifficulty]),
                center, 232, 0xFFA0A0A0);
        if (showNotReady) {
            graphics.centeredText(font, Component.translatable("screen.pvp_practice.not_ready"),
                    center, 330, 0xFFFF8080);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
