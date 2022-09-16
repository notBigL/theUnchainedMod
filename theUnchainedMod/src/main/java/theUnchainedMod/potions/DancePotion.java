package theUnchainedMod.potions;

import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theUnchainedMod.cards.Liberation;
import theUnchainedMod.cards.Swirl;

public class DancePotion extends CustomPotion {

    public static final String POTION_ID = theUnchainedMod.DefaultMod.makeID("DancePotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public DancePotion() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.S, PotionColor.SMOKE);
        potency = getPotency();
        description = DESCRIPTIONS[0];
        isThrown = false;
        tips.add(new PowerTip(name, description));
    }

    @Override
    public void use(AbstractCreature target) {
        target = AbstractDungeon.player;
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            Swirl swirl = new Swirl();
            if (target.hasPower("theUnchainedMod:FullSpinPower")) {
                swirl.fullSpinApply(target.getPower("theUnchainedMod:FullSpinPower").amount);
            }
            this.addToBot(new MakeTempCardInHandAction(swirl.makeStatEquivalentCopy(), this.potency));
        }
    }

    @Override
    public AbstractPotion makeCopy() {
        return new DancePotion();
    }

    @Override
    public int getPotency(final int potency) {
        return 2;
    }

    public void upgradePotion() {
        potency += 2;
        tips.clear();
        tips.add(new PowerTip(name, description));
    }
}
