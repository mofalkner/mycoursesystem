package ui;

import domain.Course;

import java.util.List;
import java.util.Scanner;

public class Cli
{
    Scanner scan;
    MyCourseRepository repo;

    public Cli(MyCourseRepository repo)
    {
        this.scan = new Scanner(System.in);
        this.repo = repo;
    }

    public void start()
    {
        String input = "-";
        while (!input.equals("x"))
        {
            showMenue();
            input = scan.nextLine();
            switch (input)
            {
                case "1":
                    System.out.println("Kurseingabe!");
                    break;
                case "2":
                    showAllCourses();
                    break;
                case "x":
                    System.out.println("Auf Wiedersehen!");
                    break;
                default:
                    inputError();
                    break;
            }
        }
        scan.close();
    }

    private void showAllCourses()
    {
        List<Course> list = null;

        try {
            list = repo.getAll();
            if (list.size()>0)
            {
                for (Course course : list)
                {
                    System.out.println(course);
                }
            }
            else
            {
                System.out.println("Kursliste leer");
            }
        }
        catch (DatabaseEception databaseEception)
        {
            System.out.println("Datenbankfehler bei Anzeige aller Kurse: " + databaseEception.getMessage());
        }
        catch (Exception exception)
        {
            System.out.println("Unbekannter Fehler bei Anzeige aller Kurse: " + exception.getMessage());
        }
    }

    private void showMenue()
    {
        System.out.println("---------- KURSMANAGEMENT ----------");
        System.out.println("(1) Kurs eingeben! \t (2) Alle Kurse anzeigen! \t");
        System.out.println("(x) -- ENDE --");
    }

    public void inputError()
    {
        System.out.println("Bitte nur die Zahlen der Menüauswahl eingeben!");
    }
}