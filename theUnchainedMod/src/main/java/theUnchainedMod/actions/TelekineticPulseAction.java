package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import theUnchainedMod.powers.TelekineticPulsePower;
import theUnchainedMod.vfx.TelekineticPulseWaveEffect;

public class TelekineticPulseAction extends AbstractGameAction {
    private final int damageAmount;
    private final int blockAmount;

    public TelekineticPulseAction(int damageAmount, int blockAmount) {
        this.damageAmount = damageAmount;
        this.blockAmount = blockAmount;
    }
    @Override
    public void update() {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new TelekineticPulseWaveEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, 1000.0F * Settings.scale)));
        AbstractDungeon.actionManager.addToBottom(new AllEnemiesGainBlockAction(blockAmount));
        AbstractDungeon.actionManager.addToBottom(new AllEnemiesLoseHPAction(damageAmount));

        if (!AbstractDungeon.getMonsters().areMonstersDead()) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new TelekineticPulsePower(AbstractDungeon.player, 1, new TelekineticPulseAction(damageAmount, blockAmount), damageAmount, blockAmount, AbstractCard.CardType.SKILL)));
        }
        this.isDone = true;
    }
}
