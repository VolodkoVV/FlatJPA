package vyacheslav.volodko;

import javax.persistence.*;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class FlatHab {
    static EntityManagerFactory emf;
    static EntityManager em;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            // create connection
            emf = Persistence.createEntityManagerFactory("JPATest");
            em = emf.createEntityManager();
            try {
                while (true) {
                    System.out.println("1: add flats");
                    System.out.println("2: add random flats");
                    System.out.println("3: delete flat");
                    System.out.println("4: change flat");
                    System.out.println("5: view flats");
                    System.out.print("-> ");

                    String s = sc.nextLine();
                    switch (s) {
                        case "1":
                            addFlat(sc);
                            break;
                        case "2":
                            insertRandomFlats(sc);
                            break;
                        case "3":
                            deleteFlat(sc);
                            break;
                        case "4":
                            changeFlat(sc);
                            break;
                        case "5":
                            viewFlats();
                            break;
                        default:
                            return;
                    }
                }
            } finally {
                sc.close();
                em.close();
                emf.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
    }

    private static void addFlat(Scanner sc) {
        System.out.print("Enter flat district: ");
        String district = sc.nextLine();
        System.out.print("Enter flat street: ");
        String street = sc.nextLine();
        System.out.print("Enter flat rooms: ");
        String sRoom = sc.nextLine();
        int room = Integer.parseInt(sRoom);
        System.out.print("Enter flat prise: ");
        String sPrise = sc.nextLine();
        int prise = Integer.parseInt(sPrise);

        em.getTransaction().begin();
        try {
            SimpleFlat c = new SimpleFlat(district,street,room,prise);
            em.persist(c);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void deleteFlat(Scanner sc) {
        System.out.print("Enter client id: ");
        String sId = sc.nextLine();
        long id = Long.parseLong(sId);

        SimpleFlat c = em.find(SimpleFlat.class, id);
        if (c == null) {
            System.out.println("Flat not found!");
            return;
        }

        em.getTransaction().begin();
        try {
            em.remove(c);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void changeFlat(Scanner sc) {
        System.out.print("Enter flat street: ");
        String street = sc.nextLine();

        System.out.print("Enter new prise: ");
        String sPrise = sc.nextLine();
        int prise = Integer.parseInt(sPrise);

        SimpleFlat c = null;
        try {
            Query query = em.createQuery("SELECT c FROM SimpleFlat c WHERE c.street = :street", SimpleFlat.class);
            query.setParameter("street", street);
            c = (SimpleFlat) query.getSingleResult();
        } catch (NoResultException ex) {
            System.out.println("Flat not found!");
            return;
        } catch (NonUniqueResultException ex) {
            System.out.println("Non unique result!");
            return;
        }

        em.getTransaction().begin();
        try {
            c.setPrise(prise);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void insertRandomFlats(Scanner sc) {
        System.out.print("Enter flats count: ");
        String sCount = sc.nextLine();
        int count = Integer.parseInt(sCount);

        em.getTransaction().begin();
        try {
            for (int i = 0; i < count; i++) {
                SimpleFlat c = new SimpleFlat(randomDistrict(),randomStreet(),RND.nextInt(5), RND.nextInt(1000000));
                em.persist(c);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void viewFlats() {
        Query query = em.createQuery("SELECT c FROM SimpleFlat c", SimpleFlat.class);
        List<SimpleFlat> list = (List<SimpleFlat>) query.getResultList();

        for (SimpleFlat c : list)
            System.out.println(c);
    }

    static final String[] DISTRICT = {"Pechersk", "Obolon", "Goloseev", "Soloma", "Svyatoshn"};
    static final String[] STREET = {"Pivnichna", "Jykova", "Bileqka", "Gagarina", "Nuvska"};
    static final Random RND = new Random();

    static String randomDistrict() {
        return DISTRICT[RND.nextInt(DISTRICT.length)];
    }
    static String randomStreet() {
        return STREET[RND.nextInt(STREET.length)];
    }
}


