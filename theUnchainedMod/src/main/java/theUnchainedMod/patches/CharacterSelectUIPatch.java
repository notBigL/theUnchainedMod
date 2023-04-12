package theUnchainedMod.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.characters.TheUnchained;

public class CharacterSelectUIPatch {
    private static float UI_BUTTON_SCALE = 0.6f;
    private static int UI_MAX_LINE_WIDTH = 450;
    private static int UI_LINE_SPACING = 25;
    private static int SPACING_BETWEEN_BUTTONS = 40;
    private static int UI_BUTTON_DEFAULT_X = 170;

    //  Prince Unbound Button
    public static boolean princeUnboundButtonStatus = DefaultMod.unchainedConfig.getBool(DefaultMod.UNCHAINED_SKIN_ACTIVATED_PROPERTY);
    public static boolean princeUnboundUnlocked = false; // TODO: tie to heart kill!
    private static final int pu_xPos = UI_BUTTON_DEFAULT_X;
    private static final int pu_yPos = 1080-700;
    private static final int pu_width = 35;
    private static final int pu_height = 35;
    public static Hitbox pu_hitbox;

    //  Booster Pack Button
    public static boolean boosterPackButtonStatus = DefaultMod.unchainedConfig.getBool(DefaultMod.UNCHAINED_SKIN_ACTIVATED_PROPERTY);
    public static boolean boosterPackUnlocked = false; // TODO: tie to heart kill!
    private static final int booster_xPos = UI_BUTTON_DEFAULT_X;
    private static final int booster_yPos = pu_yPos - SPACING_BETWEEN_BUTTONS; //TODO: screensize dependant?
    private static final int booster_width = 35;
    private static final int booster_height = 35;
    public static Hitbox booster_hitbox;

    @SpirePatch2(clz = CharacterSelectScreen.class, method = "open")
    public static class OpenPatch{
        public static void Prefix(){
            pu_hitbox = new Hitbox(pu_xPos * Settings.scale, pu_yPos * Settings.scale, pu_width * Settings.scale, pu_height * Settings.scale);
            booster_hitbox = new Hitbox(booster_xPos * Settings.scale, booster_yPos * Settings.scale, booster_width * Settings.scale, booster_height * Settings.scale);
        }
    }

    @SpirePatch2(clz = CharacterSelectScreen.class, method = "render")
    public static class RenderPatch{
        public static void Postfix(CharacterSelectScreen __instance, SpriteBatch sb){
                for(CharacterOption o : __instance.options){
                    if(o.selected && o.c.chosenClass.equals(TheUnchained.Enums.THE_UNCHAINED)){
                        if(princeUnboundUnlocked || DefaultMod.UNCHAINED_OPTIONAL_CONTENT_UNLOCKED)
                        {
                            sb.draw(ImageMaster.CHECKBOX, pu_xPos * Settings.scale, pu_yPos * Settings.scale, pu_width * Settings.scale, pu_height * Settings.scale);
                            if (princeUnboundButtonStatus) sb.draw(ImageMaster.TICK, pu_xPos * Settings.scale, pu_yPos * Settings.scale, pu_width * Settings.scale, pu_height * Settings.scale);
                            FontHelper.renderSmartText(sb, FontHelper.menuBannerFont, "Use the 'Prince Unbound' skin.", (pu_xPos + pu_width + 5) * Settings.scale, (pu_yPos + pu_height) * Settings.scale, UI_MAX_LINE_WIDTH, UI_LINE_SPACING, (pu_hitbox.hovered ? Color.GOLD : Color.WHITE), UI_BUTTON_SCALE);
                            pu_hitbox.render(sb);
                        }
                        if(boosterPackUnlocked || DefaultMod.UNCHAINED_OPTIONAL_CONTENT_UNLOCKED)
                        {
                            sb.draw(ImageMaster.CHECKBOX, booster_xPos * Settings.scale, booster_yPos * Settings.scale, booster_width * Settings.scale, booster_height * Settings.scale);
                            if (boosterPackButtonStatus) sb.draw(ImageMaster.TICK, booster_xPos * Settings.scale, booster_yPos * Settings.scale, booster_width * Settings.scale, booster_height * Settings.scale);
                            FontHelper.renderSmartText(sb, FontHelper.menuBannerFont, "Activate the optional Booster Pack (Cards in Compendium).", (booster_xPos + booster_width + 5) * Settings.scale,(booster_yPos + booster_height) * Settings.scale, UI_MAX_LINE_WIDTH, UI_LINE_SPACING, (booster_hitbox.hovered ? Color.GOLD : Color.WHITE), UI_BUTTON_SCALE);
                            booster_hitbox.render(sb);
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
                        if(princeUnboundUnlocked || DefaultMod.UNCHAINED_OPTIONAL_CONTENT_UNLOCKED) {
                            pu_hitbox.update();
                            if ((pu_hitbox.hovered || pu_hitbox.justHovered) && InputHelper.justClickedLeft) {
                                try {
                                    princeUnboundButtonStatus = !princeUnboundButtonStatus;
                                    DefaultMod.unchainedConfig.setBool(DefaultMod.UNCHAINED_SKIN_ACTIVATED_PROPERTY, princeUnboundButtonStatus);
                                    DefaultMod.UNCHAINED_SKIN_ACTIVATED = princeUnboundButtonStatus;
                                    DefaultMod.unchainedConfig.save();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if(boosterPackUnlocked || DefaultMod.UNCHAINED_OPTIONAL_CONTENT_UNLOCKED) {
                            booster_hitbox.update();
                            if ((booster_hitbox.hovered || booster_hitbox.justHovered) && InputHelper.justClickedLeft) {
                                try {
                                    boosterPackButtonStatus = !boosterPackButtonStatus;
                                    DefaultMod.unchainedConfig.setBool(DefaultMod.UNCHAINED_BOOSTER_PACK_ACTIVATED_PROPERTY, boosterPackButtonStatus);
                                    DefaultMod.UNCHAINED_BOOSTER_PACK_ACTIVATED = boosterPackButtonStatus;
                                    DefaultMod.unchainedConfig.save();
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
