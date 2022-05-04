package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class NonDominantFootAction extends AbstractGameAction {

    private final AbstractPlayer player;
    private final int blockAmount;


    public NonDominantFootAction(AbstractPlayer p, int blockAmount) {
        this.player = p;
        this.blockAmount = blockAmount;
    }

    @Override
    public void update() {
        if (player.hasPower("Weakened")) {
            int weakAmount = player.getPower("Weakened").amount;
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, blockAmount * weakAmount));
        }
        if (player.hasPower("Frail")) {
            int frailAmount = player.getPower("Frail").amount;
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, blockAmount * frailAmount));
        }
        isDone = true;
    }
}
