public class Session {
    private boolean isVIP;
    protected CheckoutManager manager;
    boolean hasInformation;

    public Session(CheckoutManager c){
        manager = c;
    }

    public void setIsVIP(boolean VIP){
        isVIP = VIP;
    }


    public boolean getIsVIP(){ return isVIP; }



}
