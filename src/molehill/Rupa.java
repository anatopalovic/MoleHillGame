package molehill;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Rupa extends Canvas implements Runnable {
	
	private Basta basta;
	private Zivotinja zivotinja = null;
	private Thread nit;
	private boolean pokrenuta = false;
	private int broj_koraka_pocetni;
	private int broj_koraka_trenutni;

	public Rupa(Basta basta) {
		super();
		this.basta = basta;
		
		
		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				if(pokrenuta == true)
					zgazi_rupu();
			}
		});
		
	}

	@Override
	public void run() {
		
		while(!Thread.interrupted()) {
			try {
				Thread.sleep(100);
				repaint();
				
					if(++broj_koraka_trenutni == broj_koraka_pocetni) {
						Thread.sleep(2000);
						zaustavi(false);
					}
				
			} catch (InterruptedException e) {
				nit.interrupt(); 
			}
		}
		repaint();

	}
	
	public void stvori() { 
		nit = new Thread(this);
	}
	
	public synchronized void pokreni() { 
		pokrenuta = true;
		broj_koraka_trenutni = 0;
		nit.start();
	}
	
	public synchronized void zaustavi(boolean kraj) { 
		if(pokrenuta) {
			nit.interrupt();
			if(zivotinja != null && kraj == false)
				zivotinja.ispolji_pobegla();
			pokrenuta = false;
			zivotinja = null;
			if(kraj == false)
				basta.slobodna_rupa();
		}
	}
	
	@Override
	public void paint(Graphics g) {
		setBackground(new Color(152, 93, 82));
		synchronized (this) {
			if(zivotinja != null)
				zivotinja.iscrtaj((double)broj_koraka_trenutni / (double)broj_koraka_pocetni);
		}
	}
	
	public synchronized void zgazi_rupu() {
		if(zivotinja != null)
			zivotinja.ispolji_udarena();
	}
	
	public synchronized void setZivotinja(Zivotinja zivotinja) {
		this.zivotinja = zivotinja;
	}
	
	public synchronized Zivotinja getZivotinja() {
		return zivotinja;
	}
	
	public synchronized boolean jel_pokrenuta() {
		return pokrenuta;
	}
	
	public synchronized void povrce_smanji() {
		if(pokrenuta == true) {
			basta.smanji_povrce();
		}
	}

	public synchronized void setBroj_koraka(int broj_koraka) {
		this.broj_koraka_pocetni = broj_koraka;
	}
	
	public synchronized int getBroj_koraka_tren() {
		return broj_koraka_trenutni;
	}

}
