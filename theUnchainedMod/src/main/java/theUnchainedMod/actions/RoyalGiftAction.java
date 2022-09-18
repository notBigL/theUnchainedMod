package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class RoyalGiftAction extends AbstractGameAction {
    private final boolean freeToPlayOnce;
    private final int energyOnUse;
    private final boolean upgraded;
    private final AbstractPlayer player;
    private final int blockAmount;

    public RoyalGiftAction(AbstractPlayer p, boolean upgraded, boolean freeToPlayOnce, int energyOnUse, int blockAmount) {
        this.player = p;
        this.freeToPlayOnce = freeToPlayOnce;
        this.upgraded = upgraded;
        this.energyOnUse = energyOnUse;
        this.blockAmount = blockAmount;
    }

    @Override
    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }
        if (this.player.hasRelic("Chemical X")) {
            effect += 2;
            this.player.getRelic("Chemical X").flash();
        }
        if (this.upgraded) {
            ++effect;
        }
        if (effect > 0) {
            for (int i = 0; i < effect; i++) {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, blockAmount));
                for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (!mo.isDead) {
                        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(mo, blockAmount));
                    }
                }
            }
        }
        if (!this.freeToPlayOnce) {
            this.player.energy.use(EnergyPanel.totalCount);
        }
        this.isDone = true;
    }
}
