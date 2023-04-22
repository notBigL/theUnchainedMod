package theUnchainedMod.patches;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.vfx.FlashPotionEffect;

import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;
import static theUnchainedMod.TheUnchainedMod.makePotionPath;

@SpirePatch(clz = FlashPotionEffect.class, method = SpirePatch.CONSTRUCTOR)
public class CustomPotionsFlashPatch {

    @SpirePostfixPatch()
    public static void addCustomEnumsToSwitchStatement(FlashPotionEffect fPE, AbstractPotion p, @ByRef Texture[] ___containerImg, @ByRef Texture[] ___liquidImg, @ByRef Texture[] ___hybridImg, @ByRef Texture[] ___spotsImg) {
        if (p.size == CustomPotionEnums.DANCE) {
            ___containerImg[0] = loadImage(makePotionPath("dancePotion/body.png"));
            ___liquidImg[0] = loadImage(makePotionPath("dancePotion/liquid.png"));
            ___hybridImg[0] = loadImage(makePotionPath("dancePotion/hybrid.png"));
            ___spotsImg[0] = loadImage(makePotionPath("dancePotion/spots.png"));
        } else if(p.size == CustomPotionEnums.CRUSH) {
            ___containerImg[0] = loadImage(makePotionPath("crushPotion/body.png"));
            ___liquidImg[0] = loadImage(makePotionPath("crushPotion/liquid.png"));
            ___hybridImg[0] = loadImage(makePotionPath("crushPotion/hybrid.png"));
            ___spotsImg[0] = loadImage(makePotionPath("crushPotion/spots.png"));
        }else if(p.size == CustomPotionEnums.LINK) {
            ___containerImg[0] = loadImage(makePotionPath("linkPotion/body.png"));
            ___liquidImg[0] = loadImage(makePotionPath("linkPotion/liquid.png"));
            ___hybridImg[0] = loadImage(makePotionPath("linkPotion/hybrid.png"));
            ___spotsImg[0] = loadImage(makePotionPath("linkPotion/spots.png"));
        }
    }
}
