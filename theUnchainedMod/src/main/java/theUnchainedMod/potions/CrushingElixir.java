package theUnchainedMod.potions;

import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import theUnchainedMod.patches.CustomPotionEnums;
import theUnchainedMod.powers.CrushedArmorPower;

public class CrushingElixir extends CustomPotion {

    public static final String POTION_ID = theUnchainedMod.DefaultMod.makeID("CrushingElixir");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public CrushingElixir() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, CustomPotionEnums.CRUSH, PotionColor.ANCIENT);
        potency = getPotency();
        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];
        isThrown = false;
        tips.add(new PowerTip(name, description));
    }

    @Override
    public void use(AbstractCreature target) {
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, AbstractDungeon.player, new CrushedArmorPower(mo, AbstractDungeon.player, potency)));
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
        return new CrushingElixir();
    }

    @Override
    public int getPotency(final int potency) {
        return 10;
    }
}
