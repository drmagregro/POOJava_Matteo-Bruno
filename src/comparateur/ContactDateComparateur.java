package comparateur;

import java.util.Comparator;

import model.Contact;

public class ContactDateComparateur implements Comparator<Contact> {

	@Override
	public int compare(Contact o1, Contact o2) {
		return o1.getDateNaissance().compareTo(o2.getDateNaissance());
	}

}