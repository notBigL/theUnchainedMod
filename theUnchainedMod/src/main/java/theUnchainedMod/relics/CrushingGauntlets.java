package theUnchainedMod.relics;

import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.PowerTip;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.util.TextureLoader;

import static theUnchainedMod.TheUnchainedMod.makeRelicOutlinePath;
import static theUnchainedMod.TheUnchainedMod.makeRelicPath;

public class CrushingGauntlets extends CustomRelic {

    public static final String ID = TheUnchainedMod.makeID("CrushingGauntlets");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Crushing_Gauntlets_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Crushing_Gauntlets_relic.png"));

    public CrushingGauntlets() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.CLINK);
        tips.add(new PowerTip(
                BaseMod.getKeywordTitle(DESCRIPTIONS[1].toLowerCase()),
                BaseMod.getKeywordDescription(DESCRIPTIONS[1].toLowerCase())
        ));
    }

    // Flash at the start of Battle.
    @Override
    public void atBattleStartPreDraw() {
        flash();
    }

    // Gain 1 energy on equip.
    @Override
    public void onEquip() {}

    // Lose 1 energy on unequip.
    @Override
    public void onUnequip() {}

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
