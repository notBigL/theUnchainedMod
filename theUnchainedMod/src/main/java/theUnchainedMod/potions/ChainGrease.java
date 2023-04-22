package theUnchainedMod.potions;

import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.actions.RemoveRelayedDamageAction;
import theUnchainedMod.patches.CustomPotionEnums;
import theUnchainedMod.powers.AbstractChainPower;

public class ChainGrease extends CustomPotion {

    public static final String POTION_ID = TheUnchainedMod.makeID("ChainGrease");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public ChainGrease() {
        super(NAME, POTION_ID, PotionRarity.COMMON, CustomPotionEnums.LINK, PotionColor.SMOKE);
        isThrown = false;
    }

    @Override
    public void use(AbstractCreature target) {
        target = AbstractDungeon.player;
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(target, potency));
            AbstractDungeon.actionManager.addToBottom(new RemoveRelayedDamageAction((AbstractPlayer) target));
            for (AbstractPower power : target.powers) {
                if (power instanceof AbstractChainPower) {
                    ((AbstractChainPower) power).finishMe();
                }
            }
        }
    }

    @Override
    public void initializeData() {
        potency = getPotency();
        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];
        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    @Override
    public AbstractPotion makeCopy() {
        return new ChainGrease();
    }

    @Override
    public int getPotency(final int potency) {
        return 7;
    }

}
