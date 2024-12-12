package ui;

import dataaccess.DatabaseException;
import dataaccess.MyCourseRepository;
import dataaccess.MyStudentRepository;
import domain.Course;
import domain.CourseType;
import domain.InvalidValueException;
import domain.Student;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Cli {
    Scanner scan;
    MyCourseRepository repo1;
    MyStudentRepository repo2;


    public Cli(MyCourseRepository repo1, MyStudentRepository repo2) {
        this.scan = new Scanner(System.in);
        this.repo1 = repo1;
        this.repo2 = repo2;
    }

    public void start() {
        String inputMain = "-";
        while (!inputMain.equals("x")) {
            showMainMenue();
            inputMain = this.scan.nextLine();
            switch (inputMain) {
                case "1":
                    showStudentMenue();
                    String inputStudents = this.scan.nextLine();

                    switch (inputStudents) {
                        case "1":
                            findAllStudentsbyName();
                            break;
                        case "2":
                            findAllStudentsbyBirthyear();
                            break;
                        case "3":
                            findAllStudentsbyId();
                            break;
                        case "x":
                            System.out.println("Auf Wiedersehen");
                            break;
                        default:
                            inputError();
                            break;
                    }
                    break;
                case "2":
                    showKursMenue();
                    String inputCourse = this.scan.nextLine();
                    switch (inputCourse) {
                        case "1":
                            addCourse();
                            break;
                        case "2":
                            showAllCourses();
                            break;
                        case "3":
                            showCourseDetails();
                            break;
                        case "4":
                            updateCourseDetails();
                            break;
                        case "5":
                            deleteCourse();
                            break;
                        case "6":
                            courseSearch();
                            break;
                        case "7":
                            runningCourses();
                            break;
                        case "x":
                            System.out.println("Auf Wiedersehen!");
                            break;
                        default:
                            inputError();
                            break;
                    }
                    break;
            }
        }
        scan.close();
    }

    private void findAllStudentsbyId() {
        System.out.println("Geben Sie einen Suchbegriff an!");
        Long searchLong = Long.parseLong(scan.nextLine());
        List<Student> studentList;
        try {
            studentList = repo2.findAllStudentsbyId(searchLong);
            for (Student student : studentList) {
                System.out.println(student);
            }
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler bei der Kurssuche: " + databaseException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter Fehler bei der Kurssuche: " + exception.getMessage());
        }
    }

    private void findAllStudentsbyBirthyear() {
        System.out.println("Geben Sie einen Suchbegriff an!");
        String searchString = scan.nextLine();
        List<Student> studentList;
        try {
            studentList = repo2.findAllStudentsbyBirthyear(searchString);
            for (Student student : studentList) {
                System.out.println(student);
            }
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler bei der Kurssuche: " + databaseException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter Fehler bei der Kurssuche: " + exception.getMessage());
        }
    }

    private void findAllStudentsbyName() {
        System.out.println("Geben Sie einen Suchbegriff an!");
        String searchString = scan.nextLine();
        List<Student> studentList;
        try {
            studentList = repo2.findAllStudentsbyVN(searchString);
            for (Student student : studentList) {
                System.out.println(student);
            }
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler bei der Kurssuche: " + databaseException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter Fehler bei der Kurssuche: " + exception.getMessage());
        }
    }

    private void runningCourses() {
        System.out.println("Aktuell laufende Kurse: ");
        List<Course> list;
        try {
            list = repo1.findAllRunningCourses();
            for (Course course : list) {
                System.out.println(course);
            }
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler bei Kurs-Anzeige für laufende Kurse: " + databaseException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter Fehler bei Kurs-Anzeige für laufende Kurse: " + exception.getMessage());
        }
    }

    private void courseSearch() {
        System.out.println("Geben Sie einen Suchbegriff an!");
        String searchString = scan.nextLine();
        List<Course> courseList;
        try {
            courseList = repo1.findAllCoursesByNameOrDescription(searchString);
            for (Course course : courseList) {
                System.out.println(course);
            }
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler bei der Kurssuche: " + databaseException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter Fehler bei der Kurssuche: " + exception.getMessage());
        }
    }

    private void deleteCourse() {
        System.out.println("Welchen Kurs mochten Sie löschen? Bitte ID eingeben:");
        Long courseIdToDelete = Long.parseLong(scan.nextLine());

        try {
            repo1.deleteById(courseIdToDelete);
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler beim Löschen: " + databaseException.getMessage());
        } catch (Exception e) {
            System.out.println("Unbekannter Fehler beim Löschen: " + e.getMessage());
        }
    }

    private void updateCourseDetails() {
        System.out.println("Für welche Kurs-ID möchten Sie die Kursdetails ändern?");
        Long courseId = Long.parseLong(scan.nextLine());

        try {
            Optional<Course> courseOptional = repo1.getById(courseId);
            if (courseOptional.isEmpty()) {
                System.out.println("Kurs mit der eingegebenen ID nicht in der Datenbank!");
            } else {
                Course course = courseOptional.get();

                System.out.println("Änderungen für folgenden Kurs: ");
                System.out.println(course);

                String name, description, hours, dateFrom, dateTo, courseType;

                System.out.println("Bitte neue Kursdaten angeben (Enter, falls keine Änderung gewünscht ist):");
                System.out.println("Name: ");
                name = scan.nextLine();
                System.out.println("Beschreibung: ");
                description = scan.nextLine();
                System.out.println("Stundenanzahl: ");
                hours = scan.nextLine();
                System.out.println("Startdatum (YYYY-MM-DD): ");
                dateFrom = scan.nextLine();
                System.out.println("Enddatum (YYYY-MM-DD): ");
                dateTo = scan.nextLine();
                System.out.println("Kurstyp (ZA/BF/FF/OE): ");
                courseType = scan.nextLine();

                Optional<Course> optionalCourseUpdated = repo1.update(
                        new Course(
                                course.getId(),
                                name.equals("") ? course.getName() : name,
                                description.equals("") ? course.getDescription() : description,
                                hours.equals("") ? course.getHours() : Integer.parseInt(hours),
                                dateFrom.equals("") ? course.getBeginDate() : Date.valueOf(dateFrom),
                                dateTo.equals("") ? course.getEndDate() : Date.valueOf(dateTo),
                                courseType.equals("") ? course.getCourseType() : CourseType.valueOf(courseType)
                        )
                );

                optionalCourseUpdated.ifPresentOrElse(
                        (c) -> System.out.println("Kurs aktualisiert: " + c),
                        () -> System.out.println("Kurs konnte nicht aktualisiert werden!")
                );
            }
        } catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("Eingabefehler: " + illegalArgumentException.getMessage());
        } catch (InvalidValueException invalidValueException) {
            System.out.println("Kursdaten nicht korrekt angegeben: " + invalidValueException.getMessage());
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler beim Einfügen: " + databaseException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter Fehler beim Einfügen: " + exception.getMessage());
        }
    }

    private void addCourse() {
        String name, description;
        int hours;
        Date dateFrom, dateTo;
        CourseType courseType;

        try {
            System.out.println("Bitte alle Kursdaten angeben:");
            System.out.println("Name: ");
            name = scan.nextLine();
            if (name.equals("")) throw new IllegalArgumentException("Eingabe: darf nicht leer sein!");
            System.out.println("Beschreibung: ");
            description = scan.nextLine();
            if (description.equals("")) throw new IllegalArgumentException("Eingabe darf nicht leer sein!");
            System.out.println("Stundenanzahl:");
            hours = Integer.parseInt(scan.nextLine());
            System.out.println("Startdatum (YYYY-MM-DD): ");
            dateFrom = Date.valueOf(scan.nextLine());
            System.out.println("Enddatum (YYYY-MM-DD): ");
            dateTo = Date.valueOf(scan.nextLine());
            System.out.println("Kurstyp: (ZA/BF/FF/OE: ");
            courseType = CourseType.valueOf(scan.nextLine());

            Optional<Course> optionalCourse = repo1.insert(
                    new Course(name, description, hours, dateFrom, dateTo, courseType)
            );
            if (optionalCourse.isPresent()) {
                System.out.println("Kurs angelegt: " + optionalCourse.get());
            } else {
                System.out.println("Kurs konnte nicht angelegt werden!");
            }
        } catch (IllegalArgumentException illegalArgumentException) {
            System.out.println("Eingabefehler: " + illegalArgumentException.getMessage());
        } catch (InvalidValueException invalidValueException) {
            System.out.println("Kursdaten nicht korrekt angegeben: " + invalidValueException.getMessage());
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler beim Einfügen: " + databaseException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter Fehler beim Einfügen: " + exception.getMessage());
        }
    }

    private void showCourseDetails() {
        System.out.println("Für welchen Kurs möchten Sie die Kursdetails anzeigen?");
        Long courseId = Long.parseLong(scan.nextLine());
        try {
            Optional<Course> courseOptional = repo1.getById(courseId);
            if (courseOptional.isPresent()) {
                System.out.println(courseOptional.get());
            } else {
                System.out.println("Kurs mit der ID " + courseId + " nicht gefunden!");
            }
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler bei Kurs-Detailanzeige: " + databaseException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter Fehler bei Kurs-Detailanzeige: " + exception.getMessage());
        }
    }

    private void showAllCourses() {
        List<Course> list = null;

        try {
            list = repo1.getAll();
            if (list.size() > 0) {
                for (Course course : list) {
                    System.out.println(course);
                }
            } else {
                System.out.println("Kursliste leer");
            }
        } catch (DatabaseException databaseException) {
            System.out.println("Datenbankfehler bei Anzeige aller Kurse: " + databaseException.getMessage());
        } catch (Exception exception) {
            System.out.println("Unbekannter Fehler bei Anzeige aller Kurse: " + exception.getMessage());
        }
    }

    private void showKursMenue() {
        System.out.println("---------- KURSMANAGEMENT ----------");
        System.out.println("(1) Kurs eingeben! \t (2) Alle Kurse anzeigen! \t" + "(3) Kursdetails anzeigen!");
        System.out.println("(4) Kursdetails ändern! \t (5) Kurs löschen! \t" + "(6) Kurssuche!");
        System.out.println("(7) Laufende Kurse! \t (-) ---- \t" + "(-) ---- ");
        System.out.println("(x) --- ENDE ---");
    }

    private void showStudentMenue() {
        System.out.println("---------- KURSMANAGEMENT ----------");
        System.out.println("(1) Nach Name suchen! \t (2) Nach Jahr suchen! \t" + "(3) Nach ID suchen!");
        System.out.println("(x) --- ENDE ---");
    }

    private void showMainMenue() {
        System.out.println("---------- AUSWAHL ----------");
        System.out.println("(1) Studentenmenü! \t (2) Kursmenü \t");
        System.out.println("(x) --- ENDE ---");
    }

    public void inputError() {
        System.out.println("Bitte nur die Zahlen der Menüauswahl eingeben!");
    }
}