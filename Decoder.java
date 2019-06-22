public class Decoder{
	private String musicToDecode;
	private int[] musicDecoded;

	public void setMusicToDecode(String musica){
		this.musicToDecode = musica;
	}

	public int[] decodeMusic(){
		
		char[] notasArray = this.musicToDecode.toCharArray();
		int[] decodedMusic = new int[notasArray.length];
		
		char oldChar = notasArray[0];
		for(int i =0; i < notasArray.lenght; i++){
			currentChar = notasArray[i];




			oldChar = currentChar;
		}

		return decodedMusic;
	}

	public int[] returnMusicDecoded(){
		return this.musicDecoded;
	}
}