package molehill;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Igra extends Frame {
	
	private static Igra igra = null;
	private Basta basta;
	private boolean radi = false;
	private Label povrce = new Label("");
	private Button kreni_stani;
	private CheckboxGroup tezine;
	private Checkbox lako;
	private Checkbox srednje;
	private Checkbox tesko;
	
	private Igra() {
		super("Igra");
		setBounds(300, 30, 650, 650);
		setLayout(new BorderLayout());
		
		
		
		basta = new Basta(4, 4);
		basta.dodeli_igru(this);
		add(basta, BorderLayout.CENTER);
		
		
		Panel opcije = new Panel(new GridLayout(4, 1));
		kreni_stani = new Button("Kreni");
		
		kreni_stani.addActionListener(e -> {
			
			if(radi == false) {
				radi = true;
				kreni_stani.setLabel("Stani");
				postavi_tezinu();
				lako.setEnabled(false);
				srednje.setEnabled(false);
				tesko.setEnabled(false);
				basta.pokreni();
			}
			else {
				radi = false;
				kreni_stani.setLabel("Kreni");
				basta.zaustavi();
				lako.setEnabled(true);
				srednje.setEnabled(true);
				tesko.setEnabled(true);
			}
			
		});
		
		Label tezina = new Label("Tezina: ");
		tezina.setFont(new Font(null,Font.BOLD,12));
		
		povrce = new Label("Povrce: " + basta.getPovrce());
		povrce.setFont(new Font(null,Font.BOLD,12));
		
		Panel tezina_panel = new Panel(new GridLayout(4, 1));
		tezine = new CheckboxGroup();
		lako = new Checkbox("Lako", true, tezine);
		srednje = new Checkbox("Srednje", false, tezine);
		tesko = new Checkbox("Tesko", false, tezine);
		
		
		tezina_panel.add(tezina);
		tezina_panel.add(lako);
		tezina_panel.add(srednje);
		tezina_panel.add(tesko);
		
		opcije.add(tezina_panel);
		opcije.add(kreni_stani);
		opcije.add(povrce);
		add(opcije, BorderLayout.EAST);
		
		
		
		
		
		setVisible(true);
		
		
		
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(radi == true)
					basta.zaustavi();
				dispose();
			}
		});
	}
	
	public static void napravi_igru() {
		igra = new Igra();
	}
	
	public static Igra dohvati_igru() {
		return igra;
	}
	

	private void postavi_tezinu() {
		switch(tezine.getSelectedCheckbox().getLabel()) {
			
			case "Lako": basta.setCekanje(1000); basta.setBroj_koraka(10); break;
			case "Srednje": basta.setCekanje(750); basta.setBroj_koraka(8); break;
			case "Tesko": basta.setCekanje(500); basta.setBroj_koraka(6); break;
		}
	}
	
	public void promeni_povrce() {
		if(basta.getPovrce() == 0) {
			povrce.setText("Povrce: 0");
			radi = false;
			kreni_stani.setLabel("Kreni");
			basta.zaustavi();
			lako.setEnabled(true);
			srednje.setEnabled(true);
			tesko.setEnabled(true);
			return;
		}
		povrce.setText("Povrce: " + basta.getPovrce());
	}



	public static void main(String[] args) {
		
		napravi_igru();

	}

}
