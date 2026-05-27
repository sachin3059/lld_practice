public class IdleState implements ATMState{
    private final ATM atm;

    public IdleState(ATM atm){
        this.atm = atm;
    }

    @Override
    public void insertCard(Card card) {
        atm.setCurrentCard(card);
        atm.setCurrentState(new HasCardState(atm));
        System.out.println("Card inserted. Please enter your PIN.");
    }

    @Override
    public void enterPin(int pin) {
        System.out.println("Please enter a card first.");
    }

    @Override
    public void selectOperation(Transaction transaction){
        System.out.println("Please insert a card first");
    }

    @Override
    public void ejectCard(){
        System.out.println("No card to eject.");
    }


}