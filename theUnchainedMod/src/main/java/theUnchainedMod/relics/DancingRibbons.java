package theUnchainedMod.relics;

import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.cards.Swirl;
import theUnchainedMod.util.TextureLoader;

import static theUnchainedMod.TheUnchainedMod.makeRelicOutlinePath;
import static theUnchainedMod.TheUnchainedMod.makeRelicPath;

public class DancingRibbons extends CustomRelic {
    public static final String ID = TheUnchainedMod.makeID("DancingRibbons");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("DancingRibbons_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("DancingRibbons_relic.png"));

    public DancingRibbons() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.FLAT);
        tips.add(new PowerTip(
                BaseMod.getKeywordTitle(DESCRIPTIONS[1].toLowerCase()),
                BaseMod.getKeywordDescription(DESCRIPTIONS[1].toLowerCase())
        ));
        tips.add(new PowerTip(
                BaseMod.getKeywordTitle(DESCRIPTIONS[2].toLowerCase()),
                BaseMod.getKeywordDescription(DESCRIPTIONS[2].toLowerCase())
        ));
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if(c instanceof Swirl)
        {
            this.flash();
        }
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
