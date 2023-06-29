package theUnchainedMod.potions;

import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.actions.ApplyRelayedDamageAction;
import theUnchainedMod.actions.GainRelayAction;
import theUnchainedMod.cards.Swirl;
import theUnchainedMod.patches.CustomPotionEnums;

public class RelayPotion extends CustomPotion {

    public static final String POTION_ID = TheUnchainedMod.makeID("RelayPotion");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
    public final int relayAmount = 10;
    public final int relayedDamageAmount = 10;

    public RelayPotion() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, CustomPotionEnums.RELAY, PotionColor.SMOKE);
        isThrown = false;
    }

    @Override
    public void use(AbstractCreature target) {
        target = AbstractDungeon.player;
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractDungeon.actionManager.addToBottom(new GainRelayAction(target, relayAmount * potency));
            AbstractDungeon.actionManager.addToBottom(new ApplyRelayedDamageAction(target, relayedDamageAmount * potency));
        }
    }

    @Override
    public void initializeData() {
        potency = getPotency();
        description = DESCRIPTIONS[0] + relayAmount * potency +
                      DESCRIPTIONS[1] + relayedDamageAmount * potency +
                      DESCRIPTIONS[2];

        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    @Override
    public AbstractPotion makeCopy() {
        return new RelayPotion();
    }

    @Override
    public int getPotency(final int potency) {
        return 1;
    }

}
