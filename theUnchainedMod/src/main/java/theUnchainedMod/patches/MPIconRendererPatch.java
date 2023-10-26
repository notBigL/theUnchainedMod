package theUnchainedMod.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import theUnchainedMod.cards.AbstractDynamicBoosterPackCard;
import theUnchainedMod.cards.AbstractDynamicBoosterPackRelayCard;
import theUnchainedMod.util.TextureLoader;

import static theUnchainedMod.TheUnchainedMod.makeCardPath;

@SpirePatch2(clz = AbstractCard.class, method = "renderImage")
public class MPIconRendererPatch{
    private static Texture texture = TextureLoader.getTexture(makeCardPath("BoosterPackCardCorner.png"));
    public static TextureAtlas.AtlasRegion cardMpImage = new TextureAtlas.AtlasRegion(texture, 0, 0, texture.getHeight(), texture.getWidth());
    public static void Postfix(AbstractCard __instance, SpriteBatch sb){
        if(__instance instanceof AbstractDynamicBoosterPackCard || __instance instanceof AbstractDynamicBoosterPackRelayCard){
            if(CardCrawlGame.mainMenuScreen.screen == MainMenuScreen.CurScreen.CARD_LIBRARY){
                if(cardMpImage != null){
                    sb.setColor(Color.WHITE.cpy());
                    sb.setColor(Color.WHITE.cpy());
                    sb.draw(cardMpImage,
                            __instance.current_x - (float)cardMpImage.originalWidth / 2.0F,
                            __instance.current_y - (float)cardMpImage.originalHeight / 2.0F,
                            (float)cardMpImage.originalWidth / 2.0F, (float)cardMpImage.originalHeight / 2.0F, (float)cardMpImage.originalWidth, (float)cardMpImage.originalHeight,
                            __instance.drawScale * Settings.scale,
                            __instance.drawScale * Settings.scale,
                            __instance.angle);}
            }
        }
    }
}