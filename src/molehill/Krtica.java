package molehill;

import java.awt.Color;
import java.awt.Graphics;

public class Krtica extends Zivotinja {

	public Krtica(Rupa rupa) {
		super(rupa);
	}

	@Override
	public void ispolji_udarena() {
		rupa.setZivotinja(null);
		rupa.zaustavi(false);
	}

	@Override
	public void ispolji_pobegla() {
		rupa.povrce_smanji();
	}

	@Override
	public void iscrtaj(double procenat) {
		
		Graphics g = rupa.getGraphics();
		g.setColor(Color.DARK_GRAY);
		int x = rupa.getWidth() / 2;
		int y = rupa.getHeight() / 2;
		
		int r1 = (int)(rupa.getWidth() * procenat);
		int r2 = (int)(rupa.getHeight() * procenat);
		int r = Math.min(r1, r2);
		
		x -= r / 2;
		y -= r / 2;
		g.fillOval(x, y, r, r);
		g.dispose();
	}

}
