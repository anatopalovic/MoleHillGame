package molehill;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Panel;

public class Basta extends Panel implements Runnable {
	
	private int vrste;
	private int kolone;
	private int povrce = 100;
	private double cekanje;
	private int broj_koraka;
	private Igra igra = null;
	private Thread nit;
	private Rupa matrica[][];
	private int slobodne;

	public Basta(int v, int k) {
		vrste = v;
		kolone = k;
		setBackground(Color.GREEN);
		setLayout(new GridLayout(vrste, kolone, 20, 20));
		slobodne = vrste * kolone;
		matrica = new Rupa[vrste][kolone];
		for(int i = 0; i < vrste; i++)
			for(int j = 0; j < kolone; j++) {
				matrica[i][j] = new Rupa(this);
				add(matrica[i][j]);
			}
				
	}

	@Override
	public void run() {
		
		while(!Thread.interrupted()) {
		try {
			synchronized (this) {
				while(slobodne == 0) {
					wait();
				}
			}
				Thread.sleep((long)cekanje);
				postavi_krticu();
				cekanje *= 99.0 / 100.0; 
				
		} catch (InterruptedException e) {
				nit.interrupt();
			}
			
		}

	}

	private synchronized void postavi_krticu() {

		while(true) {
			
			int i = (int)(Math.random() * vrste);
			int j = (int)(Math.random() * kolone);
			if(matrica[i][j].getZivotinja() != null)
				continue;
			matrica[i][j].setZivotinja(new Krtica(matrica[i][j]));
			--slobodne;
			matrica[i][j].stvori();
			matrica[i][j].pokreni();
			break;
		}
	}

	public int getPovrce() {
		return povrce;
	}

	public void zaustavi() {
		for(int i = 0; i < vrste; i++)
			for(int j = 0; j < kolone; j++)
				matrica[i][j].zaustavi(true);
		nit.interrupt();
		
	}

	public void pokreni() {
		nit = new Thread(this);
		slobodne = vrste * kolone;
		povrce = 100;
		igra.promeni_povrce();
		nit.start();
	}

	public void setCekanje(double i) {
		cekanje = i;
	}

	public void setBroj_koraka(int broj_koraka) {
		this.broj_koraka = broj_koraka;
		for(int i = 0; i < vrste; i++)
			for(int j = 0; j < kolone; j++)
				matrica[i][j].setBroj_koraka(broj_koraka);
	}
	
	public int getBroj_koraka() {
		return broj_koraka;
	}
	
	public synchronized void smanji_povrce() {
		--povrce;
		igra.promeni_povrce();
	}
	
	public synchronized void slobodna_rupa() {
			++slobodne;
			notify();
		}

	public void dodeli_igru(Igra i) {
		igra = i;
	}
	
}
