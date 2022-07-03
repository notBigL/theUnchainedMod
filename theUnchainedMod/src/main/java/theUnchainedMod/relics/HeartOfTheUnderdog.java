package theUnchainedMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.powers.MaladyPower;
import theUnchainedMod.util.TextureLoader;

import static theUnchainedMod.DefaultMod.makeRelicOutlinePath;
import static theUnchainedMod.DefaultMod.makeRelicPath;

public class HeartOfTheUnderdog extends CustomRelic {

    public static final String ID = DefaultMod.makeID("HeartOfTheUnderdog");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("HeartOfTheUnderdog_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("HeartOfTheUnderdog_relic.png"));
    private boolean alreadyBroken;

    public HeartOfTheUnderdog() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    public void atTurnStart() {
        alreadyBroken = false;
    }

    public void onBlockBroken(AbstractCreature m) {
        if (!alreadyBroken) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(m, this));
            this.addToBot(new GainEnergyAction(1));
            alreadyBroken = true;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
