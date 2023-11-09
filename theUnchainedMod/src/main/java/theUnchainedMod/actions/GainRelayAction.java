package theUnchainedMod.actions;

import basemod.devcommands.relic.Relic;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import theUnchainedMod.patches.RelayHelpers;
import theUnchainedMod.powers.ArcaneMasteryPower;
import theUnchainedMod.powers.GlyphBrandPower;
import theUnchainedMod.powers.RelayPower;
import theUnchainedMod.relics.ArcaneAmplifier;

public class GainRelayAction extends AbstractGameAction {
    public GainRelayAction(AbstractCreature target, int amount) {
        this.amount = amount;
        this.target = target;
    }

    @Override
    public void update() {
        if (target != null && target.isDying && !target.isDead) {
            RelayHelpers.addRelay(amount, target);
            CardCrawlGame.sound.playA("relayApply", MathUtils.random(-0.2F, 0.2F));

            if (target instanceof AbstractPlayer) {
                for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (!mo.isDeadOrEscaped() && mo.hasPower(GlyphBrandPower.POWER_ID)) {
                        mo.getPower(GlyphBrandPower.POWER_ID).onSpecificTrigger();
                    }
                }
                if (((AbstractPlayer) target).hasRelic(ArcaneAmplifier.ID)) {
                    for (AbstractRelic r : AbstractDungeon.player.relics) {
                        if (r instanceof ArcaneAmplifier) r.onTrigger();
                    }
                }
            }
        }
        this.isDone = true;
    }
}
