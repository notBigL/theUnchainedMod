package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class RoyalGiftAction extends AbstractGameAction {
    private final boolean freeToPlayOnce;
    private final int energyOnUse;
    private final boolean upgraded;
    private final AbstractPlayer player;
    private final int blockAmount;

    public RoyalGiftAction(AbstractPlayer p, AbstractCreature monster, boolean upgraded, boolean freeToPlayOnce, int energyOnUse, int blockAmount) {
        this.player = p;
        this.freeToPlayOnce = freeToPlayOnce;
        this.upgraded = upgraded;
        this.energyOnUse = energyOnUse;
        this.blockAmount = blockAmount;
        this.target = monster;
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
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(target, blockAmount));
            }
        }
        if (!this.freeToPlayOnce) {
            this.player.energy.use(EnergyPanel.totalCount);
        }
        this.isDone = true;
    }
}
