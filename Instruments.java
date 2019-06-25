public enum Instruments{

    PIANO(0),
    GUITAR(1),
    BASS(2),
    XILOPHONE(3),
    HARPSICHORD(4),
    TUBULARBELLS(5),
    PANFLUTE(6),
    CHURCHORGAN(7);

    private int value;

    public Intruments(int value){
        this.value = value;
    }

    public int Value(){
        return this.value;
    }
}