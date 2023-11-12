package theUnchainedMod.packs;

import theUnchainedMod.cards.*;

import java.util.ArrayList;

import static theUnchainedMod.TheUnchainedMod.makeID;

public class UnchainedCrushedArmorPack extends AbstractUnchainedPack {
    public static final String ID = makeID("CrushedArmorPack");

    public UnchainedCrushedArmorPack() {
        super(ID, Cripple.ID, new PackSummary(1, 5, 2, 1, 3));
    }

    public ArrayList<String> getCards() {
        ArrayList<String> cards = new ArrayList<>();
        cards.add(HeavyHitter.ID);
        cards.add(KeepDistance.ID);
        cards.add(SingleOut.ID);
        cards.add(Baguazhang.ID);

        cards.add(Claustrophobia.ID);
        cards.add(RoyalAssessment.ID);
        cards.add(Cripple.ID);
        cards.add(ImmovableObject.ID);
        cards.add(TradeOffer.ID);

        cards.add(ThePrincesGrace.ID);
        return cards;
    }
}