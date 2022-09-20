package theUnchainedMod.patches;


import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;
import static theUnchainedMod.DefaultMod.makePotionPath;

@SpirePatch(clz = AbstractPotion.class, method = "initializeImage"
)
public class DancePotionAssets {

    private static final Logger logger = LogManager.getLogger(DancePotionAssets.class.getName());


    @SpirePostfixPatch()
    public static void checkForSkirtSize(AbstractPotion potion, @ByRef Texture[] ___containerImg, @ByRef Texture[] ___liquidImg, @ByRef Texture[] ___hybridImg, @ByRef Texture[] ___spotsImg, @ByRef Texture[] ___outlineImg) {
        if (potion.size == SkirtPotionEnum.SKIRT) {
            logger.info("I AM HERE");
            ___containerImg[0] = loadImage(makePotionPath("body.png"));
            ___liquidImg[0] = loadImage(makePotionPath("liquid.png"));
            ___hybridImg[0] = loadImage(makePotionPath("hybrid.png"));
            ___spotsImg[0] = loadImage(makePotionPath("spots.png"));
            ___outlineImg[0] = loadImage(makePotionPath("outline.png"));
        }
    }
}
