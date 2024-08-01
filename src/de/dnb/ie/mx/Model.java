/**
 *
 */
package de.dnb.ie.mx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import de.dnb.basics.utils.TimeUtils;
import de.dnb.gnd.parser.Record;
import de.dnb.gnd.utils.GNDUtils;
import de.dnb.gnd.utils.mx.Library;
import de.dnb.gnd.utils.mx.MXAddress;
import de.dnb.gnd.utils.mx.MXAddress.Adressierung;
import de.dnb.gnd.utils.mx.Mailbox;
import de.dnb.gnd.utils.mx.RedaktionsTyp;

/**
 * @author baumann
 *
 */
public class Model {

	Record record;

	List<Mailbox> mailboxes;

	Set<MXAddress> addresses;

	String idn;

	String vorzugsbenennung;

	int actualMxNr = 0;

	Mailbox antwort;

	private MXAddress meineAdresse;

	Library verbund;

	Library urheber;

	public void setAnwortText(final String text) {
		if (getAntwort() == null)
			return;
		getAntwort().setText(text);
	}

	public boolean antwortWirdAngezeigt() {
		return antwortErzeugt() && (actualMxNr == mailboxes.size() - 1);
	}

	private void erzeugeAntwort() {
		if (antwortErzeugt())
			return;

		antwort = new Mailbox();
		mailboxes.add(getAntwort());
		getAntwort().setText("Bitte Text eingeben");
		meineAdresse.setAdressierung(Adressierung.ABSENDER);
		getAntwort().setAbsender(meineAdresse);
		getAntwort().setDate(new Date());
	}

	/**
	 * @return
	 */
	public boolean antwortErzeugt() {
		return antwort != null;
	}

	public void antwortAnAlle() {
		erzeugeAntwort();
		addresses.forEach(ad ->
		{
			ad.setStumm(false);
			ad.setAdressierung(Adressierung.EMPFAENGER);
		});
		getAntwort().setBeteiligte(addresses);
	}

	public void standardEmpfaenger() {
		erzeugeAntwort();
		getAntwort().setBeteiligte(MXAddress.getStandardEmpfaenger());
	}

	public void mxUrheber() {
		erzeugeAntwort();
		final MXAddress mxUrheber = new MXAddress(Adressierung.EMPFAENGER,
				false, urheber, RedaktionsTyp.DEFAULT, "", "");
		getAntwort().addBeteiligten(mxUrheber);
	}

	public void mxRed() {
		erzeugeAntwort();
		final MXAddress mxUrheber = new MXAddress(Adressierung.EMPFAENGER,
				false, verbund, RedaktionsTyp.DEFAULT, "", "");
		getAntwort().addBeteiligten(mxUrheber);
	}

	public void antwortAnAbsender() {
		final Mailbox actMX = getActualMx();
		erzeugeAntwort();
		final MXAddress absender = actMX != null ? actMX.getAbsender()
				: MXAddress.getNullAddress();
		absender.setAdressierung(Adressierung.EMPFAENGER);
		final Collection<MXAddress> abs = Arrays.asList(absender);
		getAntwort().setBeteiligte(abs);
	}

	/**
	 *
	 */
	Model() {
		loadRecord();
	}

	/**
	 *
	 */
	private void loadRecord() {
		record = GNDUtils.readFromClip();
		mailboxes = new ArrayList<>();
		addresses = new TreeSet<>();
		if (record == null)
			return;
		Mailbox.parse(record, "FLLB").forEach(mx ->
		{
			mailboxes.add(mx);

		});
		idn = record.getId();
		try {
			vorzugsbenennung = GNDUtils.getNameOfRecord(record);
			verbund = GNDUtils.getVerbund(record);
			urheber = GNDUtils.getUrheber(record);
			vorzugsbenennung += " --- Eingebender Verbund: " + verbund;
			vorzugsbenennung += " --- Urheber: " + urheber;
		} catch (final IllegalStateException e) {
			// nix
		}
		addresses = Mailbox.getAllAdresses(record, "FLLB");
	}

	String getText() {
		final Mailbox actualMx = getActualMx();
		if (actualMx != null)
			return getActualMx().getText();
		else
			return null;
	}

	MXAddress getAbsender() {
		final Mailbox actualMx = getActualMx();
		if (actualMx != null)
			return actualMx.getAbsender();
		else
			return null;
	}

	/**
	 * @return
	 */
	private Mailbox getActualMx() {
		if (actualMxNr < mailboxes.size())
			return mailboxes.get(actualMxNr);
		else
			return null;
	}

	List<MXAddress> getBeteiligte() {
		final Mailbox actualMx = getActualMx();
		if (actualMx != null)
			return getActualMx().getBeteiligte();
		else
			return Collections.emptyList();
	}

	List<String> getDates() {
		return mailboxes.stream().map(mx ->
		{
			final Date date = mx.getDate();
			final String ddmmyyyy = date != null
					? TimeUtils.toDDMMYYYY(date) + " / "
					: "/ ";
			final MXAddress absender = mx.getAbsender();
			if (absender == null)
				return ddmmyyyy;

			final Library library = absender.getLibrary();
			if (library == null)
				return ddmmyyyy + absender.asMxString();

			return ddmmyyyy + library.getKurzPlusIsil();
		}).collect(Collectors.toList());
	}

	public static void main(final String[] args) {
		final Model model = new Model();
		System.out.println(model.mailboxes);
	}

	/**
	 * @param i
	 */
	public void setMx(final int i) {
		if (i >= 0 && i < mailboxes.size())
			actualMxNr = i;

	}

	/**
	 * @return the antwort
	 */
	public Mailbox getAntwort() {
		return antwort;
	}

	/**
	 *
	 * @param i
	 *            beliebig
	 * @return Bet. oder null
	 */
	public MXAddress getAnwortEmpf(final int i) {
		return antwort.getBeteiligten(i);
	}

	public void addEmpf2Antwort(final MXAddress empfaenger) {
		antwort.addBeteiligten(empfaenger);
	}

	public void removeEmpfVonAntw(final int nr) {
		antwort.removeBeteiligten(nr);
	}

	/**
	 * @param meineSTR
	 */
	void setMeineAdresse(String meineSTR) {
		if (meineSTR == null)
			meineSTR = "a-DE-101";
		meineAdresse = MXAddress.parse(meineSTR);
	}

	String meineAdresseStr() {
		return meineAdresse.asMxString();
	}

}
