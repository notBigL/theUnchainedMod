package theUnchainedMod.patches;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.vfx.FlashPotionEffect;

import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;
import static theUnchainedMod.DefaultMod.makePotionPath;

@SpirePatch(clz = FlashPotionEffect.class, method = SpirePatch.CONSTRUCTOR)
public class SkirtPotionFlashPatch {

    @SpirePostfixPatch()
    public static void addSkirtEnumToSwitchStatement(FlashPotionEffect fPE, AbstractPotion p, @ByRef Texture[] ___containerImg, @ByRef Texture[] ___liquidImg, @ByRef Texture[] ___hybridImg, @ByRef Texture[] ___spotsImg) {
        if (p.size == SkirtPotionEnum.SKIRT) {
            ___containerImg[0] = loadImage(makePotionPath("body.png"));
            ___liquidImg[0] = loadImage(makePotionPath("liquid.png"));
            ___hybridImg[0] = loadImage(makePotionPath("hybrid.png"));
            ___spotsImg[0] = loadImage(makePotionPath("spots.png"));
        }
    }
}
