package theUnchainedMod.patches;


import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;
import static theUnchainedMod.TheUnchainedMod.makePotionPath;

@SpirePatch(clz = AbstractPotion.class, method = "initializeImage"
)
public class CustomPotionAssets {

    private static final Logger logger = LogManager.getLogger(CustomPotionAssets.class.getName());


    @SpirePostfixPatch()
    public static void checkForCustomSizes(AbstractPotion potion, @ByRef Texture[] ___containerImg, @ByRef Texture[] ___liquidImg, @ByRef Texture[] ___hybridImg, @ByRef Texture[] ___spotsImg, @ByRef Texture[] ___outlineImg) {
        if (potion.size == CustomPotionEnums.DANCE) {
            ___containerImg[0] = loadImage(makePotionPath("dancePotion/body.png"));
            ___liquidImg[0] = loadImage(makePotionPath("dancePotion/liquid.png"));
            ___hybridImg[0] = loadImage(makePotionPath("dancePotion/hybrid.png"));
            ___spotsImg[0] = loadImage(makePotionPath("dancePotion/spots.png"));
            ___outlineImg[0] = loadImage(makePotionPath("dancePotion/outline.png"));
        } else if(potion.size == CustomPotionEnums.CRUSH) {
            ___containerImg[0] = loadImage(makePotionPath("crushPotion/body.png"));
            ___liquidImg[0] = loadImage(makePotionPath("crushPotion/liquid.png"));
            ___hybridImg[0] = loadImage(makePotionPath("crushPotion/hybrid.png"));
            ___spotsImg[0] = loadImage(makePotionPath("crushPotion/empty.png"));
            ___outlineImg[0] = loadImage(makePotionPath("crushPotion/outline.png"));
        } else if(potion.size == CustomPotionEnums.LINK) {
            ___containerImg[0] = loadImage(makePotionPath("linkPotion/body.png"));
            ___liquidImg[0] = loadImage(makePotionPath("linkPotion/liquid.png"));
            ___hybridImg[0] = loadImage(makePotionPath("linkPotion/hybrid.png"));
            ___spotsImg[0] = loadImage(makePotionPath("linkPotion/empty.png"));
            ___outlineImg[0] = loadImage(makePotionPath("linkPotion/outline.png"));
        }
    }
}
