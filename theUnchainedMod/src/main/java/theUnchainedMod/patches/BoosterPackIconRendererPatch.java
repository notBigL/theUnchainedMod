package theUnchainedMod.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import theUnchainedMod.cards.AbstractDynamicBoosterPackCard;
import theUnchainedMod.cards.AbstractDynamicBoosterPackRelayCard;
import theUnchainedMod.util.TextureLoader;

import static theUnchainedMod.TheUnchainedMod.makeCardPath;

public class BoosterPackIconRendererPatch {
    private static final Texture boosterPackTexture = TextureLoader.getTexture(makeCardPath("BoosterPackCardCorner.png"));
    public static TextureAtlas.AtlasRegion boosterPackImage = new TextureAtlas.AtlasRegion(boosterPackTexture, 0, 0, boosterPackTexture.getHeight(), boosterPackTexture.getWidth());
    @SpirePatch2(clz = AbstractCard.class, method = "renderImage")
    public static class BoosterPackCardZoomedOut {

        public static void Postfix(AbstractCard __instance, SpriteBatch sb) {
            if (__instance instanceof AbstractDynamicBoosterPackCard || __instance instanceof AbstractDynamicBoosterPackRelayCard) {
                if (CardCrawlGame.mainMenuScreen.screen == MainMenuScreen.CurScreen.CARD_LIBRARY) {
                    if (boosterPackImage != null) {
                        sb.setColor(Color.WHITE.cpy());
                        sb.setColor(Color.WHITE.cpy());
                        sb.draw(boosterPackImage,
                                __instance.current_x - (float) boosterPackImage.originalWidth / 2.0F,
                                __instance.current_y - (float) boosterPackImage.originalHeight / 2.0F,
                                (float) boosterPackImage.originalWidth / 2.0F, (float) boosterPackImage.originalHeight / 2.0F, (float) boosterPackImage.originalWidth, (float) boosterPackImage.originalHeight,
                                __instance.drawScale * Settings.scale,
                                __instance.drawScale * Settings.scale,
                                __instance.angle);
                    }
                }
            }
        }
    }
    @SpirePatch2(clz = SingleCardViewPopup.class, method = "renderTitle")
    public static class BoosterPackZoomedIn {
        public static void Prefix(SingleCardViewPopup __instance, SpriteBatch sb) {
            AbstractCard card = ReflectionHacks.getPrivate(__instance, SingleCardViewPopup.class, "card");
            if (card instanceof AbstractDynamicBoosterPackCard || card instanceof AbstractDynamicBoosterPackRelayCard) {
                if (CardCrawlGame.mainMenuScreen.screen == MainMenuScreen.CurScreen.CARD_LIBRARY) {
                    if (boosterPackImage != null) {
                        sb.setColor(Color.WHITE.cpy());
                        sb.draw(boosterPackTexture, (float) Settings.WIDTH / 2.0F - 256, (float) Settings.HEIGHT / 2.0F - 256, 256, 256, 512, 512, Settings.scale * 2, Settings.scale * 2, 0.0F, 0, 0, 512, 512, false, false);
                    }
                }
            }
        }
    }
}