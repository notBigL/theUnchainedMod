package theUnchainedMod.patches;

import basemod.BaseMod;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.characters.TheUnchained;
import theUnchainedMod.relics.CrushingGauntlets;
import theUnchainedMod.util.TextureLoader;


public class CharacterSelectUIPatch {

    public static final String[] birthdaySkinText;
    public static final String[] unboundSkinText;
    public static final String[] boosterPackText;

    private static float UI_TEXT_SCALE = 0.6f;
    private static int UI_MAX_LINE_WIDTH = 450;
    private static int UI_LINE_SPACING = 25;
    private static int SPACING_BETWEEN_BUTTONS = 40;
    private static int UI_BUTTON_DEFAULT_X = 170;
    private static int UI_FIRST_BUTTON_DEFAULT_Y = 1080 - 700;
    private static int UI_CURRENT_BUTTON_Y = UI_FIRST_BUTTON_DEFAULT_Y;


    //  Birthday Boy Button
    public static boolean birthdayButtonStatus = TheUnchainedMod.unchainedConfig.getBool(TheUnchainedMod.BIRTHDAY_SKIN_ACTIVATED_PROPERTY);
    private static final int birthday_XPos = UI_BUTTON_DEFAULT_X;
    private static final int birthday_width = 35;
    private static final int birthday_height = 35;
    public static Hitbox birthday_hitbox;

    //  Prince Unbound Button
    public static boolean princeUnboundButtonStatus = TheUnchainedMod.unchainedConfig.getBool(TheUnchainedMod.PRINCE_UNBOUND_SKIN_ACTIVATED_PROPERTY);
    private static final int pu_xPos = UI_BUTTON_DEFAULT_X;
    private static final int pu_width = 35;
    private static final int pu_height = 35;
    public static Hitbox pu_hitbox;

    //  Booster Pack Button
    public static boolean boosterPackButtonStatus = TheUnchainedMod.unchainedConfig.getBool(TheUnchainedMod.UNCHAINED_BOOSTER_PACK_ACTIVATED_PROPERTY);
    private static final int booster_xPos = UI_BUTTON_DEFAULT_X;
    private static final int booster_width = 35;
    private static final int booster_height = 35;
    public static Hitbox booster_hitbox;

    @SpirePatch2(clz = CharacterSelectScreen.class, method = "open")
    public static class OpenPatch {
        public static void Prefix() {
            UI_CURRENT_BUTTON_Y = UI_FIRST_BUTTON_DEFAULT_Y;
            birthday_hitbox = new Hitbox(birthday_XPos * Settings.scale, UI_CURRENT_BUTTON_Y * Settings.scale, birthday_width * Settings.scale, birthday_height * Settings.scale);
            UI_CURRENT_BUTTON_Y -= SPACING_BETWEEN_BUTTONS;

            if (TheUnchainedMod.PrinceUnboundUnlocked() || TheUnchainedMod.ContentUnlocked()) {
                pu_hitbox = new Hitbox(pu_xPos * Settings.scale, UI_CURRENT_BUTTON_Y * Settings.scale, pu_width * Settings.scale, pu_height * Settings.scale);
                UI_CURRENT_BUTTON_Y -= SPACING_BETWEEN_BUTTONS;
            }
            if (TheUnchainedMod.BoosterpackUnlocked() || TheUnchainedMod.ContentUnlocked()) {
                booster_hitbox = new Hitbox(booster_xPos * Settings.scale, UI_CURRENT_BUTTON_Y * Settings.scale, booster_width * Settings.scale, booster_height * Settings.scale);
                UI_CURRENT_BUTTON_Y -= SPACING_BETWEEN_BUTTONS;
            }
        }
    }

    @SpirePatch2(clz = CharacterSelectScreen.class, method = "render")
    public static class RenderPatch {
        public static void Postfix(CharacterSelectScreen __instance, SpriteBatch sb) {
            UI_CURRENT_BUTTON_Y = UI_FIRST_BUTTON_DEFAULT_Y;

            for (CharacterOption o : __instance.options) {
                if (o.selected && o.c.chosenClass.equals(TheUnchained.Enums.THE_UNCHAINED)) {

                    sb.draw(ImageMaster.CHECKBOX, birthday_XPos * Settings.scale, UI_CURRENT_BUTTON_Y * Settings.scale, birthday_width * Settings.scale, birthday_height * Settings.scale);
                    if (birthdayButtonStatus)
                        sb.draw(ImageMaster.TICK, birthday_XPos * Settings.scale, UI_CURRENT_BUTTON_Y * Settings.scale, birthday_width * Settings.scale, birthday_height * Settings.scale);
                    FontHelper.renderSmartText(sb, FontHelper.menuBannerFont, birthdaySkinText[0], (birthday_XPos + birthday_width + 5) * Settings.scale, (UI_CURRENT_BUTTON_Y + birthday_height) * Settings.scale, UI_MAX_LINE_WIDTH, UI_LINE_SPACING, (birthday_hitbox.hovered ? Color.GOLD : Color.WHITE), UI_TEXT_SCALE);
                    birthday_hitbox.render(sb);
                    UI_CURRENT_BUTTON_Y -= SPACING_BETWEEN_BUTTONS;

                    if (TheUnchainedMod.PrinceUnboundUnlocked() || TheUnchainedMod.ContentUnlocked()) {
                        sb.draw(ImageMaster.CHECKBOX, pu_xPos * Settings.scale, UI_CURRENT_BUTTON_Y * Settings.scale, pu_width * Settings.scale, pu_height * Settings.scale);
                        if (princeUnboundButtonStatus)
                            sb.draw(ImageMaster.TICK, pu_xPos * Settings.scale, UI_CURRENT_BUTTON_Y * Settings.scale, pu_width * Settings.scale, pu_height * Settings.scale);
                        FontHelper.renderSmartText(sb, FontHelper.menuBannerFont, unboundSkinText[0], (pu_xPos + pu_width + 5) * Settings.scale, (UI_CURRENT_BUTTON_Y + pu_height) * Settings.scale, UI_MAX_LINE_WIDTH, UI_LINE_SPACING, (pu_hitbox.hovered ? Color.GOLD : Color.WHITE), UI_TEXT_SCALE);
                        pu_hitbox.render(sb);
                        UI_CURRENT_BUTTON_Y -= SPACING_BETWEEN_BUTTONS;
                    }
                    if (TheUnchainedMod.BoosterpackUnlocked() || TheUnchainedMod.ContentUnlocked()) {
                        sb.draw(ImageMaster.CHECKBOX, booster_xPos * Settings.scale, UI_CURRENT_BUTTON_Y * Settings.scale, booster_width * Settings.scale, booster_height * Settings.scale);
                        if (boosterPackButtonStatus)
                            sb.draw(ImageMaster.TICK, booster_xPos * Settings.scale, UI_CURRENT_BUTTON_Y * Settings.scale, booster_width * Settings.scale, booster_height * Settings.scale);
                        FontHelper.renderSmartText(sb, FontHelper.menuBannerFont, boosterPackText[0], (booster_xPos + booster_width + 5) * Settings.scale, (UI_CURRENT_BUTTON_Y + booster_height) * Settings.scale, UI_MAX_LINE_WIDTH, UI_LINE_SPACING, (booster_hitbox.hovered ? Color.GOLD : Color.WHITE), UI_TEXT_SCALE);
                        booster_hitbox.render(sb);
                        UI_CURRENT_BUTTON_Y -= SPACING_BETWEEN_BUTTONS;
                    }
                    break;
                }
            }
        }
    }


    @SpirePatch2(clz = CharacterSelectScreen.class, method = "update")
    public static class UpdatePatch {
        public static void Postfix(CharacterSelectScreen __instance) {
            for (CharacterOption o : __instance.options) {
                if (o.selected && o.c.chosenClass.equals(TheUnchained.Enums.THE_UNCHAINED)) {

                    //  Birthday skin

                    birthday_hitbox.update();
                    if ((birthday_hitbox.hovered || birthday_hitbox.justHovered) && InputHelper.justClickedLeft) {
                        try {
                            birthdayButtonStatus = !birthdayButtonStatus;
                            TheUnchainedMod.unchainedConfig.setBool(TheUnchainedMod.BIRTHDAY_SKIN_ACTIVATED_PROPERTY, birthdayButtonStatus);

                            //  turn off prince unbound skin button
                            princeUnboundButtonStatus = false;
                            TheUnchainedMod.unchainedConfig.setBool(TheUnchainedMod.PRINCE_UNBOUND_SKIN_ACTIVATED_PROPERTY, princeUnboundButtonStatus);

                            __instance.bgCharImg = TextureLoader.getTexture(TheUnchainedMod.THE_DEFAULT_PORTRAIT);
                            BaseMod.playerPortraitMap.put(TheUnchained.Enums.THE_UNCHAINED, TheUnchainedMod.THE_DEFAULT_PORTRAIT);

                            TheUnchainedMod.unchainedConfig.save();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    //  Prince Unbound Skin

                    if (TheUnchainedMod.PrinceUnboundUnlocked() || TheUnchainedMod.ContentUnlocked()) {
                        pu_hitbox.update();
                        if ((pu_hitbox.hovered || pu_hitbox.justHovered) && InputHelper.justClickedLeft) {
                            try {
                                princeUnboundButtonStatus = !princeUnboundButtonStatus;
                                TheUnchainedMod.unchainedConfig.setBool(TheUnchainedMod.PRINCE_UNBOUND_SKIN_ACTIVATED_PROPERTY, princeUnboundButtonStatus);

                                // turn off birthday skin button
                                birthdayButtonStatus = false;
                                TheUnchainedMod.unchainedConfig.setBool(TheUnchainedMod.BIRTHDAY_SKIN_ACTIVATED_PROPERTY, birthdayButtonStatus);

                                if (princeUnboundButtonStatus) {
                                    __instance.bgCharImg = TextureLoader.getTexture(TheUnchainedMod.PRINCE_UNBOUND_PORTRAIT);
                                    BaseMod.playerPortraitMap.put(TheUnchained.Enums.THE_UNCHAINED, TheUnchainedMod.PRINCE_UNBOUND_PORTRAIT);

                                } else {
                                    __instance.bgCharImg = TextureLoader.getTexture(TheUnchainedMod.THE_DEFAULT_PORTRAIT);
                                    BaseMod.playerPortraitMap.put(TheUnchained.Enums.THE_UNCHAINED, TheUnchainedMod.THE_DEFAULT_PORTRAIT);
                                }
                                TheUnchainedMod.unchainedConfig.save();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    //  Booster Pack

                    if (TheUnchainedMod.BoosterpackUnlocked() || TheUnchainedMod.ContentUnlocked()) {
                        booster_hitbox.update();
                        if ((booster_hitbox.hovered || booster_hitbox.justHovered) && InputHelper.justClickedLeft) {
                            try {
                                boosterPackButtonStatus = !boosterPackButtonStatus;
                                TheUnchainedMod.unchainedConfig.setBool(TheUnchainedMod.UNCHAINED_BOOSTER_PACK_ACTIVATED_PROPERTY, boosterPackButtonStatus);

                                if (boosterPackButtonStatus) {
                                    if (!BaseMod.getRelicsInCustomPool(TheUnchained.Enums.UNCHAINED_COLOR).containsKey(CrushingGauntlets.ID))
                                        BaseMod.addRelicToCustomPool(new CrushingGauntlets(), TheUnchained.Enums.UNCHAINED_COLOR);
                                } else {
                                    if (BaseMod.getRelicsInCustomPool(TheUnchained.Enums.UNCHAINED_COLOR).containsKey(CrushingGauntlets.ID))
                                        BaseMod.removeRelicFromCustomPool(BaseMod.getRelicsInCustomPool(TheUnchained.Enums.UNCHAINED_COLOR).get(CrushingGauntlets.ID), TheUnchained.Enums.UNCHAINED_COLOR);
                                }
                                TheUnchainedMod.unchainedConfig.save();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break; //stop looking at new chars
                }
            }
        }
    }

    static {
        birthdaySkinText = CardCrawlGame.languagePack.getUIString("theUnchainedMod:BirthdaySkin").TEXT;
        unboundSkinText = CardCrawlGame.languagePack.getUIString("theUnchainedMod:UnboundSkin").TEXT;
        boosterPackText = CardCrawlGame.languagePack.getUIString("theUnchainedMod:BoosterPack").TEXT;
    }
}


