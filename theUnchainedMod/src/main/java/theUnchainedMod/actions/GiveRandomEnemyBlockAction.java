package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class GiveRandomEnemyBlockAction extends AbstractGameAction {
    private final int blockAmount;

    public GiveRandomEnemyBlockAction(int blockAmount) {
        this.blockAmount = blockAmount;
    }

    public void update() {
        this.target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if (this.target != null) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.target, blockAmount));
        }
        this.isDone = true;
    }
}
