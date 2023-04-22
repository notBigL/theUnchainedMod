package theUnchainedMod.patches;

import basemod.BaseMod;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.characters.TheUnchained;
import theUnchainedMod.relics.CrushingGauntlets;
import theUnchainedMod.util.TextureLoader;

public class CharacterSelectUIPatch {
    private static float UI_TEXT_SCALE = 0.6f;
    private static int UI_MAX_LINE_WIDTH = 450;
    private static int UI_LINE_SPACING = 25;
    private static int SPACING_BETWEEN_BUTTONS = 40;
    private static int UI_BUTTON_DEFAULT_X = 170;
    private static int UI_FIRST_BUTTON_DEFAULT_Y = 1080-700;
    private static int UI_CURRENT_BUTTON_Y = UI_FIRST_BUTTON_DEFAULT_Y;


    //  Prince Unbound Button
    public static boolean princeUnboundButtonStatus = TheUnchainedMod.unchainedConfig.getBool(TheUnchainedMod.UNCHAINED_SKIN_ACTIVATED_PROPERTY);
    public static boolean princeUnboundUnlocked = false; // TODO: tie to heart kill!
    private static final int pu_xPos = UI_BUTTON_DEFAULT_X;
    //private static final int pu_yPos = UI_FIRST_BUTTON_DEFAULT_Y;
    private static final int pu_width = 35;
    private static final int pu_height = 35;
    public static Hitbox pu_hitbox;

    //  Booster Pack Button
    public static boolean boosterPackButtonStatus = TheUnchainedMod.unchainedConfig.getBool(TheUnchainedMod.UNCHAINED_SKIN_ACTIVATED_PROPERTY);
    public static boolean boosterPackUnlocked = true; // TODO: tie to heart kill!
    private static final int booster_xPos = UI_BUTTON_DEFAULT_X;
    //private static final int booster_yPos = pu_yPos - SPACING_BETWEEN_BUTTONS;
    private static final int booster_width = 35;
    private static final int booster_height = 35;
    public static Hitbox booster_hitbox;

    @SpirePatch2(clz = CharacterSelectScreen.class, method = "open")
    public static class OpenPatch{
        public static void Prefix(){
            UI_CURRENT_BUTTON_Y = UI_FIRST_BUTTON_DEFAULT_Y;

            if(princeUnboundUnlocked || TheUnchainedMod.UNCHAINED_OPTIONAL_CONTENT_UNLOCKED) {
                pu_hitbox = new Hitbox(pu_xPos * Settings.scale, UI_CURRENT_BUTTON_Y * Settings.scale, pu_width * Settings.scale, pu_height * Settings.scale);
                UI_CURRENT_BUTTON_Y -= SPACING_BETWEEN_BUTTONS;
            }
            if(boosterPackUnlocked || TheUnchainedMod.UNCHAINED_OPTIONAL_CONTENT_UNLOCKED) {
                booster_hitbox = new Hitbox(booster_xPos * Settings.scale, UI_CURRENT_BUTTON_Y * Settings.scale, booster_width * Settings.scale, booster_height * Settings.scale);
                UI_CURRENT_BUTTON_Y -= SPACING_BETWEEN_BUTTONS;
            }
        }
    }

    @SpirePatch2(clz = CharacterSelectScreen.class, method = "render")
    public static class RenderPatch{
        public static void Postfix(CharacterSelectScreen __instance, SpriteBatch sb){
            UI_CURRENT_BUTTON_Y = UI_FIRST_BUTTON_DEFAULT_Y;

                for(CharacterOption o : __instance.options){
                    if(o.selected && o.c.chosenClass.equals(TheUnchained.Enums.THE_UNCHAINED)){
                        if(princeUnboundUnlocked || TheUnchainedMod.UNCHAINED_OPTIONAL_CONTENT_UNLOCKED)
                        {
                            sb.draw(ImageMaster.CHECKBOX, pu_xPos * Settings.scale, UI_CURRENT_BUTTON_Y * Settings.scale, pu_width * Settings.scale, pu_height * Settings.scale);
                            if (princeUnboundButtonStatus) sb.draw(ImageMaster.TICK, pu_xPos * Settings.scale, UI_CURRENT_BUTTON_Y * Settings.scale, pu_width * Settings.scale, pu_height * Settings.scale);
                            FontHelper.renderSmartText(sb, FontHelper.menuBannerFont, "Use the 'Prince Unbound' skin.", (pu_xPos + pu_width + 5) * Settings.scale, (UI_CURRENT_BUTTON_Y + pu_height) * Settings.scale, UI_MAX_LINE_WIDTH, UI_LINE_SPACING, (pu_hitbox.hovered ? Color.GOLD : Color.WHITE), UI_TEXT_SCALE);
                            pu_hitbox.render(sb);
                            UI_CURRENT_BUTTON_Y -= SPACING_BETWEEN_BUTTONS;
                        }
                        if(boosterPackUnlocked || TheUnchainedMod.UNCHAINED_OPTIONAL_CONTENT_UNLOCKED)
                        {
                            sb.draw(ImageMaster.CHECKBOX, booster_xPos * Settings.scale, UI_CURRENT_BUTTON_Y * Settings.scale, booster_width * Settings.scale, booster_height * Settings.scale);
                            if (boosterPackButtonStatus) sb.draw(ImageMaster.TICK, booster_xPos * Settings.scale, UI_CURRENT_BUTTON_Y * Settings.scale, booster_width * Settings.scale, booster_height * Settings.scale);
                            FontHelper.renderSmartText(sb, FontHelper.menuBannerFont, "Activate the optional Booster Pack (Check Compendium for Cards and Relics).", (booster_xPos + booster_width + 5) * Settings.scale,(UI_CURRENT_BUTTON_Y + booster_height) * Settings.scale, UI_MAX_LINE_WIDTH, UI_LINE_SPACING, (booster_hitbox.hovered ? Color.GOLD : Color.WHITE), UI_TEXT_SCALE);
                            booster_hitbox.render(sb);
                            UI_CURRENT_BUTTON_Y -= SPACING_BETWEEN_BUTTONS;
                        }
                        break;
                    }
                }
            }
    }


    @SpirePatch2(clz = CharacterSelectScreen.class, method = "update")
    public static class UpdatePatch{
        public static void Postfix(CharacterSelectScreen __instance){
                for(CharacterOption o : __instance.options){
                    if(o.selected && o.c.chosenClass.equals(TheUnchained.Enums.THE_UNCHAINED)){
                        if(princeUnboundUnlocked || TheUnchainedMod.UNCHAINED_OPTIONAL_CONTENT_UNLOCKED) {
                            pu_hitbox.update();
                            if ((pu_hitbox.hovered || pu_hitbox.justHovered) && InputHelper.justClickedLeft) {
                                try {
                                    princeUnboundButtonStatus = !princeUnboundButtonStatus;
                                    TheUnchainedMod.unchainedConfig.setBool(TheUnchainedMod.UNCHAINED_SKIN_ACTIVATED_PROPERTY, princeUnboundButtonStatus);
                                    TheUnchainedMod.PRINCE_UNBOUND_SKIN_ACTIVATED = princeUnboundButtonStatus;

                                    if(princeUnboundButtonStatus) {
                                        __instance.bgCharImg = TextureLoader.getTexture(TheUnchainedMod.PRINCE_UNBOUND_PORTRAIT);
                                        BaseMod.playerPortraitMap.put(TheUnchained.Enums.THE_UNCHAINED, TheUnchainedMod.PRINCE_UNBOUND_PORTRAIT);
                                    }
                                    else
                                    {
                                        __instance.bgCharImg = TextureLoader.getTexture(TheUnchainedMod.THE_DEFAULT_PORTRAIT);
                                        BaseMod.playerPortraitMap.put(TheUnchained.Enums.THE_UNCHAINED, TheUnchainedMod.THE_DEFAULT_PORTRAIT);
                                    }
                                    TheUnchainedMod.unchainedConfig.save();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if(boosterPackUnlocked || TheUnchainedMod.UNCHAINED_OPTIONAL_CONTENT_UNLOCKED) {
                            booster_hitbox.update();
                            if ((booster_hitbox.hovered || booster_hitbox.justHovered) && InputHelper.justClickedLeft) {
                                try {
                                    boosterPackButtonStatus = !boosterPackButtonStatus;
                                    TheUnchainedMod.unchainedConfig.setBool(TheUnchainedMod.UNCHAINED_BOOSTER_PACK_ACTIVATED_PROPERTY, boosterPackButtonStatus);
                                    TheUnchainedMod.UNCHAINED_BOOSTER_PACK_ACTIVATED = boosterPackButtonStatus;

                                    if(boosterPackButtonStatus) {
                                        if (!BaseMod.getRelicsInCustomPool(TheUnchained.Enums.COLOR_ORANGE).containsKey(CrushingGauntlets.ID))
                                            BaseMod.addRelicToCustomPool(new CrushingGauntlets(), TheUnchained.Enums.COLOR_ORANGE);
                                    }
                                    else
                                    {
                                        if (BaseMod.getRelicsInCustomPool(TheUnchained.Enums.COLOR_ORANGE).containsKey(CrushingGauntlets.ID))
                                            BaseMod.removeRelicFromCustomPool(BaseMod.getRelicsInCustomPool(TheUnchained.Enums.COLOR_ORANGE).get(CrushingGauntlets.ID), TheUnchained.Enums.COLOR_ORANGE);
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
}
