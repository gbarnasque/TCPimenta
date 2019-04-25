public class Configuracoes{
	private String instrumento;
	private int bpm;

	Configuracoes(){
		this.instrumento = "piano";
		this.bpm = 60;
	}

	public void setInstrumento(String instrumento){
		this.instrumento = instrumento;
	}
	public String getInstrumento(){
		return instrumento;
	}

	public void setBPM(int bpm){
		this.bpm = bpm;
	}
	public int getBPM(){
		return this.bpm;
	}
}