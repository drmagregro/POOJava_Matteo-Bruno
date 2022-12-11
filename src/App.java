import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import comparateur.ContactDateComparateur;
import model.Contact;

public class App {
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        afficherMenu();
        while (true) {
            String choix = scan.nextLine();
            switch (choix) {
                case "1":
                    ajouterContact();
                    break;
                case "2":
                    listerContact();
                    break;
                case "3":
                    modifierContact();
                    break;
                case "4":
                    supprimerContact();
                    break;
                case "5":
                    trierContact();
                    break;
                case "6":
                    trierContactParDate();
                    break;
                case "7":
                    rechercherContact();
                    break;
                case "q":
                    scan.close();
                    return;
                default:
                    System.out.println("Boulet!!!!");
                    break;
            }
            afficherMenu();
        }
    }

    private static void supprimerContact() {
        try {
            listerContact();
            List<Contact> liste = Contact.lister();
            System.out.println("Mail du contact a supprimer ?");
            String mail = scan.nextLine();
            Contact contactASuppr = null;
            for (Contact c : liste) {
                if (c.getMail().equals(mail)) {
                    contactASuppr = c;
                }
            }
            if (contactASuppr != null) {
                liste.remove(contactASuppr);
                Contact.enregistrerTous(liste);
                System.out.println("Contact supprimé");
            } else {
                System.out.println("Pas de contact a supprimer");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void modifierContact() {
        try {
            listerContact();
            List<Contact> liste = Contact.lister();
            System.out.println("Mail du contact a modifié ?");
            String mail = scan.nextLine();
            Contact contactAModif = null;
            for (Contact c : liste) {
                if (c.getMail().equals(mail)) {
                    contactAModif = c;
                }
            }
            if (contactAModif != null) {
                System.out.println("Saisir le nom:");
                contactAModif.setNom(scan.nextLine());
                System.out.println("Saisir le prénom:");
                contactAModif.setPrenom(scan.nextLine());

                do {
                    try {
                        System.out.println("Saisir le téléphone:");
                        contactAModif.setNumero(scan.nextLine());
                        break;
                    } catch (ParseException e) {
                        System.out.println(e.getMessage());
                    }
                } while (true);

                do {
                    try {
                        System.out.println("Saisir le mail:");
                        contactAModif.setMail(scan.nextLine());
                        break;
                    } catch (ParseException e) {
                        System.out.println(e.getMessage());
                    }
                } while (true);

                do {
                    try {
                        System.out.println("Saisir la date de naissance:");
                        contactAModif.setDateNaissance(scan.nextLine());
                        break;
                    } catch (ParseException e) {
                        System.out.println("Error, try again!");
                    }
                } while (true);
                Contact.enregistrerTous(liste);
                System.out.println("Contact modifié");
            } else {
                System.out.println("Pas de contact a modifier");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void rechercherContact() {
        try {
            Stream<Contact> liste = Contact.lister().stream();
            System.out.println("Saisir recherche ?");
            String saisie = scan.nextLine();

            // 1ere technique
            /*
             * Stream<Contact> resultat = liste.filter(new Predicate<Contact>() {
             * 
             * @Override
             * public boolean test(Contact t) {
             * return t.getNom().contains(saisie);
             * }
             * });
             */

            // 2eme technique
            Stream<Contact> resultat2 = liste.filter(t -> t.getNom().contains(saisie));

            resultat2.forEach(c -> System.out.println(c.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void trierContactParDate() {
        try {
            ArrayList<Contact> liste;
            liste = Contact.lister();
            Collections.sort(liste, new ContactDateComparateur());

            for (Contact c : liste) {
                System.out.println(c.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void trierContact() {
        try {
            ArrayList<Contact> liste = Contact.lister();
            Collections.sort(liste, null);

            for (Contact c : liste) {
                System.out.println(c.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void listerContact() {
        // Contact c = new Contact();
        try {
            ArrayList<Contact> liste = Contact.lister();

            for (Contact contact : liste) {
                System.out.println(contact.getPrenom() + " " + contact.getNom());
            }
        } catch (IOException e) {
            System.out.println("Erreur avec le fichier");
        }

    }

    private static void ajouterContact() {

        Contact c = new Contact();
        System.out.println("Saisir le nom:");
        c.setNom(scan.nextLine());
        System.out.println("Saisir le prénom:");
        c.setPrenom(scan.nextLine());

        do {
            try {
                System.out.println("Saisir le téléphone:");
                c.setNumero(scan.nextLine());
                break;
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
        } while (true);

        do {
            try {
                System.out.println("Saisir le mail:");
                c.setMail(scan.nextLine());
                break;
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
        } while (true);

        do {
            try {
                System.out.println("Saisir la date de naissance:");
                c.setDateNaissance(scan.nextLine());
                break;
            } catch (ParseException e) {
                System.out.println("Error, try again!");
            }
        } while (true);

        c.enregistrer();

    }

    public static void afficherMenu() {

        ArrayList<String> menus = new ArrayList<>();
        menus.add("-- MENU --");
        menus.add("1- Ajouter un contact");
        menus.add("2- Lister les contacts");
        menus.add("3- Modifier contact");
        menus.add("4- Supprimer contact");
        menus.add("5- Trier les contacts");
        menus.add("6- Trier les contacts par date");
        menus.add("7- Rechercher les contacts sur nom");
        menus.add("q- Quitter");
        for (String s : menus) {
            System.out.println(s);
        }
    }
}