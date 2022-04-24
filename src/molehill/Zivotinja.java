package molehill;


public abstract class Zivotinja {
	
	protected Rupa rupa;
	
	public Zivotinja(Rupa rupa) {
		super();
		this.rupa = rupa;
	}

	public abstract void ispolji_udarena();
	public abstract void ispolji_pobegla();

	public abstract void iscrtaj(double procenat);
	

}
