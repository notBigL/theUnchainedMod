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
import theUnchainedMod.patches.CustomPotionEnums;

public class ChainGrease extends CustomPotion {

    public static final String POTION_ID = theUnchainedMod.DefaultMod.makeID("ChainGrease");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public ChainGrease() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
        super(NAME, POTION_ID, PotionRarity.COMMON, CustomPotionEnums.LINK, PotionColor.SMOKE);
        isThrown = false;
    }

    @Override
    public void use(AbstractCreature target) {
        target = AbstractDungeon.player;
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractCard liberation = new Liberation();
            liberation.setCostForTurn(0);
            this.addToBot(new MakeTempCardInHandAction(liberation.makeStatEquivalentCopy(), this.potency));
        }
    }

    @Override
    public void initializeData() {
        potency = getPotency();
        if (potency == 1) {
            description = DESCRIPTIONS[0];
        } else {
            description = DESCRIPTIONS[1] + potency + DESCRIPTIONS[2];
        }
        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    @Override
    public AbstractPotion makeCopy() {
        return new ChainGrease();
    }

    @Override
    public int getPotency(final int potency) {
        return 1;
    }

}
