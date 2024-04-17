package theUnchainedMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theUnchainedMod.powers.AbstractChainPower;

import java.util.UUID;

public class ChainSawAction extends AbstractGameAction {

    private final UUID cardID;

    public ChainSawAction(UUID uuid, int amountPerChain) {
        this.amount = amountPerChain;
        this.cardID = uuid;
    }

    @Override
    public void update() {
        int amountToAdd = 0;

        for (AbstractPower abstractPower : AbstractDungeon.player.powers) {
            if (abstractPower instanceof AbstractChainPower) {
                amountToAdd += amount;
            }
        }
        if (amountToAdd > 0) {
            AbstractDungeon.actionManager.addToBottom(new ModifyDamageAction(cardID, amountToAdd));
        }
        isDone = true;
    }
}
