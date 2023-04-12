package theUnchainedMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.util.TextureLoader;

import static theUnchainedMod.DefaultMod.makeRelicOutlinePath;
import static theUnchainedMod.DefaultMod.makeRelicPath;

public class Memento extends CustomRelic {

    public static final String ID = DefaultMod.makeID("Memento");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Memento_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Memento_relic.png"));
    private boolean alreadyBroken;

    public Memento() {
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

    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (target instanceof AbstractMonster && target.currentBlock > 0 && !alreadyBroken) {
            this.flash();
            this.addToBot(new RelicAboveCreatureAction(target, this));
            this.addToBot(new GainEnergyAction(1));
            alreadyBroken = true;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
