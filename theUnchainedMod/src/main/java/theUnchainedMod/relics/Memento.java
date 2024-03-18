package theUnchainedMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.util.TextureLoader;

import static theUnchainedMod.TheUnchainedMod.makeRelicOutlinePath;
import static theUnchainedMod.TheUnchainedMod.makeRelicPath;

public class Memento extends CustomRelic {

    public static final String ID = TheUnchainedMod.makeID("Memento");

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Memento_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Memento_relic.png"));
    private boolean alreadyBroken;

    public Memento() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    public void atTurnStart() {
        alreadyBroken = false;
    }

/*
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
    }*/

    @Override
    public void onUseCard(AbstractCard c, UseCardAction action) {
        if (c.type == AbstractCard.CardType.ATTACK && action.target != null) {
            if (action.target instanceof AbstractMonster && action.target.currentBlock > 0 && !alreadyBroken) {
                this.flash();
                this.addToBot(new RelicAboveCreatureAction(action.target, this));
                this.addToBot(new GainEnergyAction(1));
                alreadyBroken = true;
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
