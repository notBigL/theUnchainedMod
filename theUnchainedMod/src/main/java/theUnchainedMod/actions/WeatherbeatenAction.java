package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theUnchainedMod.powers.ToughnessPower;

public class WeatherbeatenAction extends AbstractGameAction {
    private final boolean freeToPlayOnce;
    private final int energyOnUse;
    private final boolean upgraded;
    private final AbstractPlayer player;

    public WeatherbeatenAction(AbstractPlayer p, boolean upgraded, boolean freeToPlayOnce, int energyOnUse) {
        this.player = p;
        this.freeToPlayOnce = freeToPlayOnce;
        this.upgraded = upgraded;
        this.energyOnUse = energyOnUse;
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
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(player, player, new ToughnessPower(player, effect)));
        }

        if (!this.freeToPlayOnce) {
            this.player.energy.use(EnergyPanel.totalCount);
        }

        this.isDone = true;
    }
}