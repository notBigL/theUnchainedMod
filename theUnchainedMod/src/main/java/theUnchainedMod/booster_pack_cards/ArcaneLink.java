package theUnchainedMod.booster_pack_cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.actions.GainRelayAction;
import theUnchainedMod.cards.AbstractDynamicRelayCard;
import theUnchainedMod.characters.TheUnchained;
import theUnchainedMod.patches.CustomTags;
import theUnchainedMod.powers.ArcaneLinkPower;
import theUnchainedMod.powers.DeliciousChurroPower;
import theUnchainedMod.powers.MagicMechaPower;

import static theUnchainedMod.TheUnchainedMod.makeCardPath;

public class ArcaneLink extends AbstractDynamicRelayCard {

    public static final String ID = TheUnchainedMod.makeID(ArcaneLink.class.getSimpleName());
    public static final String IMG = makeCardPath("ArcaneLink.png");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheUnchained.Enums.COLOR_BOOSTER;

    private static final int COST = 1;
    private static final int MAGIC_NUMBER = 9;
    private static final int UPGRADE_MAGIC_NUMBER = 3;
    private static final int SECOND_MAGIC_NUMBER = 2;
    private static final int UPGRADE_PLUS_SECOND_MAGIC_NUMBER = 1;
    private static final int CHAIN_LENGTH = 1;

    public ArcaneLink() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = SECOND_MAGIC_NUMBER;

        tags.add(CustomTags.CHAIN);
    }
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC_NUMBER);
            upgradeDefaultSecondMagicNumber(UPGRADE_PLUS_SECOND_MAGIC_NUMBER);
        }
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainRelayAction(p, magicNumber));

        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new ArcaneLinkPower(p, CHAIN_LENGTH, defaultSecondMagicNumber, TYPE)));
        if(p.hasPower(DeliciousChurroPower.POWER_ID)) {
            p.getPower(DeliciousChurroPower.POWER_ID).onSpecificTrigger();
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new ArcaneLinkPower(p, CHAIN_LENGTH, defaultSecondMagicNumber, TYPE)));
        }
    }
}
